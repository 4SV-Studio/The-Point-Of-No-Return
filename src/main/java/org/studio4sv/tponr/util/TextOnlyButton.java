package org.studio4sv.tponr.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class TextOnlyButton extends Button {
    private final float scale;
    private final int color;
    private final int yOffset;

    public TextOnlyButton(int x, int y, int width, int height, Component message, OnPress onPress, float scale, int color, int yOffset) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
        this.scale = scale;
        this.color = color;
        this.yOffset = yOffset;
    }

    public TextOnlyButton(int x, int y, int width, int height, Component message, OnPress onPress) {
        this(x, y, width, height, message, onPress, 1.0F);
    }

    public TextOnlyButton(int x, int y, int width, int height, Component message, OnPress onPress, float scale) {
        this(x, y, width, height, message, onPress, scale, 0xFFFFFF);
    }

    public TextOnlyButton(int x, int y, int width, int height, Component message, OnPress onPress, float scale, int color) {
        this(x, y, width, height, message, onPress, scale, color, 0);
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
            guiGraphics.drawString(font, message, 0, yOffset, color, false);
            guiGraphics.pose().popPose();
        }
    }

    public static TextOnlyButton.Builder textOnlyBuilder(Component message, OnPress onPress) {
        return new TextOnlyButton.Builder(message, onPress);
    }

    public static class Builder {
        private final Component message;
        private final OnPress onPress;
        private int x, y, width, height;
        private float scale = 1.0f;
        private int color = 0xFFFFFF;
        private int yOffset = 0;

        public Builder(Component message, OnPress onPress) {
            this.message = message;
            this.onPress = onPress;
        }

        public Builder bounds(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder scale(float scale) {
            this.scale = scale;
            return this;
        }

        public Builder color(int color) {
            this.color = color;
            return this;
        }

        public Builder yOffset(int yOffset) {
            this.yOffset = yOffset;
            return this;
        }

        public TextOnlyButton build() {
            return new TextOnlyButton(x, y, width, height, message, onPress, scale, color, yOffset);
        }
    }
}