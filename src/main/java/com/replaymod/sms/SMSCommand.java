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

package com.replaymod.sms;

import com.replaymod.sms.events.TriggerOpenConfig;
import com.replaymod.sms.util.ApiHandler;
import com.replaymod.sms.util.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.replaymod.sms.SoManySweats.STATS;

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
	public String getCommandUsage(ICommandSender sender) {
		return null;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args == null) return;
		if (args.length == 0 || args[0].equals("settings")) {
			TriggerOpenConfig.triggerConfig = true;
		} else if (args[0].equals("fetch")) {
			ApiHandler.fetchPlayerStats();
		} else if (args[0].equals("clear")) {
			STATS.clear();
			Logger.log(EnumChatFormatting.GREEN + "Successfully cleared stats.");
		}
//		} else {
//			Scoreboard scoreboard = Minecraft.getMinecraft().theWorld.getScoreboard();
//			ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1);
//
//			if (objective != null) {
//				Collection<Score> scores = scoreboard.getSortedScores(objective);
//				List<Score> scoreList = new ArrayList<>(scores);
//				Collections.reverse(scoreList); // top to bottom order
//
//
//				for (Score score : scoreList) {
//					String playerName = score.getPlayerName();
//
//					System.out.println(playerName);
//				}
//			}
//		}
	}


}
