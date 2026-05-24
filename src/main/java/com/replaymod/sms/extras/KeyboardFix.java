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

/*
 * Keyboard fix approach inspired by MCKeyboardFix by Leo3418 (liaoyuan@gmail.com)
 * https://github.com/Leo3418/mckeyboardfix/tree/master
 * Licensed under the Apache License, Version 2.0
 */

/*
 * On Linux, some keys don't register while holding Shift,
 * due to Minecraft 1.8.9's outdated input system.
 * This serves as a fix for that.
 */

package com.replaymod.sms.extras;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class KeyboardFix {
	@SubscribeEvent
	public void onKeyPress(InputEvent.KeyInputEvent event) {
		if (!Keyboard.getEventKeyState()) return; // only detect key presses

		switch (Keyboard.getEventKey()) {
			case Keyboard.KEY_AT:						 // @
				KeyBinding.onTick(Keyboard.KEY_2);
				break;
			case Keyboard.KEY_CIRCUMFLEX:				 // ^
				KeyBinding.onTick(Keyboard.KEY_6);
				break;
		}
	}
}
