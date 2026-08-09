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

import me.vmlf6502.somanysweats.resources.PrestigeColors;
import net.minecraft.util.EnumChatFormatting;

import java.util.Map;

public class DataFormatter {
	public static void prettifyStats(Map<StatKey, String> stats) {
		stats.replace(StatKey.BEDWARS_LEVEL, bedwarsLevel(stats.get(StatKey.BEDWARS_LEVEL)));
		stats.replace(StatKey.BEDWARS_FKDR, kdr(stats.get(StatKey.BEDWARS_FKDR)));
		stats.replace(StatKey.BEDWARS_WLR, wlr(stats.get(StatKey.BEDWARS_WLR)));
		stats.replace(StatKey.BEDWARS_WINSTREAK, winstreak(stats.get(StatKey.BEDWARS_WINSTREAK)));

		stats.replace(StatKey.SKYWARS_KDR, kdr(stats.get(StatKey.SKYWARS_KDR)));
		stats.replace(StatKey.SKYWARS_WINSTREAK, winstreak(stats.get(StatKey.SKYWARS_WINSTREAK)));
	}

	public static String bedwarsLevel(String bwl) {
		if (bwl == null) {
			return EnumChatFormatting.GOLD + "[" +
						EnumChatFormatting.RED + EnumChatFormatting.OBFUSCATED + "???" +
						EnumChatFormatting.RESET + EnumChatFormatting.RED + "]";
		}

		int prestige = Integer.parseInt(bwl) / 100;
		String tag = "[" + bwl + getStar(prestige) + "]";
		return PrestigeColors.addColors(tag, prestige);
    }

	public static String kdr(String fkdr) {
		if (fkdr == null) {
			return EnumChatFormatting.GOLD + "{" +
					EnumChatFormatting.RED + EnumChatFormatting.OBFUSCATED + "????" +
					EnumChatFormatting.RESET + EnumChatFormatting.RED + "}";
		}

		int prestige = (int) Float.parseFloat(fkdr); // format FKDR the same way we format Bedwars level
		String tag = "{" + fkdr + "}";
		return PrestigeColors.addColors(tag, prestige);
	}

	public static String wlr(String wlr) {
		if (wlr == null) {
			return EnumChatFormatting.GOLD + "{" +
					EnumChatFormatting.RED + EnumChatFormatting.OBFUSCATED + "????" +
					EnumChatFormatting.RESET + EnumChatFormatting.RED + "}";
		}

		int prestige = (int) Float.parseFloat(wlr); // format FKDR the same way we format Bedwars level
		String tag = "(" + wlr + ")";
		return PrestigeColors.addColors(tag, prestige);
	}

	public static String winstreak(String ws) {
		if (ws == null) {
			return EnumChatFormatting.GOLD + "<" +
					EnumChatFormatting.RED + EnumChatFormatting.OBFUSCATED + "??" +
					EnumChatFormatting.RESET + EnumChatFormatting.RED + ">";
		}

		int wsInt = Integer.parseInt(ws);

		if (wsInt == 0) return EnumChatFormatting.GRAY + "<" + ws + ">";
		else if (wsInt < 10) return EnumChatFormatting.WHITE + "<" + ws + ">";
		else if (wsInt < 20) return EnumChatFormatting.GOLD + "<" + ws + ">";
		else if (wsInt < 30) return EnumChatFormatting.AQUA + "<" + ws + ">";
		else if (wsInt < 40) return EnumChatFormatting.DARK_GREEN + "<" + ws + ">";
		else if (wsInt < 50) return EnumChatFormatting.DARK_AQUA + "<" + ws + ">";
		else if (wsInt < 60) return EnumChatFormatting.DARK_RED + "<" + ws + ">";
		else if (wsInt < 70) return EnumChatFormatting.LIGHT_PURPLE + "<" + ws + ">";
		else if (wsInt < 80) return EnumChatFormatting.BLUE + "<" + ws + ">";
		else if (wsInt < 90) return EnumChatFormatting.DARK_PURPLE + "<" + ws + ">";
		else if (wsInt < 100) return EnumChatFormatting.RED + "<" + ws + ">";
		else if (wsInt < 150) return EnumChatFormatting.RED + "<" + ws + ">";
		else if (wsInt < 200) return EnumChatFormatting.DARK_BLUE + "<" + ws + ">";
		else return EnumChatFormatting.DARK_PURPLE + "<" + ws + ">";
	}

	private static String getStar(int prestige) {
		if (prestige <= 10) {
			return "✫";
		} else if (prestige <= 20) {
			return "✪";
		} else if (prestige <= 30) {
			return "⚝";
		}
		return "✥";
	}
}
