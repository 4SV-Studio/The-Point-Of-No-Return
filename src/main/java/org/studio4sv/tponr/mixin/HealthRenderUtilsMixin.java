package org.studio4sv.tponr.mixin;

import ichttt.mods.firstaid.client.util.HealthRenderUtils;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.studio4sv.tponr.TPONR;

@Mixin(HealthRenderUtils.class)
public class HealthRenderUtilsMixin {
    @Shadow
    @Mutable
    @Final
    public static ResourceLocation SHOW_WOUNDS_LOCATION;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void modifyLock(CallbackInfo ci) {
        SHOW_WOUNDS_LOCATION = ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "textures/gui/firstaid/show_wounds.png");
    }
}
