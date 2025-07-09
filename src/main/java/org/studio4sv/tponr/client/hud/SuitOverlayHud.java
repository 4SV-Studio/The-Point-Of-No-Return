package org.studio4sv.tponr.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.studio4sv.tponr.TPONR;

public class SuitOverlayHud {
    private static final ResourceLocation OVERLAY_TEXTURE = ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "textures/gui/suit_overlay.png");
    private static final ResourceLocation BATTERY_TEXTURE = ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "textures/gui/battery.png");
    private static boolean enabled = true;
    private static float charge;

    public static void toggle() {
        enabled = !enabled;
    }

    public static void setEnabled(boolean enabled) {
        SuitOverlayHud.enabled = enabled;
    }

    public static void setCharge(float charge) {
        SuitOverlayHud.charge = charge;
    }

    public static final IGuiOverlay HUD_OVERLAY = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        if (!enabled) return;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, OVERLAY_TEXTURE);
        RenderSystem.enableBlend();

        int overlayWidth = 910;
        int overlayHeight = 1026;

        float scale = (float) screenHeight / overlayHeight;

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1.0f);

        guiGraphics.blit( // Left side
                OVERLAY_TEXTURE,
                0, 0,
                0f, 0f,
                overlayWidth / 2, overlayHeight,
                overlayWidth, overlayHeight
        );
        guiGraphics.blit( // Right side
                OVERLAY_TEXTURE,
                (int) (screenWidth / scale) - overlayWidth / 2, 0,
                (float) overlayWidth / 2, 0,
                overlayWidth / 2, overlayHeight,
                overlayWidth, overlayHeight
        );

        guiGraphics.pose().popPose();

        RenderSystem.setShaderTexture(0, BATTERY_TEXTURE);

        int batteryWidth = 12;
        int batteryHeight = 44;
        int batteryX = 10;
        int batteryY = screenHeight - 10 - batteryHeight / 2;

        guiGraphics.blit( // Empty battery
                BATTERY_TEXTURE,
                batteryX, batteryY,
                0, 0,
                batteryWidth, batteryHeight / 2,
                batteryWidth, batteryHeight
        );

        int filledHeight = Math.round((float) batteryHeight / 100 * charge);
        int fillY = batteryY + (batteryHeight - filledHeight);

        /*guiGraphics.blit( // Filled battery
                BATTERY_TEXTURE,
                batteryX,
                fillY,
                0, (float) batteryHeight / 2 + filledHeight,
                batteryWidth, filledHeight,
                batteryWidth, batteryHeight / 2
        );*/

        String chargeText = String.format("%.2f", charge) + "%";
        guiGraphics.drawString(
                gui.getFont(),
                chargeText,
                batteryX + 25 - gui.getFont().width(chargeText) / 2,
                batteryY + gui.getFont().lineHeight / 2,
                0xFFFFFF,
                false
        );

        RenderSystem.disableBlend();
    };
}
