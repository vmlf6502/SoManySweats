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

package com.replaymod.sms.resources;

import net.minecraft.util.EnumChatFormatting;

import static net.minecraft.util.EnumChatFormatting.AQUA;
import static net.minecraft.util.EnumChatFormatting.BLACK;
import static net.minecraft.util.EnumChatFormatting.BLUE;
import static net.minecraft.util.EnumChatFormatting.DARK_AQUA;
import static net.minecraft.util.EnumChatFormatting.DARK_BLUE;
import static net.minecraft.util.EnumChatFormatting.DARK_GRAY;
import static net.minecraft.util.EnumChatFormatting.DARK_GREEN;
import static net.minecraft.util.EnumChatFormatting.DARK_PURPLE;
import static net.minecraft.util.EnumChatFormatting.DARK_RED;
import static net.minecraft.util.EnumChatFormatting.GOLD;
import static net.minecraft.util.EnumChatFormatting.GRAY;
import static net.minecraft.util.EnumChatFormatting.GREEN;
import static net.minecraft.util.EnumChatFormatting.LIGHT_PURPLE;
import static net.minecraft.util.EnumChatFormatting.RED;
import static net.minecraft.util.EnumChatFormatting.WHITE;
import static net.minecraft.util.EnumChatFormatting.YELLOW;

public class PrestigeColors {
	private static final EnumChatFormatting[][] LIST = {
		new EnumChatFormatting[]{GRAY},														 							// 0
			new EnumChatFormatting[]{WHITE},
			new EnumChatFormatting[]{GOLD},
			new EnumChatFormatting[]{AQUA},
			new EnumChatFormatting[]{DARK_GREEN},
		new EnumChatFormatting[]{DARK_AQUA},														 					// 5
			new EnumChatFormatting[]{DARK_RED},
			new EnumChatFormatting[]{LIGHT_PURPLE},
			new EnumChatFormatting[]{BLUE},
			new EnumChatFormatting[]{DARK_PURPLE},
		new EnumChatFormatting[]{RED, GOLD, YELLOW, GREEN, AQUA, LIGHT_PURPLE, DARK_PURPLE},							// 10
			new EnumChatFormatting[]{GRAY, WHITE, WHITE, WHITE, WHITE, GRAY, GRAY},
			new EnumChatFormatting[]{GRAY, YELLOW, YELLOW, YELLOW, YELLOW, GOLD, GRAY},
			new EnumChatFormatting[]{GRAY, AQUA, AQUA, AQUA, AQUA, DARK_AQUA, GRAY},
			new EnumChatFormatting[]{GRAY, GREEN, GREEN, GREEN, GREEN, DARK_GREEN, GRAY},
		new EnumChatFormatting[]{GRAY, DARK_AQUA, DARK_AQUA, DARK_AQUA, DARK_AQUA, BLUE, GRAY},							// 15
			new EnumChatFormatting[]{GRAY, RED, RED, RED, RED, DARK_RED, GRAY},
			new EnumChatFormatting[]{GRAY, LIGHT_PURPLE, LIGHT_PURPLE, LIGHT_PURPLE, LIGHT_PURPLE, DARK_PURPLE, GRAY},
			new EnumChatFormatting[]{GRAY, BLUE, BLUE, BLUE, BLUE, DARK_BLUE, GRAY},
			new EnumChatFormatting[]{GRAY, DARK_PURPLE, DARK_PURPLE, DARK_PURPLE, DARK_PURPLE, DARK_GRAY, GRAY},
		new EnumChatFormatting[]{DARK_GRAY, GRAY, WHITE, WHITE, GRAY, GRAY, DARK_GRAY},									// 20
			new EnumChatFormatting[]{WHITE, WHITE, YELLOW, YELLOW, GOLD, GOLD, GOLD},
			new EnumChatFormatting[]{GOLD, GOLD, WHITE, WHITE, AQUA, DARK_AQUA, DARK_AQUA},
			new EnumChatFormatting[]{DARK_PURPLE, DARK_PURPLE, LIGHT_PURPLE, LIGHT_PURPLE, GOLD, YELLOW, YELLOW},
			new EnumChatFormatting[]{AQUA, AQUA, WHITE, WHITE, GRAY, GRAY, DARK_GRAY},
		new EnumChatFormatting[]{WHITE, WHITE, GREEN, GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN},						// 25
			new EnumChatFormatting[]{DARK_RED, DARK_RED, RED, RED, LIGHT_PURPLE, LIGHT_PURPLE, DARK_PURPLE},
			new EnumChatFormatting[]{YELLOW, YELLOW, WHITE, WHITE, DARK_GRAY, DARK_GRAY, DARK_GRAY},
			new EnumChatFormatting[]{GREEN, GREEN, DARK_GREEN, DARK_GREEN, GOLD, GOLD, YELLOW},
			new EnumChatFormatting[]{AQUA, AQUA, DARK_AQUA, DARK_AQUA, BLUE, BLUE, DARK_BLUE},
		new EnumChatFormatting[]{YELLOW, YELLOW, GOLD, GOLD, RED, RED, DARK_RED},										// 30
			new EnumChatFormatting[]{BLUE, BLUE, DARK_AQUA, DARK_AQUA, GOLD, GOLD, YELLOW},
			new EnumChatFormatting[]{RED, DARK_RED, GRAY, GRAY, DARK_RED, RED, RED},
			new EnumChatFormatting[]{BLUE, BLUE, BLUE, LIGHT_PURPLE, RED, RED, DARK_RED},
			new EnumChatFormatting[]{DARK_GREEN, GREEN, LIGHT_PURPLE, LIGHT_PURPLE, DARK_PURPLE, DARK_PURPLE, DARK_GREEN},
		new EnumChatFormatting[]{RED, RED, DARK_RED, DARK_RED, DARK_GREEN, GREEN, GREEN},								// 35
			new EnumChatFormatting[]{GREEN, GREEN, GREEN, AQUA, BLUE, BLUE, DARK_BLUE},
			new EnumChatFormatting[]{DARK_RED, DARK_RED, RED, RED, AQUA, DARK_AQUA, DARK_AQUA},
			new EnumChatFormatting[]{DARK_BLUE, DARK_BLUE, BLUE, DARK_PURPLE, DARK_PURPLE, LIGHT_PURPLE, DARK_BLUE},
			new EnumChatFormatting[]{RED, RED, GREEN, GREEN, DARK_AQUA, BLUE, BLUE},
		new EnumChatFormatting[]{DARK_PURPLE, DARK_PURPLE, RED, RED, GOLD, GOLD, YELLOW},								// 40
			new EnumChatFormatting[]{YELLOW, YELLOW, GOLD, RED, LIGHT_PURPLE, LIGHT_PURPLE, DARK_PURPLE},
			new EnumChatFormatting[]{DARK_BLUE, BLUE, DARK_AQUA, AQUA, WHITE, GRAY, GRAY},
			new EnumChatFormatting[]{BLACK, DARK_PURPLE, DARK_GRAY, DARK_GRAY, DARK_PURPLE, DARK_PURPLE, BLACK},
			new EnumChatFormatting[]{DARK_GREEN, DARK_GREEN, DARK_GREEN, YELLOW, GOLD, DARK_PURPLE, LIGHT_PURPLE},
		new EnumChatFormatting[]{WHITE, WHITE, AQUA, AQUA, DARK_AQUA, DARK_AQUA, DARK_AQUA},							// 45
			new EnumChatFormatting[]{DARK_AQUA, AQUA, YELLOW, YELLOW, GOLD, LIGHT_PURPLE, DARK_PURPLE},
			new EnumChatFormatting[]{WHITE, DARK_RED, RED, RED, BLUE, DARK_BLUE, BLUE},
			new EnumChatFormatting[]{DARK_PURPLE, DARK_PURPLE, RED, GOLD, YELLOW, AQUA, DARK_AQUA},
			new EnumChatFormatting[]{DARK_GREEN, GREEN, WHITE, WHITE, GREEN, GREEN, DARK_GREEN},
		new EnumChatFormatting[]{DARK_RED, DARK_RED, DARK_PURPLE, BLUE, BLUE, DARK_BLUE, BLACK}							// 50
	};

	public static String addColors(String tag, int prestige) {
		EnumChatFormatting[] colors;
		if (prestige >= LIST.length) {
			colors = LIST[LIST.length - 1];
		} else {
			colors = LIST[prestige];
		}

		StringBuilder sb = new StringBuilder();
		int minLen = Math.min(tag.length(), colors.length);

		// Zip together
		for (int i = 0; i < minLen; i++) {
			sb.append(colors[i]);
			sb.append(tag.charAt(i));
		}

		// Append leftovers
		sb.append(tag.substring(minLen));

		return sb.toString();
	}
}
