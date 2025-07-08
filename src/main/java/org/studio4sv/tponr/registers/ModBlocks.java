package org.studio4sv.tponr.registers;

import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.blocks.custom.BunkerDoor.BunkerDoorBlock;
import org.studio4sv.tponr.blocks.custom.BunkerDoor.BunkerDoorSubBlock;
import org.studio4sv.tponr.blocks.custom.Clock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TPONR.MOD_ID);

    public static final RegistryObject<Block> BUNKER_DOOR = BLOCKS.register("bunker_door_block",
            () -> new BunkerDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).isSuffocating((state, level, pos) -> false).noOcclusion().destroyTime(-1)));

    public static final RegistryObject<Block> BUNKER_DOOR_SUB = BLOCKS.register("bunker_door_sub_block",
            () -> new BunkerDoorSubBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).isSuffocating((state, level, pos) -> false).noOcclusion().destroyTime(-1)));

    public static final RegistryObject<Block> CLOCK = BLOCKS.register("clock",
            () -> new Clock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

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
