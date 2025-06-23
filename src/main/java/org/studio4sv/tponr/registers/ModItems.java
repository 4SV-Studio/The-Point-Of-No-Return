package org.studio4sv.tponr.registers;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.items.BunkerDoorBlockItem;
import org.studio4sv.tponr.items.ClockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.studio4sv.tponr.items.HazmatSuits.*;
import org.studio4sv.tponr.util.ModArmorMaterials;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final List<RegistryObject<Item>> registeredSuitPacks = new ArrayList<>();
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TPONR.MOD_ID);

    private static void registerSuitItems(String color, Class<? extends BaseHazmatSuitItem> suitClass) {
        ITEMS.register(color + "suit_helmet",
                () -> {
                    try {
                        Constructor<? extends BaseHazmatSuitItem> constructor = suitClass.getConstructor(ArmorMaterial.class, ArmorItem.Type.class, Item.Properties.class);
                        return constructor.newInstance(ModArmorMaterials.HARAZM_SUIT, ArmorItem.Type.HELMET, new Item.Properties());
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to register " + color + " hazmat suit helmet!", e);
                    }
                });
        ITEMS.register(color + "suit_chestplate",
                () -> {
                    try {
                        Constructor<? extends BaseHazmatSuitItem> constructor = suitClass.getConstructor(ArmorMaterial.class, ArmorItem.Type.class, Item.Properties.class);
                        return constructor.newInstance(ModArmorMaterials.HARAZM_SUIT, ArmorItem.Type.CHESTPLATE, new Item.Properties());
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to register " + color + " hazmat suit chestplate!", e);
                    }
                });
        ITEMS.register(color + "suit_leggings",
                () -> {
                    try {
                        Constructor<? extends BaseHazmatSuitItem> constructor = suitClass.getConstructor(ArmorMaterial.class, ArmorItem.Type.class, Item.Properties.class);
                        return constructor.newInstance(ModArmorMaterials.HARAZM_SUIT, ArmorItem.Type.LEGGINGS, new Item.Properties());
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to register " + color + " hazmat suit leggings!", e);
                    }
                });

        registeredSuitPacks.add(ITEMS.register(color + "suit_pack",
                () -> new BaseHazmatSuitPackItem() {
                    @Override
                    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
                        return super.onItemUseFirst(stack, context);
                    }
                }));
    }

    public static final RegistryObject<Item> BUNKER_DOOR_ITEM = ITEMS.register("bunker_door",
            () -> new BunkerDoorBlockItem(ModBlocks.BUNKER_DOOR.get(), new Item.Properties()));

    public static final RegistryObject<Item> CLOCK_ITEM = ITEMS.register("clock",
            () -> new ClockItem(ModBlocks.CLOCK.get(), new Item.Properties()));



    static {
        registerSuitItems("orange_", OrangeSuitOneItem.class);
        registerSuitItems("green_", GreenSuitOneItem.class);
        registerSuitItems("blue_", BlueSuitOneItem.class);
    }



    public static final RegistryObject<Item> SUIT_ONE_BOOTS = ITEMS.register("hazmat_suit_boots",
            () -> new OrangeSuitOneItem(ModArmorMaterials.HARAZM_SUIT, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
