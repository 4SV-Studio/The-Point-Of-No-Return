package org.studio4sv.tponr.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.saveddata.SavedData;
import org.studio4sv.tponr.networking.ModMessages;
import org.studio4sv.tponr.networking.packet.S2C.SafeAreaSyncS2CPacket;

import java.util.HashSet;
import java.util.Set;

public class SafeAreaTracker extends SavedData {
    private final Set<BlockPos> safeArea = new HashSet<>();
    private final ServerLevel level;

    public SafeAreaTracker(ServerLevel level) {
        this.level = level;
    }

    public static SafeAreaTracker load(CompoundTag tag, ServerLevel level) {
        SafeAreaTracker tracker = new SafeAreaTracker(level);
        ListTag list = tag.getList("SafeBlocks", Tag.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            CompoundTag posTag = list.getCompound(i);
            tracker.safeArea.add(new BlockPos(
                    posTag.getInt("x"),
                    posTag.getInt("y"),
                    posTag.getInt("z")
            ));
        }
        return tracker;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag list = new ListTag();
        for (BlockPos pos : safeArea) {
            CompoundTag posTag = new CompoundTag();
            posTag.putInt("x", pos.getX());
            posTag.putInt("y", pos.getY());
            posTag.putInt("z", pos.getZ());
            list.add(posTag);
        }
        tag.put("SafeBlocks", list);
        return tag;
    }

    public void addSafeArea(BlockPos pos) {
        if (safeArea.add(pos)) {
            for (ServerPlayer player : this.level.players()) {
                ModMessages.sendToPlayer(
                        new SafeAreaSyncS2CPacket(level.dimension(), pos, SafeAreaSyncS2CPacket.ACTIONS.add),
                        player
                );
            }
            setDirty();
        }
    }

    public void removeSafeArea(BlockPos pos) {
        if (safeArea.remove(pos)) {
            for (ServerPlayer player : this.level.players()) {
                ModMessages.sendToPlayer(
                        new SafeAreaSyncS2CPacket(level.dimension(), pos, SafeAreaSyncS2CPacket.ACTIONS.remove),
                        player
                );
            }
            setDirty();
        }
    }

    public boolean isSafe(BlockPos pos) {
        return safeArea.contains(pos);
    }

    public Set<BlockPos> getAllSafeBlocks() {
        return safeArea;
    }

    public static SafeAreaTracker get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                (tag) -> SafeAreaTracker.load(tag, level),
                () -> new SafeAreaTracker(level),
                "safe_area"
        );
    }
}
