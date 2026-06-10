package com.replaymod.sms.tab;


import com.replaymod.sms.util.Aligner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.replaymod.sms.SoManySweats.STATS;

public class DefaultTab {
    public static void render() {
        List<NetworkPlayerInfo> players =
                new ArrayList<>(Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap());

        List<IChatComponent> names = new ArrayList<>();

        for (NetworkPlayerInfo info : players) {
            ScorePlayerTeam team = info.getPlayerTeam();
            IChatComponent name = new ChatComponentText(
                    ScorePlayerTeam.formatPlayerName(team, info.getGameProfile().getName())
            );
            names.add(name);
        }

        ArrayList<String> statsShown = RenderStats.getStatsShown();

        // APPLY
        for (String stat : statsShown) {

            Aligner.fillNames(names);

            for (int i = 0; i < players.size(); i++) {
                String uuid = players.get(i).getGameProfile().getId().toString();
                Map<String, ChatComponentText> playerStats = STATS.get(uuid);

                if (playerStats == null) continue;

                ChatComponentText value = playerStats.get(stat);

                if (value != null) {
                    names.get(i).appendSibling(value);
                }
            }
        }

        for (int i = 0; i < players.size(); i++) {
            players.get(i).setDisplayName(names.get(i));
        }
    }
}
