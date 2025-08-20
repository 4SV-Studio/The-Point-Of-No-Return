package org.studio4sv.tponr.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import ichttt.mods.firstaid.api.damagesystem.AbstractDamageablePart;
import ichttt.mods.firstaid.api.damagesystem.AbstractPlayerDamageModel;
import ichttt.mods.firstaid.client.util.PlayerModelRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.studio4sv.tponr.TPONR;

@Mixin(PlayerModelRenderer.class)
public class PlayerModelRendererMixin {
    @Shadow(remap = false)
    @Mutable
    @Final
    private static ResourceLocation HEALTH_RENDER_LOCATION;

    @Shadow(remap = false)
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
   @Overwrite(remap = false)
   private static void drawPart(GuiGraphics guiGraphics, boolean fourColors, AbstractDamageablePart part, int texX, int texY, int sizeX, int sizeY) {
       int rawTexX = texX;
       texX += 32 * getState(part, fourColors);
       guiGraphics.blit(HEALTH_RENDER_LOCATION, rawTexX, texY, texX, texY, sizeX, sizeY);
   }

   @ModifyArg(
           method = "renderPlayerHealth",
           at = @At(
                   value = "INVOKE",
                   target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V"
           ),
           index = 3,
           remap = false
   )
   private static float modifyAlpha(float original) {
       return 1F;
   }

    @Inject(
            method = "renderPlayerHealth",
            at = @At(
                    value = "INVOKE",
                    target = "Lichttt/mods/firstaid/client/util/PlayerModelRenderer;drawPart(Lnet/minecraft/client/gui/GuiGraphics;ZLichttt/mods/firstaid/api/damagesystem/AbstractDamageablePart;IIII)V"
            ),
            cancellable = true,
            remap = false
    )
    private static void injectCustomDraws(PoseStack stack, AbstractPlayerDamageModel damageModel, boolean fourColors, GuiGraphics guiGraphics, boolean flashState, float alpha, float partialTicks, CallbackInfo ci) {
        int yOffset = flashState ? 64 : 0;

        callDrawPart(guiGraphics, fourColors, damageModel.HEAD, 8, yOffset - 5, 16, 16);
        callDrawPart(guiGraphics, fourColors, damageModel.BODY, 10, yOffset + 11, 12, 22);
        callDrawPart(guiGraphics, fourColors, damageModel.LEFT_ARM, 4, yOffset + 11, 6, 27);
        callDrawPart(guiGraphics, fourColors, damageModel.RIGHT_ARM, 22, yOffset + 11, 6, 27);
        callDrawPart(guiGraphics, fourColors, damageModel.LEFT_LEG, 9, yOffset + 33, 7, 20);
        callDrawPart(guiGraphics, fourColors, damageModel.RIGHT_LEG, 16, yOffset + 33, 7, 20);
        callDrawPart(guiGraphics, fourColors, damageModel.LEFT_FOOT, 8, yOffset + 53, 8, 11);
        callDrawPart(guiGraphics, fourColors, damageModel.RIGHT_FOOT, 16, yOffset + 53, 8, 11);

        ci.cancel();
    }

    @Invoker(remap = false, value = "drawPart")
    private static void callDrawPart(GuiGraphics guiGraphics, boolean fourColors, AbstractDamageablePart part, int texX, int texY, int sizeX, int sizeY) {
        throw new AssertionError();
    }

}
