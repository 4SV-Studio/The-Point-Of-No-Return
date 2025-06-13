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

        int y = screenHeight - 35;
        int maxStamina = ClientStaminaData.getMax()/3;
        int stamina = staminaAmount/3;

        int textureWidth = 30;
        int textureHeight = 8;

        // Border
        for (int i = 0; i < maxStamina; i += 7) {
            int currentTileWidth = Math.min(7, maxStamina - i);
            poseStack.blit(STAMINA_TEXTURE,
                    screenWidth / 2 - maxStamina / 2 + i, y,
                    21, 0,
                    currentTileWidth, 7,
                    textureWidth, textureHeight);
        }

        // Stamina
        for (int i = 0; i < stamina; i += 7) {
            int currentTileWidth = Math.min(7, stamina - i);
            poseStack.blit(STAMINA_TEXTURE,
                    screenWidth / 2 - stamina / 2 + i, y,
                    2, 0,
                    currentTileWidth, 7,
                    textureWidth, textureHeight);
        }

        // Sides
        poseStack.blit(STAMINA_TEXTURE,
                screenWidth / 2 - maxStamina / 2 - 2, y,
                0, 0,
                2, 7,
                textureWidth, textureHeight);

        poseStack.blit(STAMINA_TEXTURE,
                screenWidth / 2 + maxStamina / 2, y,
                28, 0,
                2, 7,
                textureWidth, textureHeight);

        // Icon
        poseStack.blit(STAMINA_TEXTURE,
                screenWidth / 2 - 12 / 2, y,
                9, 0,
                12, 8,
                textureWidth, textureHeight);
    };
}
