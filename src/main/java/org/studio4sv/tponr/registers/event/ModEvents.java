package org.studio4sv.tponr.registers.event;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.armor.HazmatSuitItem;
import org.studio4sv.tponr.blocks.custom.SafeAir;
import org.studio4sv.tponr.commands.HUDControllerCommands;
import org.studio4sv.tponr.mechanics.attributes.PlayerAttributesProvider;
import org.studio4sv.tponr.networking.ModMessages;
import org.studio4sv.tponr.networking.packet.S2C.AttributesDataSyncS2CPacket;
import org.studio4sv.tponr.networking.packet.S2C.StaminaDataSyncS2CPacket;
import org.studio4sv.tponr.mechanics.stamina.PlayerStamina;
import org.studio4sv.tponr.mechanics.stamina.PlayerStaminaProvider;
import org.studio4sv.tponr.registers.ModItems;
import org.studio4sv.tponr.util.RadiationUtils;
import org.studio4sv.tponr.util.SafeAreaTracker;

import java.util.concurrent.atomic.AtomicInteger;

@Mod.EventBusSubscriber(modid = TPONR.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerStaminaProvider.PLAYER_STAMINA).isPresent()) {
                event.addCapability(TPONR.id("stamina"), new PlayerStaminaProvider());
            }
            if(!event.getObject().getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES).isPresent()) {
                event.addCapability(TPONR.id("attributes"), new PlayerAttributesProvider());
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

    @SubscribeEvent
    public static void onArmorRemoved(LivingEquipmentChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (event.getSlot().getType() != EquipmentSlot.Type.ARMOR) return;

        ItemStack oldItem = event.getFrom();
        ItemStack newItem = event.getTo();

        if (!(oldItem.getItem() instanceof HazmatSuitItem)) return;
        if (newItem.getItem() instanceof HazmatSuitItem) return;

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR) {
                ItemStack armor = player.getItemBySlot(slot);
                if (armor.getItem() instanceof HazmatSuitItem) {
                    player.setItemSlot(slot, ItemStack.EMPTY);
                }
            }
        }

        int color = ((DyeableLeatherItem) oldItem.getItem()).getColor(oldItem);
        float charge = oldItem.getOrCreateTag().contains("charge") ? oldItem.getOrCreateTag().getFloat("charge") : 0;
        ItemStack suitPack = createHazmatSuitPack(color, charge);

        player.containerMenu.setCarried(ItemStack.EMPTY);
        player.containerMenu.setCarried(suitPack);
    }

    private static ItemStack createHazmatSuitPack(int color, float charge) {
        ItemStack suitPack = new ItemStack(ModItems.HAZMAT_SUIT_PACK.get());

        ((DyeableLeatherItem) suitPack.getItem()).setColor(suitPack, color);

        CompoundTag tag = suitPack.getOrCreateTag();
        tag.putFloat("charge", charge);

        return suitPack;
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.level().isClientSide) {
            ServerPlayer player = (ServerPlayer) event.player;
            if (player.getAbilities().instabuild) return;

            int radiationLevel = RadiationUtils.levelForPlayer(player);
            AtomicInteger equippedSuitParts = new AtomicInteger();

            BlockPos blockBottom = player.blockPosition();
            BlockPos blockTop = player.blockPosition().above();

            if (radiationLevel > 0 && !(SafeAreaTracker.isSafe(blockBottom)) && !(SafeAreaTracker.isSafe(blockTop))) {
                player.getArmorSlots().forEach(slot -> {
                    if (slot.getItem() instanceof HazmatSuitItem) {
                        equippedSuitParts.getAndIncrement();
                    }
                });

                if (equippedSuitParts.get() != 4) {
                    player.hurt(player.damageSources().magic(), 0.5F + (0.5F * radiationLevel));
                } else {
                    player.getArmorSlots().forEach(slot -> {
                        if (slot.getItem() instanceof HazmatSuitItem suitItem) {
                            if (suitItem.getEnergy(slot) <= 0) player.hurt(player.damageSources().magic(), 0.5F + (0.5F * radiationLevel));
                            suitItem.changeEnergy(slot, -0.01F * radiationLevel);
                        }
                    });
                }
            }
        }
    }
}