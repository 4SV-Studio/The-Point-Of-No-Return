package org.studio4sv.ponr.event;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.studio4sv.ponr.client.hud.XpHud;
import org.studio4sv.ponr.util.xpConverter;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void disableHud(RenderGuiOverlayEvent.Pre event) {
        if (event.getOverlay() == VanillaGuiOverlay.FOOD_LEVEL.type())
            event.setCanceled(true);
        if (event.getOverlay() == VanillaGuiOverlay.EXPERIENCE_BAR.type())
            event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onXpChange(PlayerXpEvent.XpChange event) {
        Player player = event.getEntity();

        int level = player.experienceLevel;
        float progress = player.experienceProgress;

        int totalXP = xpConverter.calculate(level, progress);

        XpHud.setPoints(totalXP);
    }
}
