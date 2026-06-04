package com.replaymod.sms.config.categories;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorText;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class CustomSettings {

    @Expose
    @ConfigOption(name = "Custom 1", desc = "API path")
    @ConfigEditorText
    public String custom1 = "newPackageRank";

    @Expose
    @ConfigOption(name = "Custom 2", desc = "API path")
    @ConfigEditorText
    public String custom2 = "stats/Bedwars/bw_unique_challenges_completed";

    @Expose
    @ConfigOption(name = "Custom 3", desc = "API path")
    @ConfigEditorText
    public String custom3 = "socialMedia/links/DISCORD";
}