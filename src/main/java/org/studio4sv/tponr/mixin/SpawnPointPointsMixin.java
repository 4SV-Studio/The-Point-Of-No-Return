package org.studio4sv.tponr.mixin;

import dev.gigaherz.hudcompass.integrations.server.SpawnPointPoints;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SpawnPointPoints.class)
public class SpawnPointPointsMixin {

    @ModifyArg(
            method = "lambda$playerTick$0",
            at = @At(
                    value = "INVOKE",
                    target = "Ldev/gigaherz/hudcompass/waypoints/BasicWaypoint;<init>(Ldev/gigaherz/hudcompass/waypoints/PointInfoType;Lnet/minecraft/world/phys/Vec3;Ljava/lang/String;Ldev/gigaherz/hudcompass/icons/IIconData;)V"
            ),
            index = 2,
            remap = false
    )
    private static String replaceHomeName(String original) {
        return Component.translatable("gui.hudcompass.home").getString();
    }

    @ModifyArg(
            method = "lambda$playerTick$0",
            at = @At(
                    value = "INVOKE",
                    target = "Ldev/gigaherz/hudcompass/icons/BasicIconData;mapMarker(I)Ldev/gigaherz/hudcompass/icons/BasicIconData;"
            ),
            index = 0,
            remap = false
    )
    private static int replaceHomeIcon(int original) {
        return -100;
    }

    @ModifyVariable(
            method = "lambda$playerTick$0",
            at = @At("STORE"),
            ordinal = 0,
            remap = false
    )
    private static BlockPos bunkerPos(BlockPos original) {
        return original; // TODO: return bunker pos
    }
}
