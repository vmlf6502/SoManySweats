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

package com.replaymod.sms.config;

import com.google.gson.annotations.Expose;
import com.replaymod.sms.SoManySweats;
//import io.github.moulberry.notenoughupdates.NotEnoughUpdates;
//import io.github.moulberry.notenoughupdates.core.config.GuiPositionEditor;
//import io.github.moulberry.notenoughupdates.core.config.Position;
//import io.github.moulberry.notenoughupdates.dungeons.GuiDungeonMapEditor;
//import io.github.moulberry.notenoughupdates.miscfeatures.FairySouls;
//import io.github.moulberry.notenoughupdates.miscfeatures.IQTest;
//import io.github.moulberry.notenoughupdates.miscgui.GuiEnchantColour;
//import io.github.moulberry.notenoughupdates.miscgui.GuiInvButtonEditor;
//import io.github.moulberry.notenoughupdates.miscgui.NEUOverlayPlacements;
//import io.github.moulberry.notenoughupdates.options.separatesections.About;
//import io.github.moulberry.notenoughupdates.overlays.OverlayManager;
//import io.github.moulberry.notenoughupdates.overlays.TextOverlay;
//import io.github.moulberry.notenoughupdates.util.NotificationHandler;
import io.github.notenoughupdates.moulconfig.Config;
import io.github.notenoughupdates.moulconfig.Social;
import io.github.notenoughupdates.moulconfig.annotations.Category;
import io.github.notenoughupdates.moulconfig.common.text.StructuredText;
import io.github.notenoughupdates.moulconfig.gui.GuiScreenElementWrapper;
import io.github.notenoughupdates.moulconfig.gui.MoulConfigEditor;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;

import java.util.Collections;
import java.util.List;

public class SMSConfig extends Config {
//	public void editOverlay() {
//		final LinkedHashMap<TextOverlay, Position> overlayPositions = new LinkedHashMap<TextOverlay, Position>();
//		for (TextOverlay overlay : OverlayManager.textOverlays) {
//			overlayPositions.put(overlay, overlay.getPosition());
//		}
//		GuiScreen savedGui = Minecraft.getMinecraft().currentScreen;
//		Minecraft.getMinecraft().displayGuiScreen(new GuiPositionEditor(
//			overlayPositions, () -> {
//		}, () -> NotEnoughUpdates.INSTANCE.openGui = savedGui
//		));
//	}

	@Override
	public void saveNow() {
		SoManySweats.INSTANCE.saveConfig();
	}

	@Override
	public List<Social> getSocials() {
		return Collections.emptyList();
	}

	@Override
	public StructuredText getTitle() {
		return StructuredText.of("§7SoManySweats by §5aRustacean");
	}

	@Override
	public void executeRunnable(int runnableId) {
		String activeConfigCategory = null;
		if (Minecraft.getMinecraft().currentScreen instanceof GuiScreenElementWrapper) {
			GuiScreenElementWrapper wrapper =
				(GuiScreenElementWrapper) Minecraft.getMinecraft().currentScreen;
			if (wrapper.element instanceof MoulConfigEditor) {
				activeConfigCategory = ((MoulConfigEditor) wrapper.element).getSelectedCategory();
			}
		}

//		switch (runnableId) {
//			case -1:
//				return;
//			case 0:
//				GuiScreen savedGui = Minecraft.getMinecraft().currentScreen;
//				NotEnoughUpdates.INSTANCE.openGui = new GuiDungeonMapEditor(() -> {
//					NotEnoughUpdates.INSTANCE.openGui = savedGui;
//				});
//				return;
//			case 1:
//			case 4:
//				editOverlay();
//				return;
//			case 6:
//				NotEnoughUpdates.INSTANCE.openGui = new NEUOverlayPlacements();
//				return;
//			case 7:
//				NotEnoughUpdates.INSTANCE.openGui = new GuiInvButtonEditor();
//				return;
//			case 8:
//				NotEnoughUpdates.INSTANCE.openGui = new GuiEnchantColour();
//				return;
//			case 12:
//				executeRunnableCommand("/dn");
//				return;
//			case 13:
//				executeRunnableCommand("/pv");
//				return;
//			case 15:
//				String command = NotEnoughUpdates.INSTANCE.config.misc.fariySoul ? "/neusouls on" : "/neusouls off";
//				executeRunnableCommand(command);
//				return;
//			case 16:
//				executeRunnableCommand("/neusouls clear");
//				return;
//			case 17:
//				executeRunnableCommand("/neusouls unclear");
//				return;
//			case 20:
//				FairySouls.getInstance().setTrackFairySouls(NotEnoughUpdates.INSTANCE.config.misc.trackFairySouls);
//				return;
//			case 21:
//				NotEnoughUpdates.INSTANCE.overlay.updateSearch();
//				return;
//			case 22:
//				NotEnoughUpdates.INSTANCE.manager
//					.userFacingRepositoryReload()
//					.thenAccept(strings ->
//						NotificationHandler.displayNotification(strings, true, true));
//				Minecraft.getMinecraft().displayGuiScreen(null);
//				return;
//			case 23:
//				NotEnoughUpdates.INSTANCE.config.apiData.repoUser = "NotEnoughUpdates";
//				NotEnoughUpdates.INSTANCE.config.apiData.repoName = "NotEnoughUpdates-REPO";
//				NotEnoughUpdates.INSTANCE.config.apiData.repoBranch = "master";
//				return;
//			case 26:
//				OverlayManager.powderGrindingOverlay.reset();
//				return;
//			case 27:
//				IQTest.testIQ();
//				return;
//			case 28:
//				executeRunnableCommand("/neuresetslotlocking");
//				return;
//			default:
//				System.err.printf("Unknown runnableId = %d in category %s%n", runnableId, activeConfigCategory);
//		}
	}

	/**
	 * Adds a check for the player being in a world before executing the given command
	 */
	private void executeRunnableCommand(String command) {
		if (Minecraft.getMinecraft().thePlayer == null) {
			System.err.println("Command (" + command + ") not executed since you are not in a world.");
			return;
		}
		ClientCommandHandler.instance.executeCommand(Minecraft.getMinecraft().thePlayer, command);
	}

	@Expose
	@Category(
		name = "API",
		desc = "API Data"
	)
	public SMSApiData apiData = new SMSApiData();
}
