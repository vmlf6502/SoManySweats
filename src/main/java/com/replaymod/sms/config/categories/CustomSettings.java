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

package com.replaymod.sms.config.categories;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorText;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class CustomSettings {

    @Expose
    @ConfigOption(name = "Custom 1", desc = "API path")
    @ConfigEditorText
    public String custom1 = "newPackageRank";

    @Expose
    @ConfigOption(name = "Custom 2", desc = "API path")
    @ConfigEditorText
    public String custom2 = "stats/Bedwars/bw_unique_challenges_completed";

    @Expose
    @ConfigOption(name = "Custom 3", desc = "API path")
    @ConfigEditorText
    public String custom3 = "socialMedia/links/DISCORD";
}