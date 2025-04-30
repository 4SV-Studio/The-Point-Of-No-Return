package dev.lemonnik.ponr;

import com.mojang.logging.LogUtils;
import dev.lemonnik.ponr.client.entity.BunkerDoorBlockItem.BunkerDoorBlockRenderer;
import dev.lemonnik.ponr.client.entity.ClockItem.ClockRenderer;
import dev.lemonnik.ponr.registers.ModBlockEntities;
import dev.lemonnik.ponr.registers.ModBlocks;
import dev.lemonnik.ponr.registers.ModCreativeModTabs;
import dev.lemonnik.ponr.registers.ModItems;
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

@Mod(PONR.MOD_ID)
public class PONR
{
    public static final String MOD_ID = "ponr";
    private static final Logger LOGGER = LogUtils.getLogger();
    
    public PONR(FMLJavaModLoadingContext context)
    {
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
