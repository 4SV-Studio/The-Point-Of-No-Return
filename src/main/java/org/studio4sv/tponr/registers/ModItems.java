package org.studio4sv.tponr.registers;

import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.items.BunkerDoorBlockItem;
import org.studio4sv.tponr.items.ClockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TPONR.MOD_ID);

    public static final RegistryObject<Item> BUNKER_DOOR_ITEM = ITEMS.register("bunker_door",
            () -> new BunkerDoorBlockItem(ModBlocks.BUNKER_DOOR.get(), new Item.Properties()));

    public static final RegistryObject<Item> CLOCK_ITEM = ITEMS.register("clock",
            () -> new ClockItem(ModBlocks.CLOCK.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
