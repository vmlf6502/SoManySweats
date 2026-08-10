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

package me.vmlf6502.somanysweats;

import me.vmlf6502.somanysweats.events.TriggerOpenConfig;
import me.vmlf6502.somanysweats.tab.RenderStats;
import me.vmlf6502.somanysweats.util.ApiHandler;
import me.vmlf6502.somanysweats.util.Logger;
import me.vmlf6502.somanysweats.util.StatKey;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static me.vmlf6502.somanysweats.SoManySweats.STATS;

public class SMSCommand extends CommandBase {
	@Override
	public String getCommandName() {
		return "sms";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) { return null; }

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args == null) return;
		if (args.length == 0 || args[0].equals("settings")) {
			TriggerOpenConfig.triggerConfig = true;
		} else if (args[0].equals("fetch")) {
			if (args.length < 2) {
				ApiHandler.fetchPlayerStats();
			} else {
				new Thread(() -> {
					try {
						Map<StatKey, String> stats = ApiHandler.getStatsOfPlayer(args[1]);
						if (Objects.equals(stats.get(StatKey.IS_NICKED), "true")) {
							Logger.log(EnumChatFormatting.GRAY + "Player " + EnumChatFormatting.GOLD + args[1] + EnumChatFormatting.GRAY + " not found.");
							return;
						}
						Logger.log(EnumChatFormatting.GOLD + args[1] + "'s Stats");
						for (StatKey key : RenderStats.getStatsShown()) {
							Logger.log(EnumChatFormatting.GRAY + " - " + key.name() + ": " + EnumChatFormatting.WHITE + stats.get(key));
						}
					} catch (Exception e) {
						Logger.log(EnumChatFormatting.RED + "Failed to fetch " + EnumChatFormatting.GOLD + args[1] + EnumChatFormatting.RED + "'s stats.");
						Logger.log(EnumChatFormatting.RED + e.getMessage());
						System.out.println(Arrays.toString(e.getStackTrace()));
					}
				}).start();
            }
		} else if (args[0].equals("clear")) {
			STATS.clear();
			Logger.log(EnumChatFormatting.GREEN + "Successfully cleared stats.");
		} else {
			sender.addChatMessage(new ChatComponentText(
				"/sms - Opens a GUI where you can configure your SoManySweats settings.\n" +
						"/sms fetch - Fetch the stats of the players in your game.\n" +
						"/sms clear - Clear the local stats cache so that you can request them from the API again."
			));
		}
	}


}
