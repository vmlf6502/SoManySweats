package com.replaymod.sms.config.categories;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorText;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class StatsSettings {
    @Expose
    @ConfigOption(
            name = "Bedwars Level",
            desc = "Show the Bedwars Level of players in tab"
    )
    @ConfigEditorBoolean
    public boolean showLevel = true;

    @Expose
    @ConfigOption(
            name = "FKDR",
            desc = "Show the FKDR of players in tab"
    )
    @ConfigEditorBoolean
    public boolean showFkdr = true;

    @Expose
    @ConfigOption(
            name = "Winstreak",
            desc = "Show the Winstreak of players in tab"
    )
    @ConfigEditorBoolean
    public boolean showWinstreak = true;

    @Expose
    @ConfigOption(
            name = "Custom Endpoint 1",
            desc = "Get a custom data value from the /v2/player endpoint of the Hypixel API"
    )
    @ConfigEditorText
    public String custom1 = "newPackageRank";

    @Expose
    @ConfigOption(
            name = "Custom Endpoint 2",
            desc = "Get a custom data value from the /v2/player endpoint of the Hypixel API"
    )
    @ConfigEditorText
    public String custom2 = "stats/Bedwars/bw_unique_challenges_completed";

    @Expose
    @ConfigOption(
            name = "Custom Endpoint 3",
            desc = "Get a custom data value from the /v2/player endpoint of the Hypixel API"
    )
    @ConfigEditorText
    public String custom3 = "socialMedia/links/DISCORD";


}
