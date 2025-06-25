package org.studio4sv.tponr.networking.packet.C2S;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.studio4sv.tponr.mechanics.attributes.AttributesHandler;
import org.studio4sv.tponr.mechanics.attributes.PlayerAttributesProvider;
import org.studio4sv.tponr.mechanics.stamina.PlayerStaminaProvider;
import org.studio4sv.tponr.networking.ModMessages;
import org.studio4sv.tponr.networking.packet.S2C.StaminaDataSyncS2CPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class UpgradeStatsC2SPacket {
    private final Map<String, Integer> upgradedStats;
    private final int EXP_COST;

    public UpgradeStatsC2SPacket(Map<String, Integer> upgradedStats, int EXP_COST) {
        this.upgradedStats = upgradedStats;
        this.EXP_COST = EXP_COST;
    }

    public UpgradeStatsC2SPacket(FriendlyByteBuf buf) {
        this.upgradedStats = new HashMap<>();
        this.EXP_COST = buf.readInt();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            String key = buf.readUtf();
            int value = buf.readInt();
            this.upgradedStats.put(key, value);
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(EXP_COST);
        buf.writeInt(upgradedStats.size());
        for (Map.Entry<String, Integer> entry : upgradedStats.entrySet()) {
            buf.writeUtf(entry.getKey());
            buf.writeInt(entry.getValue());
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            if (player.totalExperience < EXP_COST) return;
            AtomicInteger staminaAdded = new AtomicInteger();
            player.getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES) .ifPresent(attributes -> {
                for (Map.Entry<String, Integer> entry : upgradedStats.entrySet()) {
                    if (entry.getKey().equals("Stamina")) staminaAdded.addAndGet(entry.getValue());
                    attributes.set(entry.getKey(), attributes.get(entry.getKey()) + entry.getValue());
                }
                player.totalExperience -= EXP_COST;
            });
            player.getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(stamina -> {
               stamina.setMaxStamina(stamina.getMaxStamina() + staminaAdded.get() * 8);
                ModMessages.sendToPlayer(new StaminaDataSyncS2CPacket(stamina.getStamina(), stamina.getMaxStamina()), player);
            });
            AttributesHandler.updatePlayerAttributes(player);
        });
        return true;
    }
}