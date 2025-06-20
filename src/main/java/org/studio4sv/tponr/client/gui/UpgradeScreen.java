package org.studio4sv.tponr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.studio4sv.tponr.client.ClientAttributesData;

public class UpgradeScreen extends Screen {
    public UpgradeScreen() {
        super(Component.translatable("gui.tponr.upgrade"));
    }

    @Override
    protected void init() {

        super.init();
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        Player player = Minecraft.getInstance().player;

        int level = ClientAttributesData.getValue("Level");
        String levelText = Component.translatable("gui.tponr.level").getString() + " " + level;
        pGuiGraphics.drawString(this.font, levelText, this.width / 4 - font.width(levelText) / 2, this.height / 10, 0x11FC67, false);

        int neededXP = 807 + 50 * (level - 1);

        String expText = "EXP: " + player.totalExperience + " / " + neededXP;
        pGuiGraphics.drawString(this.font, expText, this.width / 4 - font.width(expText) / 6 * 5, this.height / 8, 0x252726, false);

        String statsText = Component.translatable("gui.tponr.stats").getString();
        pGuiGraphics.drawString(this.font, statsText, this.width / 4 - font.width(statsText) / 2, this.height / 6, 0x11FC67, false);

        String healthText = Component.translatable("stat.tponr.health").getString();
        pGuiGraphics.drawString(this.font, healthText, this.width / 4 - font.width(healthText) / 2, this.height / 2 + 15, 0x11FC67, false);

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }
}