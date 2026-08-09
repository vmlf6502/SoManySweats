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

package me.vmlf6502.somanysweats.config.categories;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.*;

public class StatsSettings {
    // CUSTOM TAB
    @Expose
    @ConfigOption(name = "Custom Tab", desc = "Renders a custom Tab list, making it easier to read stats. **May break some features of the default Tab list**")
    @ConfigEditorBoolean
    public boolean customTab = false;

    // BEDWARS
    @Expose
    @ConfigOption(name = "Bedwars", desc = "Bedwars stats shown in Tab")
    @Accordion
    public BedwarsSettings bedwars = new BedwarsSettings();
    public static class BedwarsSettings {
        @Expose
        @ConfigOption(name = "Level", desc = "Bedwars level")
        @ConfigEditorBoolean
        public boolean level = true;

        @Expose
        @ConfigOption(name = "FKDR", desc = "Bedwars final kill/death ratio")
        @ConfigEditorBoolean
        public boolean fkdr = true;

        @Expose
        @ConfigOption(name = "WLR", desc = "Bedwars win/loss ratio")
        @ConfigEditorBoolean
        public boolean wlr = true;

        @Expose
        @ConfigOption(name = "Win Streak", desc = "Bedwars win streak")
        @ConfigEditorBoolean
        public boolean winstreak = false;
    }

    // SKYWARS
    @Expose
    @ConfigOption(name = "Skywars", desc = "Skywars stats shown in Tab")
    @Accordion
    public SkywarsSettings skywars = new SkywarsSettings();
    public static class SkywarsSettings {
        @Expose
        @ConfigOption(name = "KDR", desc = "Skywars kill/death ratio")
        @ConfigEditorBoolean
        public boolean kdr = false;

        @Expose
        @ConfigOption(name = "Win Streak", desc = "Skywars win streak")
        @ConfigEditorBoolean
        public boolean winstreak = false;

    }

    // CUSTOM
    @Expose
    @ConfigOption(name = "Custom Stats", desc = "Custom stats from the API")
    @Accordion
    public CustomSettings custom = new CustomSettings();
    public static class CustomSettings {
        // CUSTOM 1
        @Expose
        @ConfigOption(name = "Custom 1", desc = "A custom data value from the API")
        @Accordion
        public CustomValue one = new CustomValue();

        // CUSTOM 2
        @Expose
        @ConfigOption(name = "Custom 2", desc = "A custom data value from the API")
        @Accordion
        public CustomValue two = new CustomValue();

        // CUSTOM 3
        @Expose
        @ConfigOption(name = "Custom 3", desc = "A custom data value from the API")
        @Accordion
        public CustomValue three = new CustomValue();

        public static class CustomValue {
            @Expose
            @ConfigOption(name = "API Path", desc = "Ex: socialMedia/links/DISCORD")
            @ConfigEditorText
            public String path = "";

            @Expose
            @ConfigOption(name = "Enabled", desc = "")
            @ConfigEditorBoolean
            public boolean enabled = false;
        }
    }
}