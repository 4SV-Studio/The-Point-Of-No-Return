package org.studio4sv.tponr.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class TextOnlyButton extends Button {
    private final float scale;
    private final int color;

    public TextOnlyButton(int x, int y, int width, int height, Component message, OnPress onPress, float scale, int color) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
        this.scale = scale;
        this.color = color;
    }

    public TextOnlyButton(int x, int y, int width, int height, Component message, OnPress onPress) {
        this(x, y, width, height, message, onPress, 1.0F);
    }

    public TextOnlyButton(int x, int y, int width, int height, Component message, OnPress onPress, float scale) {
        this(x, y, width, height, message, onPress, scale, 0xFFFFFF);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.visible) {
            Font font = Minecraft.getInstance().font;
            Component message = this.getMessage();
            int textWidth = (int)(font.width(message) * scale);
            int textHeight = (int)(font.lineHeight * scale);
            int xPos = this.getX() + (this.getWidth() - textWidth) / 2;
            int yPos = this.getY() + (this.getHeight() - textHeight) / 2;

            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(xPos, yPos, 0);
            guiGraphics.pose().scale(scale, scale, 1.0F);
            guiGraphics.drawString(font, message, 0, 0, color, false);
            guiGraphics.pose().popPose();
        }
    }
}