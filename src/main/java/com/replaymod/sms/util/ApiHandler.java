/*
 * Copyright (C) 2026 vmlf
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

package com.replaymod.sms.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static com.replaymod.sms.SoManySweats.STATS;
import static com.replaymod.sms.SoManySweats.config;

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

		for (NetworkPlayerInfo info : players) {
			String uuid = info.getGameProfile().getId().toString();
			if (STATS.containsKey(uuid)) {
				latch.countDown();
				continue;
			}

			if (rateLimiter.check("hypixel_api", "GET")) {
				threadPool.submit(() -> {
					String error = null;
					try {
						error = getStatsOfPlayer(info);
					} finally {
						if (error != null) {
							errors.add(error);
						}
						latch.countDown();
						successes.getAndIncrement();
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
		new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

			if (!errors.isEmpty()) {
				Logger.log(EnumChatFormatting.RED + "Failed to fetch " + errors.size() + " player(s)'s stats:");

				List<String> uniqueErrors = new ArrayList<>(new HashSet<>(errors));
				for (String error : uniqueErrors) {
					Logger.log(EnumChatFormatting.RED + error);
				}
			} else {
				Logger.log(EnumChatFormatting.GREEN + "Successfully fetched " + successes.get() + " player(s)'s stats.");
			}
        }).start();
	}

	private static String getStatsOfPlayer(NetworkPlayerInfo info) {
		String uuid = info.getGameProfile().getId().toString();

		URL url;
		try {
			if (config.getInstance().apiData.developerMode) {
				url = new URL(HYPIXEL_API + "?uuid=" + uuid);
			} else {
				url = new URL(PROXY_API + "?uuid=" + uuid);
			}
		} catch (MalformedURLException e) {
			System.err.println("Error creating URL from either " + HYPIXEL_API + " or " + PROXY_API + ": " + e);

			return "Error resolving API URL";
		}

		// Fetch from API
		HttpURLConnection conn;
		boolean requestFailed = false;
		try {
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
					return "Unexpected response code " + responseCode + ".";
				}
				requestFailed = true;
			}
		} catch (IOException e) {
			if (config.getInstance().apiData.developerMode) {
				System.err.println("Error connecting to the Hypixel API: " + e);
				return "Error connecting to the Hypixel API. The Hypixel API may be down";
			}
			System.err.println("Error connecting to the proxy: " + e);
			return "Error connecting to the proxy. The proxy may be down. Try using Developer Mode if this issue persists";
		}

        InputStream inputStream;
        try {
            inputStream = requestFailed
                    ? conn.getErrorStream()
                    : conn.getInputStream();
        } catch (IOException e) {
			System.err.println("Error getting input stream: " + e);
			conn.disconnect();
            return "Error getting input stream.";
        }

		StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = in.readLine()) != null) response.append(line);
		} catch (IOException e) {
			System.err.println("Error reading input stream: " + e);
			return "Error reading the API's response.";
		} finally {
			conn.disconnect();
			try {
				inputStream.close();
			} catch (IOException e) {
				System.err.println("Error closing input stream: " + e);
			}
		}

		JSONObject data = new JSONObject(response.toString());

		String success = parseJSON(data, "success");
		if (Objects.equals(success, "false")) {
			String cause = parseJSON(data, "cause");
			System.err.println("Hypixel API request failed. Cause: " + cause);
			if (Objects.equals(cause, "Invalid API key")) {
				if (!config.getInstance().apiData.developerMode) {
					return "The proxy's API key is invalid. " +
							"Open an issue on GitHub (if there isn't one already) and we'll update the key as soon as we can. " +
							"In the meantime, you can try using Developer Mode with your own API key.";
				}
				API_KEY_INVALID = true;
				STORED_INVALID_KEY = config.getInstance().apiData.apiKey;
			}
			return "Cause: " + cause + ".";
		} else if (Objects.equals(success, "???")) {
			System.err.println("Unexpected response from server: " + data);
			return "Unexpected response from server.";
		}

		// Nick detection
		boolean nicked = false;
		String player = parseJSON(data, "player");
		if (Objects.equals(player, "???")) {
			// TODO: Query Mojang API to see if the player exists rather than assuming they are nicked even though they may have never logged onto Hypixel
			nicked = true;
		}

		String bedwarsLevel, finalKills, finalDeaths, fkdr, winstreak, custom1, custom2, custom3;
		bedwarsLevel = fkdr = winstreak = custom1 = custom2 = custom3 = "???";


		if (!nicked) {
			JSONObject playerData = new JSONObject(player);
			bedwarsLevel = parseJSON(playerData, "achievements/bedwars_level");
			finalKills = parseJSON(playerData, "stats/Bedwars/final_kills_bedwars");
			finalDeaths = parseJSON(playerData, "stats/Bedwars/final_deaths_bedwars");
			winstreak = parseJSON(playerData, "stats/Bedwars/winstreak");
			custom1 = parseJSON(playerData, config.getInstance().statsSettings.custom1);
			custom2 = parseJSON(playerData, config.getInstance().statsSettings.custom2);
			custom3 = parseJSON(playerData, config.getInstance().statsSettings.custom3);

			// FKDR
			if (Objects.equals(finalKills, "???")) {
				finalKills = "0";
			}
			if (Objects.equals(finalDeaths, "???")) {
				finalDeaths = "0";
			}
			if (Objects.equals(finalDeaths, "0")) { // avoid division by zero error
				fkdr = finalKills;
			} else {
				fkdr = String.valueOf(new BigDecimal(finalKills).divide(new BigDecimal(finalDeaths), 2, RoundingMode.HALF_UP));
			}
		}

		Map<String, ChatComponentText> playerStats = new HashMap<>();
		playerStats.put("level", DataFormatter.formatBedwarsLevel(bedwarsLevel));
		playerStats.put("fkdr", DataFormatter.formatFkdr(fkdr));
		playerStats.put("winstreak", DataFormatter.formatWs(winstreak));
		playerStats.put("custom1", new ChatComponentText(EnumChatFormatting.RESET + " " + EnumChatFormatting.RED + "-" + custom1 + "-"));
		playerStats.put("custom2", new ChatComponentText(EnumChatFormatting.RESET + " " + EnumChatFormatting.GREEN + "~" + custom2 + "~"));
		playerStats.put("custom3", new ChatComponentText(EnumChatFormatting.RESET + " " + EnumChatFormatting.BLUE + "=" + custom3 + "="));

		STATS.put(uuid, playerStats);
		return null;
	}

	private static String parseJSON(JSONObject data, String path) {
		String[] paths = path.split("/");
		JSONObject current = data;

		for (String s : paths) {
			if (!current.has(s)) {
				return "???";
			}
			Object value = current.get(s);

			if (value instanceof JSONObject) {
				current = (JSONObject) value;
			} else if (value == JSONObject.NULL) {
				return "???";
			} else {
				return value.toString();
			}
		}

		return current.toString();
	}
}
