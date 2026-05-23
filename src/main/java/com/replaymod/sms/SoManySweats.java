/*
 * Copyright (C) 2026 vmlf
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

package com.replaymod.sms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.replaymod.sms.config.SMSConfig;
import com.replaymod.sms.extras.KeyboardFix;
//import io.github.moulberry.notenoughupdates.core.config.ConfigUtil;
//import io.github.moulberry.notenoughupdates.util.kotlin.KotlinTypeAdapterFactory;
import com.typesafe.config.ConfigUtil;
import io.github.notenoughupdates.moulconfig.gui.GuiScreenElementWrapper;
import io.github.notenoughupdates.moulconfig.gui.MoulConfigEditor;
import io.github.notenoughupdates.moulconfig.observer.PropertyTypeAdapterFactory;
import io.github.notenoughupdates.moulconfig.processor.ConfigProcessorDriver;
import io.github.notenoughupdates.moulconfig.processor.MoulConfigProcessor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SoManySweats {
	public static final SoManySweats INSTANCE = new SoManySweats();
	public static Map<String, Map<String, ChatComponentText>> STATS = new HashMap<String, Map<String, ChatComponentText>>();

	private File smsDir;
	private static File configFile;
	public static MoulConfigEditor editor;
	public static SMSConfig config = new SMSConfig();

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

		configFile = new File(smsDir, "configNew.json");

		if (configFile.exists()) {
			loadConfig();
		}

		if (config == null) {
			config = new SMSConfig();
			saveConfig();
		}
	}

	public void init() {
		ClientCommandHandler.instance.registerCommand(new SMSCommand());
		MinecraftForge.EVENT_BUS.register(new Events());
		MinecraftForge.EVENT_BUS.register(new KeyboardFix());
	}

	public static void openConfigGui() {
		if (editor == null) {
			MoulConfigProcessor<SMSConfig> processor = MoulConfigProcessor.withDefaults(config);
			new ConfigProcessorDriver(processor).processConfig(config);
			editor = new MoulConfigEditor(processor);
		}
		Minecraft.getMinecraft().displayGuiScreen(new GuiScreenElementWrapper(editor));
	}

	public static void saveConfig() {
		try (FileWriter w = new FileWriter(configFile)) {
			new Gson().toJson(config, w);
		} catch (IOException e) { e.printStackTrace(); }
	}

	public static void loadConfig() {
		if (configFile.exists()) {
			try (FileReader r = new FileReader(configFile)) {
				config = new Gson().fromJson(r, SMSConfig.class);
			} catch (IOException e) { e.printStackTrace(); }
		}
	}
}
