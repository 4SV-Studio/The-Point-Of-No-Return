package org.studio4sv.tponr;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeableLeatherItem;
import org.studio4sv.tponr.client.entity.BunkerDoorBlockItem.BunkerDoorBlockRenderer;
import org.studio4sv.tponr.client.entity.ClockBlockItem.ClockBlockRenderer;
import org.studio4sv.tponr.client.entity.FilterBlockItem.FilterBlockRenderer;
import org.studio4sv.tponr.client.entity.SuitChargerBlockItem.SuitChargerRenderer;
import org.studio4sv.tponr.client.entity.SuitDyerBlockItem.SuitDyerBlockRenderer;
import org.studio4sv.tponr.networking.ModMessages;
import org.studio4sv.tponr.registers.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

@Mod(TPONR.MOD_ID)
public class TPONR
{
    public static final String MOD_ID = "tponr";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public TPONR(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModCreativeModTabs.register(modEventBus);
        ModMenus.register();

        GeckoLib.initialize();

        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("The Point Of No Return is starting");

        ModMessages.register();
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            BlockEntityRenderers.register(ModBlockEntities.BUNKER_DOOR_ENTITY.get(), BunkerDoorBlockRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.CLOCK_ENTITY.get(), ClockBlockRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.SUIT_DYER_ENTITY.get(), SuitDyerBlockRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.SUIT_CHARGER_ENTITY.get(), SuitChargerRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.FILTER_ENTITY.get(), FilterBlockRenderer::new);

            Minecraft.getInstance().getItemColors().register(
                    (stack, tintIndex) -> tintIndex == 0 ? ((DyeableLeatherItem) stack.getItem()).getColor(stack) : -1,
                    ModItems.HAZMAT_SUIT_HELMET.get(),
                    ModItems.HAZMAT_SUIT_CHESTPLATE.get(),
                    ModItems.HAZMAT_SUIT_LEGGINGS.get(),
                    ModItems.HAZMAT_SUIT_BOOTS.get(),
                    ModItems.HAZMAT_SUIT_PACK.get()
            );
        }
    }
}
