package org.studio4sv.tponr.mixin;

import ichttt.mods.firstaid.client.util.PlayerModelRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.studio4sv.tponr.TPONR;

@Mixin(PlayerModelRenderer.class)
public class PlayerModelRendererMixin {
    @Shadow
    @Mutable
    @Final
    private static ResourceLocation HEALTH_RENDER_LOCATION;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void modifyLock(CallbackInfo ci) {
        HEALTH_RENDER_LOCATION = ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "textures/gui/firstaid/simple_health.png");
    }
}
