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

import com.replaymod.sms.resources.PrestigeColors;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class DataFormatter {
	public static ChatComponentText formatBedwarsLevel(String bwl) {
		if (bwl.equals("???")) {
			bwl =
					EnumChatFormatting.GOLD + "[" +
							EnumChatFormatting.RED + EnumChatFormatting.OBFUSCATED + "???" +
							EnumChatFormatting.RESET + EnumChatFormatting.RED + "]";
		} else {
			int prestige = Integer.parseInt(bwl) / 100;
			String tag = "[" + bwl + getStar(prestige) + "]";
			bwl = PrestigeColors.addColors(tag, prestige);
		}

		return new ChatComponentText(" " + EnumChatFormatting.BOLD + bwl);
	}

	public static ChatComponentText formatFkdr(String fkdr) {
		if (fkdr.equals("???")) {
			fkdr =
					EnumChatFormatting.GOLD + "{" +
							EnumChatFormatting.RED + EnumChatFormatting.OBFUSCATED + "????" +
							EnumChatFormatting.RESET + EnumChatFormatting.RED + "}";
		}
		else {
			int prestige = (int) Float.parseFloat(fkdr); // format FKDR the same way we format Bedwars level
			String tag = "{" + fkdr + "}";
			fkdr = PrestigeColors.addColors(tag, prestige);
		}

		return new ChatComponentText(" " + EnumChatFormatting.ITALIC + fkdr);
	}

	public static ChatComponentText formatWs(String ws) {
		if (ws.equals("???")) {
			ws =
					EnumChatFormatting.GOLD + "<" +
							EnumChatFormatting.RED + EnumChatFormatting.OBFUSCATED + "??" +
							EnumChatFormatting.RESET + EnumChatFormatting.RED + ">";
		}
		else {
			int wsInt = Integer.parseInt(ws);

			if (wsInt == 0) ws = EnumChatFormatting.GRAY + "<" + ws + ">";
			else if (wsInt < 10) ws = EnumChatFormatting.WHITE + "<" + ws + ">";
			else if (wsInt < 20) ws = EnumChatFormatting.GOLD + "<" + ws + ">";
			else if (wsInt < 30) ws = EnumChatFormatting.AQUA + "<" + ws + ">";
			else if (wsInt < 40) ws = EnumChatFormatting.DARK_GREEN + "<" + ws + ">";
			else if (wsInt < 50) ws = EnumChatFormatting.DARK_AQUA + "<" + ws + ">";
			else if (wsInt < 60) ws = EnumChatFormatting.DARK_RED + "<" + ws + ">";
			else if (wsInt < 70) ws = EnumChatFormatting.LIGHT_PURPLE + "<" + ws + ">";
			else if (wsInt < 80) ws = EnumChatFormatting.BLUE + "<" + ws + ">";
			else if (wsInt < 90) ws = EnumChatFormatting.DARK_PURPLE + "<" + ws + ">";
			else if (wsInt < 100) ws = EnumChatFormatting.RED + "<" + ws + ">";
			else if (wsInt < 150) ws = EnumChatFormatting.RED + "<" + ws + ">";
			else if (wsInt < 200) ws = EnumChatFormatting.DARK_BLUE + "<" + ws + ">";
			else ws = EnumChatFormatting.DARK_PURPLE + "<" + ws + ">";
		}
		return new ChatComponentText(" " + EnumChatFormatting.UNDERLINE + ws);
	}

	public static ChatComponentText formatWlr(String wlr) {
		if (wlr.equals("???")) {
			wlr =
					EnumChatFormatting.GOLD + "(" +
							EnumChatFormatting.RED + EnumChatFormatting.OBFUSCATED + "??" +
							EnumChatFormatting.RESET + EnumChatFormatting.RED + ")";
		}
		else {
			float wlrFloat = Float.parseFloat(wlr);

			if (wlrFloat == 0) wlr = EnumChatFormatting.GRAY + "(" + wlr + ")";
			else if (wlrFloat < 10) wlr = EnumChatFormatting.WHITE + "(" + wlr + ")";
			else if (wlrFloat < 20) wlr = EnumChatFormatting.GOLD + "(" + wlr + ")";
			else if (wlrFloat < 30) wlr = EnumChatFormatting.AQUA + "(" + wlr + ")";
			else if (wlrFloat < 40) wlr = EnumChatFormatting.DARK_GREEN + "(" + wlr + ")";
			else if (wlrFloat < 50) wlr = EnumChatFormatting.DARK_AQUA + "(" + wlr + ")";
			else if (wlrFloat < 60) wlr = EnumChatFormatting.DARK_RED + "(" + wlr + ")";
			else if (wlrFloat < 70) wlr = EnumChatFormatting.LIGHT_PURPLE + "(" + wlr + ")";
			else if (wlrFloat < 80) wlr = EnumChatFormatting.BLUE + "(" + wlr + ")";
			else if (wlrFloat < 90) wlr = EnumChatFormatting.DARK_PURPLE + "(" + wlr + ")";
			else if (wlrFloat < 100) wlr = EnumChatFormatting.RED + "(" + wlr + ")";
			else if (wlrFloat < 150) wlr = EnumChatFormatting.RED + "(" + wlr + ")";
			else if (wlrFloat < 200) wlr = EnumChatFormatting.DARK_BLUE + "(" + wlr + ")";
			else wlr = EnumChatFormatting.DARK_PURPLE + "(" + wlr + ")";
		}

		return new ChatComponentText(" " + EnumChatFormatting.UNDERLINE + wlr);
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
