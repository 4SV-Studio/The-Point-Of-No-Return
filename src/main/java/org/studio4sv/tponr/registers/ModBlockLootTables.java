package org.studio4sv.tponr.registers;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.BUNKER_DOOR.get());
        dropSelf(ModBlocks.BUNKER_DOOR_SUB.get());
        dropSelf(ModBlocks.CLOCK.get());
        dropSelf(ModBlocks.SUIT_CHARGER.get());
        dropSelf(ModBlocks.SUIT_CHARGER_SUB.get());
        dropSelf(ModBlocks.SUIT_DYER.get());
        dropSelf(ModBlocks.SUIT_DYER_SUB.get());
    }


    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).toList();
    }
}
