/*
 * Copyright (C) 2026 SoManySweats contributors.
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

package me.vmlf6502.somanysweats.tab;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.*;

import static me.vmlf6502.somanysweats.SoManySweats.STATS;

public class CustomTab {
    private static ArrayList<TabColumn> COLUMNS;

    public static void renderTabList() {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution res = new ScaledResolution(mc);
        int screenWidth = res.getScaledWidth();

        FontRenderer fr = mc.fontRendererObj;
        NetHandlerPlayClient connection = mc.getNetHandler();
        if (connection == null) return;

        List<NetworkPlayerInfo> players = new ArrayList<>(connection.getPlayerInfoMap());

        Scoreboard scoreboard = mc.theWorld.getScoreboard();
        players.sort(Comparator.comparing((NetworkPlayerInfo p) -> {
            ScorePlayerTeam team = scoreboard.getPlayersTeam(p.getGameProfile().getName());
            return team != null ? team.getRegisteredName() : "";
        }).thenComparing(p -> p.getGameProfile().getName()));

        COLUMNS = new ArrayList<>();
        COLUMNS.add(new TabColumn("Name", 150, TabColumn.Alignment.LEFT));
        for (String stat : RenderStats.getStatsShown()) {
            COLUMNS.add(new TabColumn(stat, 40, TabColumn.Alignment.CENTER));
        }

        int entryHeight = 10;
        int headerHeight = 14;
        int padding = 4;

        int totalWidth = getTotalWidth();
        int totalHeight = headerHeight + players.size() * (entryHeight + 1) + padding * 2;

        int startX = (screenWidth - totalWidth) / 2;
        int startY = 10;

        // Background
        Gui.drawRect(
                startX - padding,
                startY - padding,
                startX + totalWidth + padding,
                startY + totalHeight,
                0xAA000000
        );

        // Column headers
        int colX = startX;
        for (TabColumn col : COLUMNS) {
            drawAligned(fr, EnumChatFormatting.YELLOW + col.header, colX, startY, col.width, col.align, 0xFFFFAA);
            colX += col.width + 4;
        }

        // Divider line under headers
        int divY = startY + headerHeight - 2;
        Gui.drawRect(startX, divY, startX + totalWidth, divY + 1, 0x88FFFFFF);

        // Player rows
        for (int i = 0; i < players.size(); i++) {
            NetworkPlayerInfo info = players.get(i);
            int rowY = startY + headerHeight + i * (entryHeight + 1);

            // Alternating row backgrounds
            if (i % 2 == 0) {
                Gui.drawRect(startX, rowY, startX + totalWidth, rowY + entryHeight, 0x22FFFFFF);
            }

            List<String> values = getRowData(info);

            colX = startX;
            for (int j = 0; j < COLUMNS.size(); j++) {
                TabColumn col = COLUMNS.get(j);
                String value = j < values.size() ? values.get(j) : "-";
                drawAligned(fr, value, colX, rowY + 1, col.width, col.align, 0xFFFFFF);
                colX += col.width + 4;
            }
        }
    }

    private static int getTotalWidth() {
        int total = 0;
        for (TabColumn col : COLUMNS) total += col.width;
        return total + (COLUMNS.size() - 1) * 4; // 4px gap between columns
    }

    private static void drawAligned(FontRenderer fr, String text, int x, int y,
                                    int colWidth, TabColumn.Alignment align, int color) {
        int textWidth = fr.getStringWidth(text);
        int drawX = 0;
        switch (align) {
            case LEFT:
                drawX = x;
                break;
            case CENTER:
                drawX = x + (colWidth - textWidth) / 2;
                break;
            case RIGHT:
                drawX = x + colWidth - textWidth;
                break;
        };
        fr.drawStringWithShadow(text, drawX, y, color);
    }

    private static List<String> getRowData(NetworkPlayerInfo info) {
        ScorePlayerTeam team = info.getPlayerTeam();
        String name = ScorePlayerTeam.formatPlayerName(team, info.getGameProfile().getName());

        UUID uuid = info.getGameProfile().getId();
        ArrayList<String> statsShown = RenderStats.getStatsShown();

        Map<String, ChatComponentText> playerStats = STATS.get(uuid.toString());
        ArrayList<String> statsList = new ArrayList<>();

        statsList.add(name);

        if (playerStats != null) {
            for (String stat : statsShown) {
                ChatComponentText value = playerStats.get(stat);
                if (value == null) continue;
                statsList.add(value.getUnformattedText());
            }
        }

        return statsList;
    }

    private static class TabColumn {
        public final String header;
        public final int width;
        public final Alignment align;

        public enum Alignment { LEFT, CENTER, RIGHT }

        public TabColumn(String header, int width, Alignment align) {
            this.header = header;
            this.width = width;
            this.align = align;
        }
    }
}