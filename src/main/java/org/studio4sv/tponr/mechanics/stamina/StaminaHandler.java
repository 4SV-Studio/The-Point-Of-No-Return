package org.studio4sv.tponr.mechanics.stamina;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.networking.ModMessages;
import org.studio4sv.tponr.networking.packet.S2C.StaminaDataSyncS2CPacket;

@Mod.EventBusSubscriber(modid = TPONR.MOD_ID)
public class StaminaHandler {
    public static final int SPRINT_COST = 1;
    public static final int JUMP_COST = 5;
    public static final int MINING_COST = 10;

    private static final int REGEN_AMOUNT = 5;
    private static final int REGEN_COOLDOWN = 30;

    private static int regenTickCounter = 0;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side != LogicalSide.SERVER || event.phase != TickEvent.Phase.END) return;

        ServerPlayer serverPlayer = (ServerPlayer) event.player;

        serverPlayer.getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(stamina -> {
            boolean syncNeeded = false;

            if (serverPlayer.isSprinting()) {
                if (stamina.getStamina() > SPRINT_COST) {
                    stamina.subStamina(SPRINT_COST);
                    regenTickCounter = 0;
                    syncNeeded = true;
                }
            }

            // Regen
            regenTickCounter++;
            if (regenTickCounter >= REGEN_COOLDOWN) {
                regenTickCounter = 0;
                if (!serverPlayer.isSprinting()) {
                    stamina.addStamina(REGEN_AMOUNT);
                    syncNeeded = true;
                }
            }

            if (syncNeeded) {
                syncStamina(serverPlayer, stamina.getStamina(), stamina.getMaxStamina());
            }
        });
    }

    @SubscribeEvent
    public static void onPlayerJump(LivingJumpEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;

        serverPlayer.getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(stamina -> {
            if (!serverPlayer.isInWater() && !serverPlayer.isInLava()) {
                if (stamina.getStamina() >= JUMP_COST) {
                    stamina.subStamina(JUMP_COST);
                    regenTickCounter = 0;
                    syncStamina(serverPlayer, stamina.getStamina(), stamina.getMaxStamina());
                }
            }
        });
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer serverPlayer)) return;

        serverPlayer.getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(stamina -> {
            if (stamina.getStamina() < MINING_COST) {
                // TODO: damage arms with first aid
                event.setCanceled(true);
            } else {
                stamina.subStamina(MINING_COST);
                regenTickCounter = 0;
                syncStamina(serverPlayer, stamina.getStamina(), stamina.getMaxStamina());
            }
        });
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        syncIfServerPlayer(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        syncIfServerPlayer(event.getEntity());
    }

    private static void syncIfServerPlayer(Player player) {
        if (player instanceof ServerPlayer serverPlayer && !serverPlayer.level().isClientSide()) {
            serverPlayer.getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(stamina -> {
                syncStamina(serverPlayer, stamina.getStamina(), stamina.getMaxStamina());
            });
        }
    }

    private static void syncStamina(ServerPlayer player, int stamina, int maxStamina) {
        ModMessages.sendToPlayer(new StaminaDataSyncS2CPacket(stamina, maxStamina), player);
    }
}