package org.studio4sv.tponr.registers;

import org.studio4sv.tponr.TPONR;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TPONR.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TPONR_TAB = CREATIVE_MODE_TABS.register(TPONR.MOD_ID + "_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.FAKE_SUIT.get()))
                    .title(Component.translatable("creativetab." + TPONR.MOD_ID + "_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(ModItems.BUNKER_DOOR.get());
                        pOutput.accept(ModItems.CLOCK.get());
                        ItemStack suit_pack = new ItemStack(ModItems.HAZMAT_SUIT_PACK.get());
                        suit_pack.getOrCreateTag().putFloat("charge", 100f);
                        pOutput.accept(suit_pack);
                        pOutput.accept(ModItems.SUIT_DYER.get());
                        pOutput.accept(ModItems.CHARGER.get());
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}