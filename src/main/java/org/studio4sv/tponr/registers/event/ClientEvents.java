package org.studio4sv.tponr.registers.event;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.studio4sv.tponr.client.UpgradeScreenKeyHandler;
import org.studio4sv.tponr.client.gui.UpgradeScreen;
import org.studio4sv.tponr.client.hud.XpHud;
import org.studio4sv.tponr.util.xpConverter;

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
    public static void onTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        int level = player.experienceLevel;
        float progress = player.experienceProgress;
        int totalXP = xpConverter.calculate(level, progress);
        XpHud.setPoints(totalXP);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (UpgradeScreenKeyHandler.OPEN_UPGRADE_SCREEN.consumeClick()) {
            Minecraft.getInstance().setScreen(new UpgradeScreen());
        }
    }
}
