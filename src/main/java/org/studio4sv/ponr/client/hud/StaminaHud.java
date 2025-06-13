package org.studio4sv.ponr.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.studio4sv.ponr.PONR;
import org.studio4sv.ponr.client.ClientStaminaData;

public class StaminaHud {
    private static int staminaAmount;

    private final static ResourceLocation STAMINA_ICON = new ResourceLocation(PONR.MOD_ID,
            "textures/gui/stamina/stamina_icon.png");

    private final static ResourceLocation STAMINA_SIDE = new ResourceLocation(PONR.MOD_ID,
            "textures/gui/stamina/stamina_side.png");

    private final static ResourceLocation STAMINA_LINES = new ResourceLocation(PONR.MOD_ID,
            "textures/gui/stamina/stamina_lines.png");

    public static void setStaminaAmount(int stamina) {
        staminaAmount = Math.max(0, Math.min(stamina, ClientStaminaData.getPlayerMaxStamina()));
    }

    public static final IGuiOverlay HUD_STAMINA = (gui, poseStack, partialTick, screenWidth, screenHeight) -> {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F); // R, G, B, A
        RenderSystem.setShaderTexture(0, STAMINA_ICON);

        int width = 11;
        int height = 8;
        int x = screenWidth / 2 - width / 2 - 1;
        int y = screenHeight - 35;

        poseStack.blit(STAMINA_LINES, screenWidth / 2 - ClientStaminaData.getPlayerMaxStamina() / 2, y, 0, 0, ClientStaminaData.getPlayerMaxStamina(), 7, 1, 7); // texture, x, y, u, v, width, height, textureWidth, textureHeight

        poseStack.blit(STAMINA_ICON, x, y, 0, 0, width, height, width, height); // texture, x, y, u, v, width, height, textureWidth, textureHeight

        poseStack.blit(STAMINA_SIDE, screenWidth / 2 - ClientStaminaData.getPlayerMaxStamina() / 2 - 2, y, 0, 0,  2, 7, 2, 7); // texture, x, y, u, v, width, height, textureWidth, textureHeight
        poseStack.blit(STAMINA_SIDE, screenWidth / 2 + ClientStaminaData.getPlayerMaxStamina() / 2    , y, 0, 0, 2, 7, 2, 7); // texture, x, y, u, v, width, height, textureWidth, textureHeight
    };
}
