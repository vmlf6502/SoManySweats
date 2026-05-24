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

package com.replaymod.sms.config;

import com.google.gson.annotations.Expose;
import com.replaymod.sms.SoManySweats;
import com.replaymod.sms.util.Logger;
import io.github.notenoughupdates.moulconfig.Config;
import io.github.notenoughupdates.moulconfig.annotations.Category;
import io.github.notenoughupdates.moulconfig.common.text.StructuredText;
public class SMSConfig extends Config {
	@Override
	public StructuredText getTitle() {
		return StructuredText.of("§7SoManySweats by §5vmlf");
	}

	@Override
	public void saveNow() {
		SoManySweats.config.saveToFile();
	}

	@Expose
	@Category(
		name = "API",
		desc = "API Data"
	)
	public SMSApiData apiData = new SMSApiData();
}
