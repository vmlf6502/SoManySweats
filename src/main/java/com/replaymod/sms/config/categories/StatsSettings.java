package com.replaymod.sms.config.categories;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.Category;

public class StatsSettings {

    @Expose
    @Category(name = "Bedwars", desc = "Bedwars stats shown in tab")
    public BedwarsSettings bedwars = new BedwarsSettings();

    @Expose
    @Category(name = "Skywars", desc = "Skywars stats shown in tab")
    public SkywarsSettings skywars = new SkywarsSettings();

    @Expose
    @Category(name = "Custom Endpoints", desc = "Custom API values")
    public CustomSettings custom = new CustomSettings();
}