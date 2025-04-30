package org.studio4sv.ponr.registers;

import org.studio4sv.ponr.PONR;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PONR.MOD_ID);

    public static final RegistryObject<CreativeModeTab> PONR_TAB = CREATIVE_MODE_TABS.register("ponr_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.BUNKER_DOOR_ITEM.get()))
                    .title(Component.translatable("creativetab.ponr_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.BUNKER_DOOR_ITEM.get());
                        pOutput.accept(ModItems.CLOCK_ITEM.get());
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}