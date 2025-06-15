package org.studio4sv.ponr.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.studio4sv.ponr.PONR;
import org.studio4sv.ponr.client.ClientStaminaData;

public class StaminaHud {
    private static int staminaAmount;

    private final static ResourceLocation STAMINA_TEXTURE = new ResourceLocation(PONR.MOD_ID,
            "textures/gui/stamina.png");

    public static void setStaminaAmount(int stamina) {
        staminaAmount = Math.max(0, Math.min(stamina, ClientStaminaData.getMax()));
    }

    public static final IGuiOverlay HUD_STAMINA = (gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, STAMINA_TEXTURE);

        int y = screenHeight - 31;
        int textureWidth = 30;
        int textureHeight = 16;

        final int SCALE_FACTOR = 3;
        int maxStaminaPixels = ClientStaminaData.getMax() / SCALE_FACTOR;
        int currentStaminaPixels = staminaAmount / SCALE_FACTOR;

        int centerX = screenWidth / 2;
        
        // Sides
        poseStack.blit(STAMINA_TEXTURE,
                centerX - maxStaminaPixels / 2 - 2, y,
                0, 0,
                2, 7,
                textureWidth, textureHeight);

        poseStack.blit(STAMINA_TEXTURE,
                centerX + maxStaminaPixels / 2, y,
                28, 0,
                2, 7,
                textureWidth, textureHeight);

        // Empty bar
        for (int i = 0; i < maxStaminaPixels; i += 7) {
            int currentTileWidth = Math.min(7, maxStaminaPixels - i);
            poseStack.blit(STAMINA_TEXTURE,
                    centerX - maxStaminaPixels / 2 + i, y,
                    21, 0,
                    currentTileWidth, 7,
                    textureWidth, textureHeight);
        }

        int halfMaxPixels = maxStaminaPixels / 2;
        float fillPercent = (float) currentStaminaPixels / maxStaminaPixels;
        int sidePixels = Math.round(halfMaxPixels * fillPercent);
        
        // Filled stamina left
        for (int i = 0; i < sidePixels; i += 7) {
            int currentTileWidth = Math.min(7, sidePixels - i);
            poseStack.blit(STAMINA_TEXTURE,
                    centerX - i - currentTileWidth, y,
                    2, 0,
                    currentTileWidth, 7,
                    textureWidth, textureHeight);
        }
        
        // Filled stamina right
        for (int i = 0; i < sidePixels; i += 7) {
            int currentTileWidth = Math.min(7, sidePixels - i);
            poseStack.blit(STAMINA_TEXTURE,
                    centerX + i, y,
                    2, 0,
                    currentTileWidth, 7,
                    textureWidth, textureHeight);
        }

        // Center icon
        poseStack.blit(STAMINA_TEXTURE,
                centerX - 6, y,
                9, 0,
                12, 8,
                textureWidth, textureHeight);
    };
}