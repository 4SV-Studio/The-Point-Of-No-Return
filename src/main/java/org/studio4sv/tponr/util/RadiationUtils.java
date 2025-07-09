package org.studio4sv.tponr.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;

public class RadiationUtils {
    public static int levelForPlayer(ServerPlayer player) {
        Biome biome = player.level().getBiome(player.blockPosition()).value();

        float temp = Math.abs(biome.getBaseTemperature());

        if (temp <= 0.15f) return 1;
        else if (temp <= 0.8f) return 2;
        else if (temp <= 1.2f) return 3;
        else if (temp <= 1.5f) return 4;
        else return 5;
    }

    public static int levelForPlayer(Player player) {
        Biome biome = player.level().getBiome(player.blockPosition()).value();

        float temp = Math.abs(biome.getBaseTemperature());

        if (temp <= 0.15F) return 1;
        else if (temp <= 0.3F) return 2;
        else if (temp <= 0.9F) return 3;
        else if (temp <= 1.5F) return 4;
        else if (temp <= 2.0F) return 5;
        else return 6;
    }
}
