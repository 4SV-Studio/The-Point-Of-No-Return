package org.studio4sv.tponr.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.armor.HazmatSuitItem;

public class XpHud {
    private static final ResourceLocation XP_TEXTURE = TPONR.id("textures/gui/xp.png");
    private static String xpText = "0";
    private static boolean enabled = true;

    public static void toggle() {
        enabled = !enabled;
    }

    public static void setPoints(int points) {
        xpText = String.valueOf(points);
    }

    public static final IGuiOverlay HUD_XP = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        if (!enabled) return;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, XP_TEXTURE);

        Player player = gui.getMinecraft().player;
        if (player == null) return;

        int textureWidth = 60;
        int textureHeight = 54;

        int color;
        int v;

        if (player.hasItemInSlot(EquipmentSlot.HEAD) && (player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof HazmatSuitItem)) {
            color = 0x9FACDE;
            v = textureHeight / 2;
        } else {
            color = 0x59F5A4;
            v = 0;
        }
        int x = screenWidth - textureWidth;
        int y = screenHeight / 3 * 2;

        guiGraphics.blit(XP_TEXTURE, x, y, 0, v, textureWidth, textureHeight/2, textureWidth, textureHeight);
        guiGraphics.drawString(gui.getFont(), String.valueOf(xpText), x + textureWidth / 3 * 2 - gui.getFont().width(xpText) / 2, y + textureHeight / 4 - gui.getFont().lineHeight / 2 + 1, color, false);
    };
}
