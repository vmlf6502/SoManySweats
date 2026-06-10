package com.replaymod.sms.tab;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.replaymod.sms.SoManySweats.config;

public class RenderStats {
    @SubscribeEvent
    public void onTabListRender(RenderGameOverlayEvent.Pre event) {
        if (event.type != RenderGameOverlayEvent.ElementType.PLAYER_LIST) return;
        if (config.getInstance().statsSettings.customTab) {
            event.setCanceled(true);
            CustomTab.renderTabList();
        } else {
            DefaultTab.render();
        }
    }

    static @NotNull ArrayList<String> getStatsShown() {
        ArrayList<String> statsShown = new ArrayList<>();

        // BEDWARS
        if (config.getInstance().statsSettings.bedwars.level) statsShown.add("level");
        if (config.getInstance().statsSettings.bedwars.fkdr) statsShown.add("fkdr");
        if (config.getInstance().statsSettings.bedwars.winstreak) statsShown.add("winstreak");
        if (config.getInstance().statsSettings.bedwars.wlr) statsShown.add("wlr");

        // SKYWARS
        if (config.getInstance().statsSettings.skywars.winstreak) statsShown.add("skwinstreak");
        if (config.getInstance().statsSettings.skywars.kdr) statsShown.add("kdr");

        // CUSTOM
        if (config.getInstance().statsSettings.custom.custom1.enabled) statsShown.add("custom1");
        if (config.getInstance().statsSettings.custom.custom2.enabled) statsShown.add("custom2");
        if (config.getInstance().statsSettings.custom.custom3.enabled) statsShown.add("custom3");
        return statsShown;
    }
}
