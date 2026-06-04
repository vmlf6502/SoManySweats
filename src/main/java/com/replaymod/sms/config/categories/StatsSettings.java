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
import io.github.notenoughupdates.moulconfig.annotations.Category;

public class StatsSettings {

    @Expose
    @Category(name = "Bedwars", desc = "Bedwars stats shown in tab")
    public BedwarsSettings bedwars = new BedwarsSettings();

    @Expose
    @Category(name = "Skywars", desc = "Skywars stats shown in tab")
    public SkywarsSettings skywars = new SkywarsSettings();

    @Expose
    @Category(name = "Custom Endpoints", desc = "Custom API values")
    public CustomSettings custom = new CustomSettings();
}