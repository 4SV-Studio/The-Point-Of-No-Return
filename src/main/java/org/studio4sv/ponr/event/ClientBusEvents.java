package org.studio4sv.ponr.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.studio4sv.ponr.PONR;
import org.studio4sv.ponr.client.hud.HungerHud;
import org.studio4sv.ponr.client.hud.StaminaHud;
import org.studio4sv.ponr.client.hud.XpHud;

public class ClientBusEvents {
    @Mod.EventBusSubscriber(modid = PONR.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("ponr_hunger", HungerHud.HUD_HUNGER);
            event.registerAboveAll("ponr_stamina", StaminaHud.HUD_STAMINA);
            event.registerAboveAll("ponr_xp", XpHud.HUD_XP);
        }
    }
}
