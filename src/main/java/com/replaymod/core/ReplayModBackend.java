/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.IResourcePack
 *  net.minecraftforge.common.config.Configuration
 *  net.minecraftforge.fml.common.Loader
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.common.Mod$EventHandler
 *  net.minecraftforge.fml.common.ModContainer
 *  net.minecraftforge.fml.common.event.FMLInitializationEvent
 *  net.minecraftforge.fml.common.event.FMLPreInitializationEvent
 */
package com.replaymod.core;

import com.replaymod.core.ReplayMod;
import com.replaymod.core.SettingsRegistry;
import com.replaymod.core.mixin.MinecraftAccessor;
import com.replaymod.core.versions.MCVer;
import com.replaymod.core.versions.forge.EventsAdapter;
import java.util.List;
import net.minecraft.client.resources.IResourcePack;
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
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        this.mod.initModules();
        this.eventsAdapter.register();
        System.out.println("but then i had a brilliant idea i used f5");
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

