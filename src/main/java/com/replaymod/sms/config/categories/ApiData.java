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

package com.replaymod.sms.config.categories;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorText;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;


public class ApiData {
	@Expose
	@ConfigOption(
		name = "Developer Mode",
		desc = "Enable Developer Mode to use your own API key for development of SoManySweats"
	)
	@ConfigEditorBoolean
	public boolean developerMode = false;

	@Expose
	@ConfigOption(
		name = "Developer API Key",
		desc = "§7Get an API key from the Hypixel Developer Dashboard at https://developer.hypixel.net/dashboard"
	)
	@ConfigEditorText
	public String apiKey = "";
}
