/*
 * Copyright (C) 2026 vmlf
 *
 * This file is part of SoManySweats-ReplayMod.
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

package com.replaymod.sms;

import com.replaymod.sms.config.SMSConfig;
import com.replaymod.sms.events.BedwarsGameStart;
import com.replaymod.sms.events.RenderStats;
import com.replaymod.sms.events.TriggerOpenConfig;
import com.replaymod.sms.extras.KeyboardFix;
import io.github.notenoughupdates.moulconfig.GuiTextures;
import io.github.notenoughupdates.moulconfig.common.MyResourceLocation;
import io.github.notenoughupdates.moulconfig.managed.ManagedConfig;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SoManySweats {
	public static final SoManySweats INSTANCE = new SoManySweats();
	public static Map<String, Map<String, ChatComponentText>> STATS = new HashMap<String, Map<String, ChatComponentText>>();

	private File smsDir;
	public static ManagedConfig<SMSConfig> config;

	public static boolean showLevel = true;
	public static boolean showFkdr = true;
	public static boolean showWinstreak = true;
	public static boolean showFinals;
	public static boolean showWins;
	public static boolean showBeds;

	private SoManySweats() {}

	public void preinit(FMLPreInitializationEvent event) {
		smsDir = new File(event.getModConfigurationDirectory(), "somanysweats");
		smsDir.mkdirs();
		File configFile = new File(smsDir, "configNew.json");

		config = ManagedConfig.create(
				configFile,
				SMSConfig.class);
		config.saveToFile();
		config.reloadFromFile();
	}

	public void init() {
		ClientCommandHandler.instance.registerCommand(new SMSCommand());
		MinecraftForge.EVENT_BUS.register(new BedwarsGameStart());
		MinecraftForge.EVENT_BUS.register(new RenderStats());
		MinecraftForge.EVENT_BUS.register(new TriggerOpenConfig());
		MinecraftForge.EVENT_BUS.register(new KeyboardFix());
		GuiTextures.setTextureRoot(new MyResourceLocation("somanysweats", "core"));
	}
}
