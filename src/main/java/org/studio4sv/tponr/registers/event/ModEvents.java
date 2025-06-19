package org.studio4sv.tponr.registers.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.commands.HUDControllerCommands;
import org.studio4sv.tponr.mechanics.attributes.PlayerAttributesProvider;
import org.studio4sv.tponr.networking.ModMessages;
import org.studio4sv.tponr.networking.packet.S2C.AttributesDataSyncS2CPacket;
import org.studio4sv.tponr.networking.packet.S2C.StaminaDataSyncS2CPacket;
import org.studio4sv.tponr.mechanics.stamina.PlayerStamina;
import org.studio4sv.tponr.mechanics.stamina.PlayerStaminaProvider;

@Mod.EventBusSubscriber(modid = TPONR.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerStaminaProvider.PLAYER_STAMINA).isPresent()) {
                event.addCapability(ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "stamina"), new PlayerStaminaProvider());
            }
            if(!event.getObject().getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES).isPresent()) {
                event.addCapability(ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "attributes"), new PlayerAttributesProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerStamina.class);
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(stamina -> {
                    ModMessages.sendToPlayer(new StaminaDataSyncS2CPacket(stamina.getStamina(), stamina.getMaxStamina()), player);
                });
                player.getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES).ifPresent(attributes -> {
                    ModMessages.sendToPlayer(new AttributesDataSyncS2CPacket(attributes.getAttributes()), player);
                });
            }
        }
    }

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        HUDControllerCommands.register(event.getDispatcher());
    }

}