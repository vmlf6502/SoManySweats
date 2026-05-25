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

import com.replaymod.sms.SoManySweats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.replaymod.sms.SoManySweats.STATS;

public class ApiHandler {
	private static final String HYPIXEL_API = "https://api.hypixel.net/v2/player";
	private static final String PROXY_API = "https://hypixel-proxy.rustacean64.workers.dev";

	public static void fetchPlayerStats() {
		if (Objects.equals(SoManySweats.config.getInstance().apiData.apiKey, "")) {
			Logger.log("No API key found.");
			return;
		}

		Collection<NetworkPlayerInfo> players = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();

		for (NetworkPlayerInfo info : players) {
			String uuid = info.getGameProfile().getId().toString();
			if (STATS.containsKey(uuid)) {
				continue;
			}

			new Thread(() -> fetchAndStoreStats(info)).start();
		}
	}

	private static void fetchAndStoreStats(NetworkPlayerInfo info) {
		String uuid = info.getGameProfile().getId().toString();

		String bedwarsLevel = "Nick";
		String fkdr = "???";
		String ws = "???";
		int finalKills = 0;
		int finalDeaths = 0;

		try {
			URL url;
			if (SoManySweats.config.getInstance().apiData.developerMode) {
				url = new URL(HYPIXEL_API + "?key=" + SoManySweats.config.getInstance().apiData.apiKey + "&uuid=" + uuid);
			} else {
				url = new URL(PROXY_API + "/player?uuid=" + uuid);
			}
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);

			int responseCode = conn.getResponseCode();
			if (responseCode == 403) {
				Logger.log(EnumChatFormatting.RED + "Invalid API key.");
				return;
			}
			if (responseCode != 200) {
				System.out.println("Request failed with status: " + responseCode);
			}

			StringBuilder response = new StringBuilder();
			try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
				String line;
				while ((line = in.readLine()) != null) {
					response.append(line);
				}
			}

			JSONObject player = new JSONObject(response.toString()).getJSONObject("player");

			try {
				JSONObject bedwars = player.getJSONObject("stats").getJSONObject("Bedwars");
				bedwarsLevel = String.valueOf(player.getJSONObject("achievements").getInt("bedwars_level"));
				finalKills = bedwars.getInt("final_kills_bedwars");
				finalDeaths = bedwars.getInt("final_deaths_bedwars");
				ws = String.valueOf(bedwars.getInt("winstreak"));
			} catch (JSONException ignored) {}

			if (!bedwarsLevel.equals("Nick")) {
				fkdr = finalDeaths != 0
					? new BigDecimal(finalKills).divide(new BigDecimal(finalDeaths), 2, RoundingMode.HALF_UP).toString()
					: String.valueOf(finalKills);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, ChatComponentText> playerStats = new HashMap<>();
		playerStats.put("level", DataFormatter.formatBedwarsLevel(bedwarsLevel));
		playerStats.put("fkdr", DataFormatter.formatFkdr(fkdr));
		playerStats.put("winstreak", DataFormatter.formatWs(ws));

		STATS.put(uuid, playerStats);
	}
}
