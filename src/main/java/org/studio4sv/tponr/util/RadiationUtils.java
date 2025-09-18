package org.studio4sv.tponr.util;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.studio4sv.tponr.blocks.custom.BunkerDoor.BunkerDoorBlock;
import org.studio4sv.tponr.blocks.custom.BunkerDoor.BunkerDoorSubBlock;
import org.studio4sv.tponr.blocks.entity.BunkerDoor.BunkerDoorBlockEntity;
import org.studio4sv.tponr.blocks.entity.BunkerDoor.BunkerDoorSubBlockEntity;
import org.studio4sv.tponr.client.ClientSafeAreaTracker;
import org.studio4sv.tponr.registers.ModTags;

public class RadiationUtils {
    public static boolean isSealValid(BlockState state, BlockPos pos, Level level) {
        if (!state.is(ModTags.Blocks.SEAL_BLOCKS)) {
            return false;
        }

        Block block = state.getBlock();

        if (block instanceof BunkerDoorBlock) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof BunkerDoorBlockEntity door) {
                return !door.isOpen();
            }
            return false;
        }

        if (block instanceof BunkerDoorSubBlock) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof BunkerDoorSubBlockEntity sub) {
                BlockPos mainPos = sub.getMainBlockPos();
                BlockEntity mainBe = level.getBlockEntity(mainPos);
                if (mainBe instanceof BunkerDoorBlockEntity main) {
                    return !main.isOpen();
                }
            }
            return false;
        }

        return true;
    }

    public static int levelForPlayer(Player player) {
        Biome biome = player.level().getBiome(player.blockPosition()).value();
        float temp = Math.abs(biome.getBaseTemperature());

        BlockPos blockBottom = player.blockPosition().below();
        BlockPos blockTop = player.blockPosition().above();

        if (!player.level().isClientSide() && player.level() instanceof ServerLevel serverLevel) {
            SafeAreaTracker tracker = SafeAreaTracker.get(serverLevel);

            if (tracker.isSafe(blockBottom) && tracker.isSafe(blockTop)) {
                return 0;
            }
        } else if (player.level().isClientSide()) {
            ResourceKey<Level> dimension = player.level().dimension();
            if (ClientSafeAreaTracker.isSafe(dimension, blockBottom) && ClientSafeAreaTracker.isSafe(dimension, blockTop)) {
                return 0;
            }
        }

        return radTable(temp);
    }

    public static int levelFromPos(BlockPos pos, Level level) {
        Biome biome = level.getBiome(pos).value();
        float temp = Math.abs(biome.getBaseTemperature());

        if (level instanceof ServerLevel serverLevel) {
            SafeAreaTracker tracker = SafeAreaTracker.get(serverLevel);
            if (tracker.isSafe(pos)) {
                return 0;
            }
        }

        return radTable(temp);
    }

    public static int radTable(Float temp) {
        if (temp <= 0.15F) return 1;
        else if (temp <= 0.3F) return 2;
        else if (temp <= 0.9F) return 3;
        else if (temp <= 1.5F) return 4;
        else if (temp <= 2.0F) return 5;
        else return 6;
    }
}
