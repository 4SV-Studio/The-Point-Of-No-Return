package org.studio4sv.tponr.registers;

import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.blocks.custom.BunkerDoor.BunkerDoorBlock;
import org.studio4sv.tponr.blocks.custom.BunkerDoor.BunkerDoorSubBlock;
import org.studio4sv.tponr.blocks.custom.ClockBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.studio4sv.tponr.blocks.custom.FilterBlock;
import org.studio4sv.tponr.blocks.custom.SuitCharger.SuitChargerBlock;
import org.studio4sv.tponr.blocks.custom.SuitCharger.SuitChargerSubBlock;
import org.studio4sv.tponr.blocks.custom.SuitDyer.SuitDyerBlock;
import org.studio4sv.tponr.blocks.custom.SuitDyer.SuitDyerSubBlock;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TPONR.MOD_ID);

    public static final RegistryObject<Block> BUNKER_DOOR = BLOCKS.register("bunker_door_block",
            () -> new BunkerDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).isSuffocating((state, level, pos) -> false).noOcclusion().destroyTime(-1)));

    public static final RegistryObject<Block> BUNKER_DOOR_SUB = BLOCKS.register("bunker_door_sub_block",
            () -> new BunkerDoorSubBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).isSuffocating((state, level, pos) -> false).noOcclusion().destroyTime(-1)));

    public static final RegistryObject<Block> CLOCK = BLOCKS.register("clock",
            () -> new ClockBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> SUIT_DYER = BLOCKS.register("suit_dyer_block",
            () -> new SuitDyerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> SUIT_DYER_SUB = BLOCKS.register("suit_dyer_sub_block",
            () -> new SuitDyerSubBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> SUIT_CHARGER = BLOCKS.register("suit_charger_block",
            () -> new SuitChargerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> SUIT_CHARGER_SUB = BLOCKS.register("suit_charger_sub_block",
            () -> new SuitChargerSubBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> FILTER = BLOCKS.register("filter_block",
            () -> new FilterBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> BLACK_BLOCK = BLOCKS.register("black_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK)
            )
    );

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
