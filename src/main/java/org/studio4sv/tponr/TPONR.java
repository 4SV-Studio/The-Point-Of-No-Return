package org.studio4sv.tponr;

import com.mojang.logging.LogUtils;
import net.minecraftforge.event.RegisterCommandsEvent;
import org.studio4sv.tponr.client.entity.BunkerDoorBlockItem.BunkerDoorBlockRenderer;
import org.studio4sv.tponr.client.entity.ClockItem.ClockRenderer;
import org.studio4sv.tponr.commands.HUDControllerCommands;
import org.studio4sv.tponr.networking.ModMessages;
import org.studio4sv.tponr.registers.ModBlockEntities;
import org.studio4sv.tponr.registers.ModBlocks;
import org.studio4sv.tponr.registers.ModCreativeModTabs;
import org.studio4sv.tponr.registers.ModItems;
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

    public TPONR(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);

        ModCreativeModTabs.register(modEventBus);

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
            BlockEntityRenderers.register(ModBlockEntities.CLOCK_ENTITY.get(), ClockRenderer::new);
        }
    }
}
