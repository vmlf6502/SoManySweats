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

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Aligner {
	public static void fillNames(List<IChatComponent> names) {
		ArrayList<Integer> widths = new ArrayList<>();
		for (IChatComponent name : names) {
			widths.add(Minecraft.getMinecraft().fontRendererObj.getStringWidth(name.getFormattedText()));
		}

		int largest = Collections.max(widths);
		for (int i = 0; i < names.size(); i++) {
			names.get(i).appendSibling(new ChatComponentText(addWhitespace(largest - widths.get(i))));
		}
	}

	private static String addWhitespace(int px) {
		return new String(new char[px/4]).replace("\0", " "); // spaces
	}
}
