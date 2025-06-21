package org.studio4sv.tponr.registers;

import net.minecraft.world.item.ArmorItem;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.items.BunkerDoorBlockItem;
import org.studio4sv.tponr.items.ClockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.studio4sv.tponr.items.HazmatSuits.OrangeSuitOneItem;
import org.studio4sv.tponr.util.ModArmorMaterials;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TPONR.MOD_ID);

    public static final RegistryObject<Item> BUNKER_DOOR_ITEM = ITEMS.register("bunker_door",
            () -> new BunkerDoorBlockItem(ModBlocks.BUNKER_DOOR.get(), new Item.Properties()));

    public static final RegistryObject<Item> CLOCK_ITEM = ITEMS.register("clock",
            () -> new ClockItem(ModBlocks.CLOCK.get(), new Item.Properties()));



    public static final RegistryObject<Item> ORANGE_SUIT_ONE_HELMET = ITEMS.register("orange_hazard_suit_one_helmet",
            () -> new OrangeSuitOneItem(ModArmorMaterials.HARAZM_SUIT, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> ORANGE_SUIT_ONE_CHESTPLATE = ITEMS.register("orange_hazard_suit_one_chestplate",
            () -> new OrangeSuitOneItem(ModArmorMaterials.HARAZM_SUIT, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> ORANGE_SUIT_ONE_LEGGINGS = ITEMS.register("orange_hazard_suit_one_leggings",
            () -> new OrangeSuitOneItem(ModArmorMaterials.HARAZM_SUIT, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> ORANGE_SUIT_ONE_BOOTS = ITEMS.register("orange_hazard_suit_one_boots",
            () -> new OrangeSuitOneItem(ModArmorMaterials.HARAZM_SUIT, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
