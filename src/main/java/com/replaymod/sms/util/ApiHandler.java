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
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static com.replaymod.sms.SoManySweats.STATS;
import static com.replaymod.sms.SoManySweats.config;

public class ApiHandler {
	private static final String HYPIXEL_API = "https://api.hypixel.net/v2/player";
	private static final String PROXY_API = "https://hypixel-proxy.rustacean64.workers.dev/player";
	private static boolean API_KEY_INVALID = false;
	private static String STORED_INVALID_KEY;

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
		AtomicInteger errors = new AtomicInteger(0);

		for (NetworkPlayerInfo info : players) {
			String uuid = info.getGameProfile().getId().toString();
			if (STATS.containsKey(uuid)) {
				continue;
			}

			new Thread(() -> {
				boolean success = false;
				try {
					success = fetchAndStoreStats(info);
				} finally {
					if (!success) {
						errors.getAndIncrement();
					}
					latch.countDown();
				}
			}).start();
		}

		// Wait for all threads to finish
		new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

			if (errors.get() != 0) {
				Logger.log(EnumChatFormatting.RED + "Failed to fetch stats with " + errors.get() + " error(s).");
				Logger.log(EnumChatFormatting.GRAY + "	- Check logs for more info.");
				if (config.getInstance().apiData.developerMode && API_KEY_INVALID) {
					STORED_INVALID_KEY = config.getInstance().apiData.apiKey;
					Logger.log(EnumChatFormatting.RED + "Invalid API key.");
				}
			} else {
				Logger.log(EnumChatFormatting.GREEN + "Successfully fetched stats.");
			}
        }).start();
	}

	private static boolean fetchAndStoreStats(NetworkPlayerInfo info) {
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
			return false;
		}

		// Fetch from API
		HttpURLConnection conn;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			// Make CloudFlare allow the request
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
			if (config.getInstance().apiData.developerMode) {
				conn.setRequestProperty("API-Key", config.getInstance().apiData.apiKey);
			}

			int responseCode = conn.getResponseCode();
			if (responseCode == 403) {
				if (config.getInstance().apiData.developerMode) {
					API_KEY_INVALID = true;
				}
				BufferedReader err = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				StringBuilder errBody = new StringBuilder();
				String line;
				while ((line = err.readLine()) != null) errBody.append(line);
				System.err.println("Server returned response code 403 for URL: " + url + " with body: " + errBody);
				return false;
			}
			if (responseCode != 200) {
				System.out.println("Request failed with status: " + responseCode);
			}
		} catch (IOException e) {
			if (config.getInstance().apiData.developerMode) {
				System.err.println("Error connecting to the Hypixel API: " + e);
				Logger.log(EnumChatFormatting.RED + "Error connecting to the Hypixel API");
				Logger.log(EnumChatFormatting.GRAY + "	- The Hypixel API may be down.");
				Logger.log(EnumChatFormatting.GRAY + "	- Check logs for more info.");
			} else {
				System.err.println("Error connecting to the proxy: " + e);
				Logger.log(EnumChatFormatting.RED + "Error connecting to the proxy");
				Logger.log(EnumChatFormatting.GRAY + "	- The proxy may be down.");
				Logger.log(EnumChatFormatting.GRAY + "	- Try using Developer Mode if this issue persists.");
				Logger.log(EnumChatFormatting.GRAY + "	- Check logs for more info.");
			}
			return false;
		}

		StringBuilder response = new StringBuilder();
		try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
			String line;
			while ((line = in.readLine()) != null) {
				response.append(line);
			}
		} catch (IOException e) {
			System.err.println("Error reading input stream: " + e);
			Logger.log(EnumChatFormatting.RED + "Error reading response from server. Check logs for more info.");
			return false;
		}

		JSONObject data = new JSONObject(response.toString());

		String success = parseJSON(data, "success");
		if (Objects.equals(success, "false")) {
			String cause = parseJSON(data, "cause");
			System.err.println("Hypixel API request failed. Cause: " + cause);
			Logger.log(EnumChatFormatting.RED + "Hypixel API request failed. Cause: " + cause + ".");
			return false;
		} else if (Objects.equals(success, "???")) {
			System.err.println("Unexpected response from server: " + data);
			Logger.log("bad things happended");
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
		return true;
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
