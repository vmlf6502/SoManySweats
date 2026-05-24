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

package com.replaymod.sms.events;

import com.replaymod.sms.SoManySweats;
import com.replaymod.sms.config.SMSConfig;
import com.replaymod.sms.util.Logger;
import io.github.notenoughupdates.moulconfig.common.IMinecraft;
import io.github.notenoughupdates.moulconfig.gui.GuiScreenElementWrapper;
import io.github.notenoughupdates.moulconfig.gui.MoulConfigEditor;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static com.replaymod.sms.SoManySweats.config;

public class TriggerOpenConfig {
    public static boolean triggerConfig = false;
    private boolean wasConfigOpen = false;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (triggerConfig) {
            SoManySweats.config.openConfigGui();
            triggerConfig = false;
        }

        if (event.phase != TickEvent.Phase.START) return;

        if (wasConfigOpen && Minecraft.getMinecraft().currentScreen == null) {
            SoManySweats.config.saveToFile();
        }
        wasConfigOpen = Minecraft.getMinecraft().currentScreen instanceof GuiScreenElementWrapper;
    }
}
