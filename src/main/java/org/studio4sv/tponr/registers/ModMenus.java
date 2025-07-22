package org.studio4sv.tponr.registers;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.client.gui.SuitDyerGui.SuitDyerMenu;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, TPONR.MOD_ID);

    public static final RegistryObject<MenuType<SuitDyerMenu>> SUIT_DYE_MENU =
            MENUS.register("suit_dye_menu", () ->
                    IForgeMenuType.create((windowId, inv, data) -> new SuitDyerMenu(
                            windowId,
                            data.readBlockPos(),
                            inv,
                            inv.player)
                    )
            );

    @SuppressWarnings("removal")
    public static void register() {
        MENUS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
