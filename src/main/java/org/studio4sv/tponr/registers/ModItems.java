package org.studio4sv.tponr.registers;

import net.minecraft.world.item.ArmorItem;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.armor.HazmatSuitItem;
import org.studio4sv.tponr.items.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.studio4sv.tponr.util.armor.ModArmorMaterials;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TPONR.MOD_ID);

    public static final RegistryObject<Item> HAZMAT_SUIT_HELMET = ITEMS.register("hazmat_suit_helmet",
            () -> new HazmatSuitItem(ModArmorMaterials.HAZMAT_SUIT, ArmorItem.Type.HELMET, new Item.Properties().durability(-1)));
    public static final RegistryObject<Item> HAZMAT_SUIT_CHESTPLATE = ITEMS.register("hazmat_suit_chestplate",
            () -> new HazmatSuitItem(ModArmorMaterials.HAZMAT_SUIT, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(-1)));
    public static final RegistryObject<Item> HAZMAT_SUIT_LEGGINGS = ITEMS.register("hazmat_suit_leggings",
            () -> new HazmatSuitItem(ModArmorMaterials.HAZMAT_SUIT, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(-1)));
    public static final RegistryObject<Item> HAZMAT_SUIT_BOOTS = ITEMS.register("hazmat_suit_boots",
            () -> new HazmatSuitItem(ModArmorMaterials.HAZMAT_SUIT, ArmorItem.Type.BOOTS, new Item.Properties().durability(-1)));

    public static final RegistryObject<Item> HAZMAT_SUIT_PACK = ITEMS.register("hazmat_suit_pack",
            () -> new HazmatSuitPackItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> FAKE_SUIT = ITEMS.register("fake_suit",
            () -> new FakeSuitItem(new Item.Properties()));

    public static final RegistryObject<Item> STEEL_MIX = ITEMS.register("steel_mix",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CHARGER_COMPONENT = ITEMS.register("charger_component",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PROPELLER = ITEMS.register("propeller",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> INPUT_COMPONENT = ITEMS.register("input_component",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DYE_CARTRIDGE = ITEMS.register("dye_cartridge",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ANTI_SCINT = ITEMS.register("anti_scint",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
