/*
 * Copyright (C) 2026 vmlf
 *
 * This file is part of SoManySweats-ReplayMod.
 *
 * SoManySweats-ReplayMod is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * SoManySweats-ReplayMod is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with SoManySweats-ReplayMod. If not, see <https://www.gnu.org/licenses/>.
 */

package com.replaymod.sms.util;

import com.replaymod.sms.resources.PrestigeColors;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class DataFormatter {
	public static ChatComponentText formatBedwarsLevel(String bwl) {
		if (bwl.equals("Nick")) {
			bwl = EnumChatFormatting.LIGHT_PURPLE + "[" + bwl + "]";
		} else {
			int prestige = Integer.parseInt(bwl) / 100;
			String tag = "[" + bwl + getStar(prestige) + "]";
			bwl = PrestigeColors.addColors(tag, prestige);
		}

		return new ChatComponentText(" " + EnumChatFormatting.BOLD + bwl);
	}

	public static ChatComponentText formatFkdr(String fkdr) {
		if (fkdr.equals("???")) {
			fkdr = EnumChatFormatting.YELLOW + "{???}";
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
			ws = EnumChatFormatting.YELLOW + "<???>";
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
