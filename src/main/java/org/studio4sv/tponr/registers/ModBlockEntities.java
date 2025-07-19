package org.studio4sv.tponr.registers;

import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.blocks.entity.BunkerDoor.BunkerDoorBlockEntity;
import org.studio4sv.tponr.blocks.entity.BunkerDoor.BunkerDoorSubBlockEntity;
import org.studio4sv.tponr.blocks.entity.ClockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TPONR.MOD_ID);

    public static final RegistryObject<BlockEntityType<BunkerDoorBlockEntity>> BUNKER_DOOR_ENTITY =
            BLOCK_ENTITIES.register("bunker_door_block_entity", () ->
                    BlockEntityType.Builder.of(BunkerDoorBlockEntity::new,
                            ModBlocks.BUNKER_DOOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<BunkerDoorSubBlockEntity>> BUNKER_DOOR_SUB_ENTITY =
            BLOCK_ENTITIES.register("bunker_door_sub_block_entity", () ->
                    BlockEntityType.Builder.of(BunkerDoorSubBlockEntity::new,
                            ModBlocks.BUNKER_DOOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<ClockEntity>> CLOCK_ENTITY =
            BLOCK_ENTITIES.register("clock_entity", () ->
                    BlockEntityType.Builder.of(ClockEntity::new,
                            ModBlocks.CLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
