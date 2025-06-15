package org.studio4sv.ponr.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.studio4sv.ponr.PONR;

public class XpHud {
    private static final ResourceLocation XP_TEXTURE = new ResourceLocation(PONR.MOD_ID, "textures/gui/xp.png");
    private static String xpText = "0";

    public static void setPoints(int points) {
        xpText = String.valueOf(points);
    }

    public static final IGuiOverlay HUD_XP = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, XP_TEXTURE);

        int textureWidth = 60;
        int textureHeight = 27;
        int x = screenWidth - textureWidth;
        int y = screenHeight / 3 * 2;

        guiGraphics.blit(XP_TEXTURE, x, y, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);

        guiGraphics.drawString(gui.getFont(), String.valueOf(xpText), x + textureWidth / 3 * 2 - gui.getFont().width(xpText) / 2, y + textureHeight / 2 - gui.getFont().lineHeight / 2 + 1, 0x59F5A4, false);
    };
}
