package org.studio4sv.ponr.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.studio4sv.ponr.PONR;

public class HungerHud {
    private static final ResourceLocation HUNGER_TEXTURE = new ResourceLocation(PONR.MOD_ID, "textures/gui/hunger.png");

    private static int currentHungerStage;

    public static void setHungerStage(int stage) {
        currentHungerStage = Math.max(1, Math.min(6, stage));
    }

    public static final IGuiOverlay HUD_HUNGER = (gui, poseStack, partialTick, screenWidth, screenHeight) -> {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, HUNGER_TEXTURE);

        int padding = 10;

        int width = 35;
        int height = 35;
        int textureSize = 26;
        int x = screenWidth - width - padding;
        int y = height + (padding * 2);

        poseStack.blit(HUNGER_TEXTURE, x, y, 0, textureSize * (currentHungerStage - 1), textureSize, textureSize, textureSize, textureSize * 6); // texture, x, y, u, v, width, height, textureWidth, textureHeight
    };
}
