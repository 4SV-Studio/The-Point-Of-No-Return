package org.studio4sv.tponr.client.gui.SuitDyerGui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.networking.ModMessages;
import org.studio4sv.tponr.networking.packet.C2S.DyerChangeColorS2CPacket;
import org.studio4sv.tponr.util.CenteredEditBox;
import org.studio4sv.tponr.util.ColorUtils;
import org.studio4sv.tponr.util.TextOnlyButton;

public class SuitDyerScreen extends AbstractContainerScreen<SuitDyerMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "textures/gui/suit_dyer.png");

    private EditBox redField;
    private EditBox greenField;
    private EditBox blueField;

    public SuitDyerScreen(SuitDyerMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 286;
        this.imageHeight = 168;
    }

    @Override
    protected void init() {
        super.init();

        redField = createColorField(leftPos + 55, topPos + 30);
        greenField = createColorField(leftPos + 55, topPos + 51);
        blueField = createColorField(leftPos + 55, topPos + 72);

        addRenderableWidget(redField);
        addRenderableWidget(greenField);
        addRenderableWidget(blueField);

        redField.setTextColor(0xFF0000);
        greenField.setTextColor(0x00FF00);
        blueField.setTextColor(0x0000FF);

        // Sub buttons
        Button redSubButton = TextOnlyButton.textOnlyBuilder(Component.literal(""), btn -> redField.setValue(String.valueOf(Math.max(0, Math.min(parseColor(redField.getValue()) - 1, 255))))).bounds(leftPos + 34, topPos + 30, 16, 16).build();
        Button greenSubButton = TextOnlyButton.textOnlyBuilder(Component.literal(""), btn -> greenField.setValue(String.valueOf(Math.max(0, Math.min(parseColor(greenField.getValue()) - 1, 255))))).bounds(leftPos + 34, topPos + 51, 16, 16).build();
        Button blueSubButton = TextOnlyButton.textOnlyBuilder(Component.literal(""), btn -> blueField.setValue(String.valueOf(Math.max(0, Math.min(parseColor(blueField.getValue()) - 1, 255))))).bounds(leftPos + 34, topPos + 72, 16, 16).build();

        // Plus buttons
        Button redPlusButton = TextOnlyButton.textOnlyBuilder(Component.literal(""), btn -> redField.setValue(String.valueOf(Math.max(0, Math.min(parseColor(redField.getValue()) + 1, 255))))).bounds(leftPos + 102, topPos + 30, 16, 16).build();
        Button greenPlusButton = TextOnlyButton.textOnlyBuilder(Component.literal(""), btn -> greenField.setValue(String.valueOf(Math.max(0, Math.min(parseColor(greenField.getValue()) + 1, 255))))).bounds(leftPos + 102, topPos + 51, 16, 16).build();
        Button bluePlusButton = TextOnlyButton.textOnlyBuilder(Component.literal(""), btn -> blueField.setValue(String.valueOf(Math.max(0, Math.min(parseColor(blueField.getValue()) + 1, 255))))).bounds(leftPos + 102, topPos + 72, 16, 16).build();

        // Apply button
        Button applyButton = TextOnlyButton.textOnlyBuilder(Component.translatable("gui.tponr.apply"), btn -> {
            int r = parseColor(redField.getValue());
            int g = parseColor(greenField.getValue());
            int b = parseColor(blueField.getValue());

            ModMessages.sendToServer(new DyerChangeColorS2CPacket(ColorUtils.rgbToHex(r, g, b), menu.getBlockPos()));
        }).scale(0.8F).yOffset(1).color(0x000000).bounds(leftPos + 33, topPos + 120, 86, 9).build();

        addRenderableWidget(redSubButton);
        addRenderableWidget(greenSubButton);
        addRenderableWidget(blueSubButton);

        addRenderableWidget(redPlusButton);
        addRenderableWidget(greenPlusButton);
        addRenderableWidget(bluePlusButton);

        addRenderableWidget(applyButton);
    }

    private EditBox createColorField(int x, int y) {
        EditBox box = new CenteredEditBox(font, x, y, 42, 16, Component.literal("0"));
        box.setMaxLength(3);
        box.setFilter(s -> s.matches("\\d{0,3}"));
        box.setValue("0");
        box.setBordered(false);

        return box;
    }

    private int parseColor(String text) {
        try {
            int val = Integer.parseInt(text);
            return Math.max(0, Math.min(255, val));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        guiGraphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {}
}
