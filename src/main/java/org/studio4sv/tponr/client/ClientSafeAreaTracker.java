package org.studio4sv.tponr.client;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class ClientSafeAreaTracker {

    private static final Map<ResourceKey<Level>, Set<BlockPos>> safeAreas = new HashMap<>();

    public static void addSafeArea(ResourceKey<Level> dimension, BlockPos pos) {
        safeAreas.computeIfAbsent(dimension, k -> new HashSet<>()).add(pos);
    }

    public static void removeSafeArea(ResourceKey<Level> dimension, BlockPos pos) {
        Set<BlockPos> set = safeAreas.get(dimension);
        if (set != null) set.remove(pos);
    }

    public static boolean isSafe(ResourceKey<Level> dimension, BlockPos pos) {
        Set<BlockPos> set = safeAreas.get(dimension);
        return set != null && set.contains(pos);
    }

    public static void setSafeArea(ResourceKey<Level> dimension, Set<BlockPos> newSafeArea) {
        safeAreas.put(dimension, new HashSet<>(newSafeArea));
    }
}
