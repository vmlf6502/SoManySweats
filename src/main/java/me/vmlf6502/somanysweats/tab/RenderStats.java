package me.vmlf6502.somanysweats.tab;

import me.vmlf6502.somanysweats.util.StatKey;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static me.vmlf6502.somanysweats.SoManySweats.config;

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

    static @NotNull ArrayList<StatKey> getStatsShown() {
        ArrayList<StatKey> statsShown = new ArrayList<>();

        // BEDWARS
        if (config.getInstance().statsSettings.bedwars.level) statsShown.add(StatKey.BEDWARS_LEVEL);
        if (config.getInstance().statsSettings.bedwars.fkdr) statsShown.add(StatKey.BEDWARS_FKDR);
        if (config.getInstance().statsSettings.bedwars.winstreak) statsShown.add(StatKey.BEDWARS_WINSTREAK);
        if (config.getInstance().statsSettings.bedwars.wlr) statsShown.add(StatKey.BEDWARS_WLR);

        // SKYWARS
        if (config.getInstance().statsSettings.skywars.winstreak) statsShown.add(StatKey.SKYWARS_WINSTREAK);
        if (config.getInstance().statsSettings.skywars.kdr) statsShown.add(StatKey.SKYWARS_KDR);

        // CUSTOM
        if (config.getInstance().statsSettings.custom.one.enabled) statsShown.add(StatKey.CUSTOM_1);
        if (config.getInstance().statsSettings.custom.two.enabled) statsShown.add(StatKey.CUSTOM_2);
        if (config.getInstance().statsSettings.custom.three.enabled) statsShown.add(StatKey.CUSTOM_3);
        return statsShown;
    }
}
