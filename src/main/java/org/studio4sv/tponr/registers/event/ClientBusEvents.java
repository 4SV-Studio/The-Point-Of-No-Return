package org.studio4sv.tponr.registers.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.client.hud.HungerHud;
import org.studio4sv.tponr.client.hud.StaminaHud;
import org.studio4sv.tponr.client.hud.XpHud;

public class ClientBusEvents {
    @Mod.EventBusSubscriber(modid = TPONR.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll( TPONR.MOD_ID + "_hunger", HungerHud.HUD_HUNGER);
            event.registerAboveAll(TPONR.MOD_ID + "_stamina", StaminaHud.HUD_STAMINA);
            event.registerAboveAll(TPONR.MOD_ID + "_xp", XpHud.HUD_XP);
        }
    }
}
