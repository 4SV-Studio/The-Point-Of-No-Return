package org.studio4sv.tponr.util;

import net.minecraft.core.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class SafeAreaTracker {
    private static final Set<BlockPos> safeArea = new HashSet<>();

    public static void addSafeArea(BlockPos pos) {
        safeArea.add(pos);
    }

    public static void removeSafeArea(BlockPos pos) {
        safeArea.remove(pos);
    }

    public static boolean isSafe(BlockPos pos) {
        return safeArea.contains(pos);
    }
}
