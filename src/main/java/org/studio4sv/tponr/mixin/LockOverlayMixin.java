package org.studio4sv.tponr.mixin;

import kirderf1.inventoryfree.client.LockOverlay;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.studio4sv.tponr.TPONR;

@Mixin(LockOverlay.class)
public class LockOverlayMixin {
    @Shadow(remap = false)
    @Mutable
    @Final
    private static ResourceLocation LOCK;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void modifyLock(CallbackInfo ci) {
        LOCK = ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "textures/gui/inventory_free/inventory_lock.png");
    }
}
