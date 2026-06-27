/*
 * Copyright (C) 2026 SoManySweats contributors
 *
 * This file is part of SoManySweats.
 * Based on code from ReplayMod by ReplayMod contributors
 * Original source: https://github.com/ReplayMod/ReplayMod
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

package com.replaymod.core;

import me.vmlf6502.somanysweats.SoManySweats;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid="somanysweats", useMetadata=true, version="1.8.9-2.6.14", acceptedMinecraftVersions="[ 1.8.9 ]", acceptableRemoteVersions="*", clientSideOnly=true)
public class ReplayModBackend {
    @Mod.EventHandler
    public void init(FMLPreInitializationEvent event) {
        System.out.println("we are rolllin rockn rollers");
        SoManySweats.INSTANCE.preinit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        SoManySweats.INSTANCE.init();
    }

    public String getVersion() {
        return ((ModContainer)Loader.instance().getIndexedModList().get("somanysweats")).getVersion();
    }

    public String getMinecraftVersion() {
        return "1.8.9";
    }

    public boolean isModLoaded(String id) {
        return Loader.isModLoaded((String)id);
    }
}

