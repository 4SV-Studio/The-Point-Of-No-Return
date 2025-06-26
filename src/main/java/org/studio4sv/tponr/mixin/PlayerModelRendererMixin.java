package org.studio4sv.tponr.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import ichttt.mods.firstaid.api.damagesystem.AbstractDamageablePart;
import ichttt.mods.firstaid.api.damagesystem.AbstractPlayerDamageModel;
import ichttt.mods.firstaid.client.util.PlayerModelRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.*;
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

    @Shadow
    private static int getState(AbstractDamageablePart part, boolean fourColors) {
        return 0; // Dummy
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void modifyLock(CallbackInfo ci) {
        HEALTH_RENDER_LOCATION = ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "textures/gui/firstaid/simple_health.png");
    }

    @Inject(method = "renderPlayerHealth", at = @At("HEAD"), remap = false)
    private static void onRenderHead(PoseStack stack, AbstractPlayerDamageModel damageModel, boolean fourColors, GuiGraphics guiGraphics, boolean flashState, float alpha, float partialTicks, CallbackInfo ci) {
        stack.pushPose();
        int screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        stack.translate(0, screenHeight - 64, 0);
    }

    @Inject(method = "renderPlayerHealth", at = @At("RETURN"), remap = false)
    private static void onRenderReturn(PoseStack stack, AbstractPlayerDamageModel damageModel, boolean fourColors, GuiGraphics guiGraphics, boolean flashState, float alpha, float partialTicks, CallbackInfo ci) {
        stack.popPose();
    }

   /**
    * @author Lemonnik
    * @reason Adjust UV for custom texture
    */
   @Overwrite
   private static void drawPart(GuiGraphics guiGraphics, boolean fourColors, AbstractDamageablePart part, int texX, int texY, int sizeX, int sizeY) {
       int rawTexX = texX;
       texX += 32 * getState(part, fourColors);
       guiGraphics.blit(HEALTH_RENDER_LOCATION, rawTexX, texY, texX, texY, sizeX, sizeY);
   }
}
