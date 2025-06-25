package org.studio4sv.tponr.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class UpgradeScreenKeyHandler {
    public static final String CATEGORY = "key.categories.tponr";

    public static final KeyMapping OPEN_UPGRADE_SCREEN = new KeyMapping(
            "key.tponr.open_upgrade_screen",
            InputConstants.KEY_U,
            CATEGORY
    );

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(OPEN_UPGRADE_SCREEN);
    }
}