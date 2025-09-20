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
import org.studio4sv.tponr.blocks.entity.FilterBlockEntity;
import org.studio4sv.tponr.blocks.entity.SuitCharger.SuitChargerBlockEntity;
import org.studio4sv.tponr.blocks.entity.SuitCharger.SuitChargerSubBlockEntity;
import org.studio4sv.tponr.blocks.entity.SuitDyer.SuitDyerBlockEntity;
import org.studio4sv.tponr.blocks.entity.SuitDyer.SuitDyerSubBlockEntity;

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

    public static final RegistryObject<BlockEntityType<SuitDyerBlockEntity>> SUIT_DYER_ENTITY =
            BLOCK_ENTITIES.register("suit_dyer_block_entity", () ->
                    BlockEntityType.Builder.of(SuitDyerBlockEntity::new,
                            ModBlocks.DYER.get()).build(null));

    public static final RegistryObject<BlockEntityType<SuitDyerSubBlockEntity>> SUIT_DYER_SUB_ENTITY =
            BLOCK_ENTITIES.register("suit_dyer_sub_block_entity", () ->
                    BlockEntityType.Builder.of(SuitDyerSubBlockEntity::new,
                            ModBlocks.DYER_SUB.get()).build(null));

    public static final RegistryObject<BlockEntityType<SuitChargerBlockEntity>> SUIT_CHARGER_ENTITY =
            BLOCK_ENTITIES.register("suit_charger_block_entity", () ->
                    BlockEntityType.Builder.of(SuitChargerBlockEntity::new,
                            ModBlocks.CHARGER.get()).build(null));

    public static final RegistryObject<BlockEntityType<SuitChargerSubBlockEntity>> SUIT_CHARGER_SUB_ENTITY =
            BLOCK_ENTITIES.register("suit_charger_sub_block_entity", () ->
                    BlockEntityType.Builder.of(SuitChargerSubBlockEntity::new,
                            ModBlocks.CHARGER.get()).build(null));

    public static final RegistryObject<BlockEntityType<FilterBlockEntity>> FILTER_ENTITY =
            BLOCK_ENTITIES.register("filter_block_entity", () ->
                    BlockEntityType.Builder.of(FilterBlockEntity::new,
                            ModBlocks.FILTER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
