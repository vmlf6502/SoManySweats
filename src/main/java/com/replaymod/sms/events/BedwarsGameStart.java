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

import com.replaymod.sms.util.ApiHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

// TODO: Identify the start of Bedwars games with the scoreboard instead of chat
public class BedwarsGameStart {
    @SubscribeEvent
    public void onReceiveChatMessage(ClientChatReceivedEvent event) {
        // If game started
        if (event.message.getUnformattedText() == null || !event.message.getUnformattedText().contains("Upgrade yourself and your team by collecting")) return;

        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            ApiHandler.fetchPlayerStats();
        }).start();
    }
}
