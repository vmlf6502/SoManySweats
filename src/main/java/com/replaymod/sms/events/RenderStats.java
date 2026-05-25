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

package com.replaymod.sms.events;

import com.replaymod.sms.util.Aligner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.replaymod.sms.SoManySweats.STATS;

public class RenderStats {
    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.PLAYER_LIST) return;

        List<NetworkPlayerInfo> players = new ArrayList<>(Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap());
        List<IChatComponent> names = new ArrayList<>();
        for (NetworkPlayerInfo info : players) {
            ScorePlayerTeam team = info.getPlayerTeam();
            IChatComponent name = new ChatComponentText(ScorePlayerTeam.formatPlayerName(team, info.getGameProfile().getName()));
            names.add(name);
        }

        String[] statsToGet = {"level", "fkdr", "winstreak"};
        for (String stat : statsToGet) {
            Aligner.fillNames(names);
            for (int i = 0; i < players.size(); i++) {
                String uuid = players.get(i).getGameProfile().getId().toString();
                Map<String, ChatComponentText> playerStats = STATS.get(uuid);
                if (playerStats == null) {
                    continue;
                }

                ChatComponentText value = playerStats.get(stat);
                names.get(i).appendSibling(value);
            }
        }

        for (int i = 0; i < players.size(); i++) {
            players.get(i).setDisplayName(names.get(i));
        }
    }
}
