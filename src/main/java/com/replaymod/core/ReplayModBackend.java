/*
 * Copyright (C) 2026 vmlf
 *
 * This file is part of SoManySweats-ReplayMod.
 * Based on code from ReplayMod by ReplayMod contributors
 * Original source: https://github.com/ReplayMod/ReplayMod
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

package com.replaymod.core;

import com.replaymod.core.versions.forge.EventsAdapter;

import com.replaymod.sms.SoManySweats;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid="replaymod", useMetadata=true, version="1.8.9-2.6.14", acceptedMinecraftVersions="[ 1.8.9 ]", acceptableRemoteVersions="*", clientSideOnly=true, updateJSON="https://raw.githubusercontent.com/ReplayMod/ReplayMod/master/versions.json", guiFactory="com.replaymod.core.gui.GuiFactory")
public class ReplayModBackend {
    private final ReplayMod mod = new ReplayMod(this);
    private final EventsAdapter eventsAdapter = new EventsAdapter();
    @Deprecated
    public static Configuration config;

    @Mod.EventHandler
    public void init(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        SettingsRegistry settingsRegistry = this.mod.getSettingsRegistry();
        settingsRegistry.backend.setConfiguration(config);
        settingsRegistry.save();

        SoManySweats.INSTANCE.preinit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        this.mod.initModules();
        this.eventsAdapter.register();

        SoManySweats.INSTANCE.init();
    }

    public String getVersion() {
        return ((ModContainer)Loader.instance().getIndexedModList().get("replaymod")).getVersion();
    }

    public String getMinecraftVersion() {
        return "1.8.9";
    }

    public boolean isModLoaded(String id) {
        return Loader.isModLoaded((String)id);
    }
}

