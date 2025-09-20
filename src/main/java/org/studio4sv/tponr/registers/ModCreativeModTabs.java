package org.studio4sv.tponr.registers;

import net.minecraft.nbt.CompoundTag;
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
            () -> CreativeModeTab.builder().icon(() -> {
                        ItemStack icon = new ItemStack(ModItems.HAZMAT_SUIT_PACK.get());
                        CompoundTag tag = icon.getOrCreateTagElement("display");
                        tag.putInt("color", 0xFF6400);
                        return icon;
                    })
                    .title(Component.translatable("creativetab." + TPONR.MOD_ID + "_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(ModBlocks.BUNKER_DOOR.get());
                        pOutput.accept(ModBlocks.CLOCK.get());
                        ItemStack suit_pack = new ItemStack(ModItems.HAZMAT_SUIT_PACK.get());
                        suit_pack.getOrCreateTag().putFloat("charge", 100f);
                        pOutput.accept(suit_pack);
                        pOutput.accept(ModBlocks.DYER.get());
                        pOutput.accept(ModBlocks.CHARGER.get());
                        pOutput.accept(ModBlocks.FILTER.get());
                        pOutput.accept(ModItems.STEEL_MIX.get());
                        pOutput.accept(ModItems.STEEL_INGOT.get());
                        pOutput.accept(ModItems.CHARGER_COMPONENT.get());
                        pOutput.accept(ModItems.FAN.get());
                        pOutput.accept(ModItems.ANTI_SCINT.get());
                        pOutput.accept(ModBlocks.STEEL_BLOCK.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}