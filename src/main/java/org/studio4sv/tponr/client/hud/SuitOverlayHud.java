package org.studio4sv.tponr.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.util.RadiationUtils;

public class SuitOverlayHud {
    private static final ResourceLocation OVERLAY_TEXTURE = TPONR.id("textures/gui/suit_overlay.png");
    private static final ResourceLocation BATTERY_TEXTURE = TPONR.id("textures/gui/battery.png");
    private static final ResourceLocation DANGER_TEXTURE = TPONR.id("textures/gui/danger.png");
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

    private static void drawOutline(ForgeGui gui, GuiGraphics guiGraphics, int x, int y, String text, int color) {
        guiGraphics.drawString(gui.getFont(), text, x + 1, y, color, false);
        guiGraphics.drawString(gui.getFont(), text, x - 1, y, color, false);
        guiGraphics.drawString(gui.getFont(), text, x, y + 1, color, false);
        guiGraphics.drawString(gui.getFont(), text, x, y - 1, color, false);
    }

    public static final IGuiOverlay HUD_OVERLAY = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        if (!enabled) return;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, OVERLAY_TEXTURE);
        RenderSystem.enableBlend();

        int overlayWidth = 910;
        int overlayHeight = 1026;

        float overlayScale = (float) screenHeight / overlayHeight;

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(overlayScale, overlayScale, 1.0f);

        guiGraphics.blit( // Left side
                OVERLAY_TEXTURE,
                0, 0,
                0f, 0f,
                overlayWidth / 2, overlayHeight,
                overlayWidth, overlayHeight
        );
        guiGraphics.blit( // Right side
                OVERLAY_TEXTURE,
                (int) (screenWidth / overlayScale) - overlayWidth / 2, 0,
                (float) overlayWidth / 2, 0,
                overlayWidth / 2, overlayHeight,
                overlayWidth, overlayHeight
        );

        guiGraphics.pose().popPose();

        RenderSystem.setShaderTexture(0, BATTERY_TEXTURE);

        int widgetX = 45;

        int batteryWidth = 24;
        int batteryHeight = 22;

        int dangerWidth = 23;
        int dangerHeight = 20;

        int batteryY = (screenHeight - batteryHeight - 2);
        int dangerY = (screenHeight - batteryHeight - 8 - dangerHeight);

        guiGraphics.blit( // Danger sign
                DANGER_TEXTURE,
                widgetX - dangerWidth / 4, dangerY,
                0, 0,
                dangerWidth, dangerHeight,
                dangerWidth, dangerHeight
        );

        guiGraphics.blit( // Empty battery
                BATTERY_TEXTURE,
                widgetX, batteryY,
                (float) batteryWidth / 2, 0,
                batteryWidth / 2, batteryHeight,
                batteryWidth, batteryHeight
        );

        int filledHeight = Math.round((float) batteryHeight / 100 * charge);
        int fillY = batteryY + (batteryHeight - filledHeight);

        guiGraphics.blit( // Filled battery
                BATTERY_TEXTURE,
                widgetX, fillY,
                0, batteryHeight - filledHeight,
                batteryWidth / 2, filledHeight,
                batteryWidth, batteryHeight
        );

        String chargeText = String.format("%.2f", charge) + "%";
        drawOutline(gui, guiGraphics, widgetX + 15, batteryY, chargeText, 0x000000);
        guiGraphics.drawString(
                gui.getFont(),
                chargeText,
                widgetX + 15,
                batteryY,
                0x70B8FC,
                false
        );

        int levelY = batteryY - 25;

        String radiationLvlText = Component.translatable("gui.tponr.lvl").getString() + " " + RadiationUtils.levelForPlayer(Minecraft.getInstance().player);
        drawOutline(gui, guiGraphics, widgetX + 20, levelY, radiationLvlText, 0x000000);
        guiGraphics.drawString(
                gui.getFont(),
                radiationLvlText,
                widgetX + 20,
                levelY,
                0x70B8FC,
                false
        );

        RenderSystem.disableBlend();
    };
}
