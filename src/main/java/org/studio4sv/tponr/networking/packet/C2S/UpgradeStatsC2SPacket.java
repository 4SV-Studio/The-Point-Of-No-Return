package org.studio4sv.tponr.networking.packet.C2S;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.studio4sv.tponr.mechanics.attributes.AttributesHandler;
import org.studio4sv.tponr.mechanics.attributes.PlayerAttributesProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class UpgradeStatsC2SPacket {
    private final Map<String, Integer> upgradedStats;
    private final int EXP_COST;
    private final int levelsToAdd;

    public UpgradeStatsC2SPacket(Map<String, Integer> upgradedStats, int EXP_COST, int levelsToAdd) {
        this.upgradedStats = upgradedStats;
        this.EXP_COST = EXP_COST;
        this.levelsToAdd = levelsToAdd;
    }

    public UpgradeStatsC2SPacket(FriendlyByteBuf buf) {
        this.upgradedStats = new HashMap<>();
        this.levelsToAdd = buf.readInt();
        this.EXP_COST = buf.readInt();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            String key = buf.readUtf();
            int value = buf.readInt();
            this.upgradedStats.put(key, value);
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(levelsToAdd);
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
            player.getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES) .ifPresent(attributes -> {
                for (Map.Entry<String, Integer> entry : upgradedStats.entrySet()) {
                    attributes.set(entry.getKey(), attributes.get(entry.getKey()) + entry.getValue());
                }
                AttributesHandler.applyAttributeModifiers(player);
                attributes.set("Level", attributes.get("Level") + levelsToAdd);
                player.totalExperience -= EXP_COST;
            });
        });
        return true;
    }
}