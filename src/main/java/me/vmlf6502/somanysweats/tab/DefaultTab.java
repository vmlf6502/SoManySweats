package me.vmlf6502.somanysweats.tab;


import me.vmlf6502.somanysweats.util.Aligner;
import me.vmlf6502.somanysweats.util.StatKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static me.vmlf6502.somanysweats.SoManySweats.STATS;

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

        // APPLY
        ArrayList<StatKey> statsShown = RenderStats.getStatsShown();
        for (StatKey stat : statsShown) {
            Aligner.fillNames(names);

            for (int i = 0; i < players.size(); i++) {
                UUID uuid = players.get(i).getGameProfile().getId();
                Map<StatKey , String> stats = STATS.get(uuid);
                if (stats == null) continue;

                String value = stats.get(stat);
                if (value == null) value = "§6<§c§k???§6>§r"; // TODO: Make the brackets match per type of stat
                names.get(i).appendSibling(new ChatComponentText(" " + value));
            }
        }

        for (int i = 0; i < players.size(); i++) {
            players.get(i).setDisplayName(names.get(i));
        }
    }
}
