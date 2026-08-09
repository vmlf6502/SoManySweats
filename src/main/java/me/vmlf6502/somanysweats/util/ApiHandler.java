/*
 * Copyright (C) 2026 SoManySweats contributors.
 *
 * This file is part of SoManySweats.
 *
 * SoManySweats is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * SoManySweats is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with SoManySweats. If not, see <https://www.gnu.org/licenses/>.
 */

package me.vmlf6502.somanysweats.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.EnumChatFormatting;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static me.vmlf6502.somanysweats.SoManySweats.STATS;
import static me.vmlf6502.somanysweats.SoManySweats.config;

public class ApiHandler {
	private static final String HYPIXEL_API = "https://api.hypixel.net/v2/player";
	private static final String PROXY_API = "https://hypixel-proxy.somanysweats.workers.dev/player";

	private static boolean API_KEY_INVALID = false;
	private static String STORED_INVALID_KEY;

	private static final RateLimiter rateLimiter = new RateLimiter(300, 300);
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(100);

	public static void fetchPlayerStats() {
		if (config.getInstance().apiData.developerMode) {
			if (Objects.equals(config.getInstance().apiData.apiKey, "")) {
				Logger.log("No API key found.");
				return;
			} else if (Objects.equals(config.getInstance().apiData.apiKey, STORED_INVALID_KEY)) {
				Logger.log(EnumChatFormatting.RED + "Invalid API key.");
				return;
			} else {
				API_KEY_INVALID = false;
			}
		}

		Collection<NetworkPlayerInfo> players = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
		CountDownLatch latch = new CountDownLatch(players.size());
		ArrayList<String> errors = new ArrayList<>();
		AtomicInteger successes = new AtomicInteger();
		int skips = 0;

		for (NetworkPlayerInfo info : players) {
			// Avoid getting stats that we already have and bots/obfuscated players
			UUID uuid = info.getGameProfile().getId();
			ScorePlayerTeam team = info.getPlayerTeam();
			if (STATS.containsKey(uuid) || info.getGameProfile().getName().startsWith("§k") || (team != null && Objects.equals(team.getTeamName(), "§fa999-76d80d5f"))) {
				skips++;
				latch.countDown();
				continue;
			}

			if (rateLimiter.check("hypixel_api", "GET")) {
				threadPool.submit(() -> {
					try {
						Map<StatKey, String> stats = getStatsOfPlayer(uuid);
						STATS.put(uuid, stats);
						successes.getAndIncrement();
					} catch (Exception e) {
						System.out.println(Arrays.toString(e.getStackTrace()));
						errors.add(e.getMessage());
					} finally {
						latch.countDown();
					}
				});
			} else {
				while (latch.getCount() > 0) {
					errors.add("Rate limit exceeded.");
					latch.countDown();
				}
			}
		}

		// Wait for all threads to finish
		int finalSkips = skips;
		new Thread(() -> {
			try {
				latch.await();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}

			if (!errors.isEmpty()) {
				List<String> uniqueErrors = new ArrayList<>(new HashSet<>(errors));
				uniqueErrors.remove("Skipped");

				Logger.log(EnumChatFormatting.RED + "Failed to fetch " + errors.size() + " player(s)'s stats:");
				for (String error : uniqueErrors) {
					Logger.log(EnumChatFormatting.RED + error);
				}
			} else {
				Logger.log(EnumChatFormatting.GREEN + "Successfully fetched " + successes.get() + " player(s)'s stats.");
				if (finalSkips != 0) {
					Logger.log(EnumChatFormatting.GREEN + "(Skipped " + finalSkips + " bots/obfuscated names)");
				}
			}
		}).start();
	}

	private static Map<StatKey, String> getStatsOfPlayer(UUID uuid) throws IOException, RuntimeException {
		URL url;
		if (config.getInstance().apiData.developerMode) {
			url = new URL(HYPIXEL_API + "?uuid=" + uuid.toString());
		} else {
			url = new URL(PROXY_API + "?uuid=" + uuid.toString());
		}

		// Fetch from API
		HttpURLConnection conn;
		boolean requestFailed = false;
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5000);
		conn.setReadTimeout(5000);
		if (config.getInstance().apiData.developerMode) {
			conn.setRequestProperty("API-Key", config.getInstance().apiData.apiKey);
		} else {
			// Make CloudFlare allow the request
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
		}

		int responseCode = conn.getResponseCode();
		if (responseCode != 200) {
			System.err.println("Request failed with status: " + responseCode);
			if (!(responseCode == 400 || responseCode == 403 || responseCode == 429)) {
				throw new RuntimeException("Unexpected response code " + responseCode + ".");
			}
			requestFailed = true;
		}

		InputStream inputStream;
		inputStream = requestFailed
				? conn.getErrorStream()
				: conn.getInputStream();

		StringBuilder response = new StringBuilder();
		try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = in.readLine()) != null) response.append(line);
		} catch (IOException e) {
			System.err.println("Error reading input stream: " + e);
			throw new IOException("Error reading the API's response.");
		} finally {
			conn.disconnect();
			inputStream.close();
		}

		JSONObject data = new JSONObject(response.toString());

		String success = parseJSON(data, "success");
		if (Objects.equals(success, "false")) {
			String cause = parseJSON(data, "cause");
			System.err.println("Hypixel API request failed. Cause: " + cause);
			if (Objects.equals(cause, "Invalid API key")) {
				if (!config.getInstance().apiData.developerMode) {
					throw new RuntimeException("The proxy's API key is invalid. " +
							"Open an issue on GitHub (if there isn't one already) and we'll update the key as soon as we can. " +
							"In the meantime, if you are a developer, you can try using Developer Mode with your own API key.");
				}
				API_KEY_INVALID = true;
				STORED_INVALID_KEY = config.getInstance().apiData.apiKey;
			}
			throw new RuntimeException("Cause: " + cause + ".");
		} else if (success == null) {
			System.err.println("Unexpected response from server: " + data);
			throw new RuntimeException("Unexpected response from server.");
		}

		Map<StatKey, String> stats = new HashMap<>();

		// Nick detection
		String player = parseJSON(data, "player");
		if (player == null) {
			stats.put(StatKey.IS_NICKED, "true");
			return stats;
		} else {
			stats.put(StatKey.IS_NICKED, "false");
		}

		JSONObject playerData = new JSONObject(player);
		stats.put(StatKey.BEDWARS_LEVEL, parseJSON(playerData, "achievements/bedwars_level"));
		stats.put(StatKey.BEDWARS_FINAL_KILLS, parseJSON(playerData, "stats/Bedwars/final_kills_bedwars"));
		stats.put(StatKey.BEDWARS_FINAL_DEATHS, parseJSON(playerData, "stats/Bedwars/final_deaths_bedwars"));
		stats.put(StatKey.BEDWARS_WINSTREAK, parseJSON(playerData, "stats/Bedwars/winstreak"));
		stats.put(StatKey.BEDWARS_WINS, parseJSON(playerData, "stats/Bedwars/wins_bedwars"));
		stats.put(StatKey.BEDWARS_LOSSES, parseJSON(playerData, "stats/Bedwars/losses_bedwars"));
		stats.put(StatKey.SKYWARS_WINSTREAK, parseJSON(playerData, "stats/SkyWars/win_streak"));
		stats.put(StatKey.SKYWARS_KILLS, parseJSON(playerData, "stats/SkyWars/kills"));
		stats.put(StatKey.SKYWARS_DEATHS, parseJSON(playerData, "stats/SkyWars/deaths"));
		stats.put(StatKey.CUSTOM_1, parseJSON(playerData, config.getInstance().statsSettings.custom.one.path));
		stats.put(StatKey.CUSTOM_2, parseJSON(playerData, config.getInstance().statsSettings.custom.two.path));
		stats.put(StatKey.CUSTOM_3, parseJSON(playerData, config.getInstance().statsSettings.custom.three.path));

		stats.put(StatKey.BEDWARS_FKDR, getRatio(stats.get(StatKey.BEDWARS_FINAL_KILLS), stats.get(StatKey.BEDWARS_FINAL_DEATHS)));
		stats.put(StatKey.BEDWARS_WLR, getRatio(stats.get(StatKey.BEDWARS_WINS), stats.get(StatKey.BEDWARS_LOSSES)));
		stats.put(StatKey.SKYWARS_KDR, getRatio(stats.get(StatKey.SKYWARS_KILLS), stats.get(StatKey.SKYWARS_DEATHS)));

		DataFormatter.prettifyStats(stats);
		return stats;
	}

	private static String parseJSON(JSONObject data, String path) {
		String[] paths = path.split("/");
		JSONObject current = data;

		for (String s : paths) {
			if (!current.has(s)) {
				return null;
			}
			Object value = current.get(s);

			if (value instanceof JSONObject) {
				current = (JSONObject) value;
			} else if (value == JSONObject.NULL) {
				return null;
			} else {
				return value.toString();
			}
		}

		return current.toString();
	}

	private static String getRatio(String x, String y) {
		if (x == null) {
			x = "0";
		}
		if (y == null) {
			y = "0";
		}
		if (Objects.equals(y, "0")) { // avoid division by zero error
			return x;
		}

		return String.valueOf(new BigDecimal(x).divide(new BigDecimal(y), 2, RoundingMode.HALF_UP));
	}
}