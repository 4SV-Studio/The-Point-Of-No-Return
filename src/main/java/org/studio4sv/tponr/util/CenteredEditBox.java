package org.studio4sv.tponr.util;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public class CenteredEditBox extends EditBox {
    private final Font font;
    private boolean isEditable = true;
    private int color = 0xE0E0E0;
    private boolean hasShadow = true;

    public CenteredEditBox(Font font, int x, int y, int width, int height, Component message) {
        super(font, x, y, width, height, message);
        this.font = font;
        this.setBordered(false);
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        String text = this.getValue();
        int textWidth = font.width(text);
        int textX = getX() + (width - textWidth) / 2;
        int textY = getY() + (height - font.lineHeight) / 2;

        graphics.drawString(font, text, textX, textY, color, hasShadow);

        if (this.isFocused() && isEditable) {
            int cursorX = font.width(text.substring(0, this.getCursorPosition()));
            graphics.fill(textX + cursorX, textY - 1, textX + cursorX + 1, textY + 9, 0xFFFFFFFF);
        }
    }

    @Override
    public void setEditable(boolean editable) {
        super.setEditable(editable);
        isEditable = editable;
    }

    @Override
    public void setTextColor(int pColor) {
        super.setTextColor(pColor);
        color = pColor;
    }

    public void setShadow(boolean shadow) {
        hasShadow = shadow;
    }
}
