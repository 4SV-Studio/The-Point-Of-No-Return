package org.studio4sv.tponr.registers.event;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.client.gui.SuitDyerGui.SuitDyerScreen;
import org.studio4sv.tponr.client.hud.HungerHud;
import org.studio4sv.tponr.client.hud.StaminaHud;
import org.studio4sv.tponr.client.hud.SuitOverlayHud;
import org.studio4sv.tponr.client.hud.XpHud;
import org.studio4sv.tponr.registers.ModMenus;

public class ClientBusEvents {
    @Mod.EventBusSubscriber(modid = TPONR.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                MenuScreens.register(ModMenus.SUIT_DYE_MENU.get(), SuitDyerScreen::new);
            });
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll( TPONR.MOD_ID + "_hunger", HungerHud.HUD_HUNGER);
            event.registerAboveAll(TPONR.MOD_ID + "_stamina", StaminaHud.HUD_STAMINA);
            event.registerAboveAll(TPONR.MOD_ID + "_xp", XpHud.HUD_XP);
            event.registerAboveAll(TPONR.MOD_ID + "_suit_overlay", SuitOverlayHud.HUD_OVERLAY);
        }
    }
}
