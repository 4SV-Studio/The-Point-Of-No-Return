package org.studio4sv.tponr.mechanics.attributes;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.mechanics.stamina.PlayerStaminaProvider;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = TPONR.MOD_ID)
public class AttributesHandler {

    private static final UUID HEALTH_MODIFIER_UUID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    private static final UUID STRENGTH_MODIFIER_UUID = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
    private static final UUID AGILITY_MODIFIER_UUID = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");
    
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            applyAttributeModifiers(serverPlayer);
        }
    }
    
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            applyAttributeModifiers(serverPlayer);
        }
    }
    
    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            applyAttributeModifiers(serverPlayer);
        }
    }
    
    public static void applyAttributeModifiers(ServerPlayer player) {
        player.getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES).ifPresent(attributes -> {
            // Health modifier
            int healthValue = attributes.get("Health");
            double healthBonus = (healthValue - 10) * 2.0; // Each point above 10 gives 2 extra health
            applyAttributeModifier(player, Attributes.MAX_HEALTH, HEALTH_MODIFIER_UUID, 
                                 "Health Attribute Bonus", healthBonus, AttributeModifier.Operation.ADDITION);
            
            // Strength modifier
            int strengthValue = attributes.get("Strength");
            double strengthBonus = (strengthValue - 10) * 0.5; // Each point above 10 gives 0.5 extra damage
            applyAttributeModifier(player, Attributes.ATTACK_DAMAGE, STRENGTH_MODIFIER_UUID,
                                 "Strength Attribute Bonus", strengthBonus, AttributeModifier.Operation.ADDITION);
            
            // Agility modifier
            int agilityValue = attributes.get("Agility");
            double agilityBonus = (agilityValue - 10) * 0.01; // Each point above 10 gives 1% speed boost
            applyAttributeModifier(player, Attributes.MOVEMENT_SPEED, AGILITY_MODIFIER_UUID,
                                 "Agility Attribute Bonus", agilityBonus, AttributeModifier.Operation.MULTIPLY_BASE);
            
            // Stamina modifier
            int staminaValue = attributes.get("Stamina");
            int staminaBonus = (staminaValue - 10) * 15; // Each point above 10 gives 15 extra max stamina
            player.getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(stamina -> {
                int newMaxStamina = 150 + staminaBonus;
                stamina.setMaxStamina(newMaxStamina);
            });
        });
    }
    
    private static void applyAttributeModifier(Player player, net.minecraft.world.entity.ai.attributes.Attribute attribute, 
                                             UUID uuid, String name, double amount, AttributeModifier.Operation operation) {
        AttributeInstance attributeInstance = player.getAttribute(attribute);
        if (attributeInstance != null) {
            attributeInstance.removeModifier(uuid);

            if (amount != 0) {
                AttributeModifier modifier = new AttributeModifier(uuid, name, amount, operation);
                attributeInstance.addPermanentModifier(modifier);
            }
        }
    }
    
    public static void updatePlayerAttributes(ServerPlayer player) {
        applyAttributeModifiers(player);
    }
}