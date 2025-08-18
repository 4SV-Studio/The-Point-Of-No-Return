package org.studio4sv.tponr.networking.packet.S2C;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import org.studio4sv.tponr.client.ClientSafeAreaTracker;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class SafeAreaSyncS2CPacket {
    public enum ACTIONS {
        add, remove
    }

    private final ResourceKey<Level> dimension;
    private final BlockPos pos;
    private final Set<BlockPos> safeArea = new HashSet<>();
    private final ACTIONS action;

    public SafeAreaSyncS2CPacket(ResourceKey<Level> dimension, BlockPos pos, ACTIONS action) {
        this.dimension = dimension;
        this.pos = pos;
        this.action = action;
    }

    public SafeAreaSyncS2CPacket(ResourceKey<Level> dimension, Set<BlockPos> safeArea) {
        this.dimension = dimension;
        this.safeArea.addAll(safeArea);
        this.pos = null;
        this.action = ACTIONS.add;
    }

    public SafeAreaSyncS2CPacket(FriendlyByteBuf buf) {
        this.dimension = buf.readResourceKey(Registries.DIMENSION);

        boolean hasPos = buf.readBoolean();
        this.pos = hasPos ? buf.readBlockPos() : null;

        int size = buf.readInt();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                safeArea.add(buf.readBlockPos());
            }
        }
        this.action = buf.readEnum(ACTIONS.class);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceLocation(dimension.location());

        buf.writeBoolean(pos != null);
        if (pos != null) buf.writeBlockPos(pos);

        buf.writeInt(safeArea.size());
        for (BlockPos blockPos : safeArea) {
            buf.writeBlockPos(blockPos);
        }
        buf.writeEnum(action);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            if (!safeArea.isEmpty()) {
                ClientSafeAreaTracker.setSafeArea(dimension, safeArea);
            } else {
                if (action == ACTIONS.add) {
                    ClientSafeAreaTracker.addSafeArea(dimension, pos);
                } else if (action == ACTIONS.remove) {
                    ClientSafeAreaTracker.removeSafeArea(dimension, pos);
                }
            }
        });
        return true;
    }
}