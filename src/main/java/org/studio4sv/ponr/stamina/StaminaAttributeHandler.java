package org.studio4sv.ponr.stamina;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.studio4sv.ponr.PONR;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = PONR.MOD_ID)
public class StaminaAttributeHandler {
    
    private static final UUID STAMINA_SPEED_MODIFIER_ID = UUID.fromString("7107DE5E-7CE8-4030-940E-514C1F160890");

    private static final Map<UUID, Boolean> lowStaminaState = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side != LogicalSide.SERVER || event.phase != TickEvent.Phase.END) return;

        ServerPlayer serverPlayer = (ServerPlayer) event.player;
        UUID playerId = serverPlayer.getUUID();

        serverPlayer.getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(stamina -> {
            boolean hasLowStamina = stamina.getStamina() < StaminaHandler.SPRINT_COST;
            boolean wasLowStamina = lowStaminaState.getOrDefault(playerId, false);

            lowStaminaState.put(playerId, hasLowStamina);
            
            // Handle sprint
            if (hasLowStamina && serverPlayer.isSprinting()) {
                serverPlayer.setSprinting(false);
                applySpeedModifier(serverPlayer, -0.5);
            } else if (!hasLowStamina && wasLowStamina) {
                removeSpeedModifier(serverPlayer);
            }

            if (serverPlayer.isSprinting() && !hasLowStamina) {
                stamina.subStamina(StaminaHandler.SPRINT_COST);
            }
        });
    }
    
    private static void applySpeedModifier(ServerPlayer player, double modifier) {
        var attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attribute != null) {
            attribute.removeModifier(STAMINA_SPEED_MODIFIER_ID);
            attribute.addPermanentModifier(new AttributeModifier(
                STAMINA_SPEED_MODIFIER_ID, 
                "stamina_speed_penalty", 
                modifier, 
                AttributeModifier.Operation.MULTIPLY_TOTAL
            ));
        }
    }
    
    private static void removeSpeedModifier(ServerPlayer player) {
        var attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attribute != null) {
            attribute.removeModifier(STAMINA_SPEED_MODIFIER_ID);
        }
    }
}