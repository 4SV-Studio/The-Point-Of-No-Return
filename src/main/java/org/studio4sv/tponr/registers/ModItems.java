package org.studio4sv.tponr.registers;

import net.minecraft.world.item.ArmorItem;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.armor.HazmatSuitItem;
import org.studio4sv.tponr.items.BunkerDoorBlockItem;
import org.studio4sv.tponr.items.ClockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.studio4sv.tponr.util.ModArmorMaterials;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final List<RegistryObject<Item>> registeredSuitPacks = new ArrayList<>();
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TPONR.MOD_ID);


    public static final RegistryObject<Item> BUNKER_DOOR_ITEM = ITEMS.register("bunker_door",
            () -> new BunkerDoorBlockItem(ModBlocks.BUNKER_DOOR.get(), new Item.Properties()));

    public static final RegistryObject<Item> CLOCK_ITEM = ITEMS.register("clock",
            () -> new ClockItem(ModBlocks.CLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> HAZMAT_SUIT_HELMET_ITEM = ITEMS.register("hazmat_suit_helmet",
            () -> new HazmatSuitItem(ModArmorMaterials.HAZMAT_SUIT, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> HAZMAT_SUIT_CHESTPLATE_ITEM = ITEMS.register("hazmat_suit_chestplate",
            () -> new HazmatSuitItem(ModArmorMaterials.HAZMAT_SUIT, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> HAZMAT_SUIT_LEGGINGS_ITEM = ITEMS.register("hazmat_suit_leggings",
            () -> new HazmatSuitItem(ModArmorMaterials.HAZMAT_SUIT, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> HAZMAT_SUIT_BOOTS_ITEM = ITEMS.register("hazmat_suit_boots",
            () -> new HazmatSuitItem(ModArmorMaterials.HAZMAT_SUIT, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
