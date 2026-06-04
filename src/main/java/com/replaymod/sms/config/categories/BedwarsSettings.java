package com.replaymod.sms.config.categories;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class BedwarsSettings {

    @Expose
    @ConfigOption(name = "Level", desc = "Show bedwars level on the tablist")
    @ConfigEditorBoolean
    public boolean showLevel = true;

    @Expose
    @ConfigOption(name = "FKDR", desc = "Show bedwars final kill/death ratio on the tablist")
    @ConfigEditorBoolean
    public boolean showFkdr = true;

    @Expose
    @ConfigOption(name = "Winstreak", desc = "Show bedwars winstreak on the tablist")
    @ConfigEditorBoolean
    public boolean showWinstreak = true;

    @Expose
    @ConfigOption(name = "Win/Loss Ratio", desc = "Show bedwars win/loss ratio on the tablist")
    @ConfigEditorBoolean
    public boolean showWlr = true;
}