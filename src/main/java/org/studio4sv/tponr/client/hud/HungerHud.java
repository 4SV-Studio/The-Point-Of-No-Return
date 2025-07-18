package org.studio4sv.tponr.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.studio4sv.tponr.TPONR;

public class HungerHud {
    private static final ResourceLocation HUNGER_TEXTURE = ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "textures/gui/hunger.png");

    private static int currentHungerStage;
    private static boolean enabled = true;

    public static void toggle() {
        enabled = !enabled;
    }

    public static void setHungerStage(int stage) {
        currentHungerStage = Math.max(1, Math.min(6, stage));
    }

    public static final IGuiOverlay HUD_HUNGER = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        if (!enabled) return;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, HUNGER_TEXTURE);

        int padding = 10;

        int scaledSize = 35;

        int textureSize = 26;
        int x = screenWidth - scaledSize - padding;
        int y = scaledSize + (padding * 2);

        PoseStack poseStack = guiGraphics.pose();

        poseStack.pushPose();
        poseStack.translate(x, y, 0);
        float scale = 35.0f / 26.0f;
        poseStack.scale(scale, scale, 1.0f);

        guiGraphics.blit(HUNGER_TEXTURE, 0, 0, 0, textureSize * (currentHungerStage - 1), textureSize, textureSize, textureSize, textureSize * 6);

        poseStack.popPose();
    };
}
