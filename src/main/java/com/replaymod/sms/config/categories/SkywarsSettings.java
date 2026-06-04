package com.replaymod.sms.config.categories;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class SkywarsSettings {

    @Expose
    @ConfigOption(name = "Winstreak", desc = "Show skywars winstreak on the tablist")
    @ConfigEditorBoolean
    public boolean showWinstreak = true;

}