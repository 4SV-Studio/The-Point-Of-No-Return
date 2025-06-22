package org.studio4sv.tponr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.client.ClientAttributesData;
import org.studio4sv.tponr.mechanics.stamina.PlayerStaminaProvider;

import java.util.concurrent.atomic.AtomicReference;

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

        int upgradeScreenWidth = 286;
        int upgradeScreenHeight = 168;
        pGuiGraphics.blit(ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "textures/gui/upgrade_screen.png"), this.width / 2 - upgradeScreenWidth / 2, this.height / 2 - upgradeScreenHeight / 2, 0, 0, upgradeScreenWidth, upgradeScreenHeight, upgradeScreenWidth, upgradeScreenHeight);

        int screenWidthStart = this.width / 2 - upgradeScreenWidth / 2;
        int screenHeightStart = this.height / 2 - upgradeScreenHeight / 2;

        int level = ClientAttributesData.getValue("Level");
        String levelText = Component.translatable("gui.tponr.level").getString() + " " + level;
        pGuiGraphics.drawString(this.font, levelText, screenWidthStart + 36, screenHeightStart + 21, 0x11FC67, false);

        int neededXP = 807 + 50 * (level - 1);

        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().scale(0.9F, 0.9F, 1.0F);
        String expText = "EXP: " + player.totalExperience + " / " + neededXP;
        pGuiGraphics.drawString(this.font, expText,  (int) ((screenWidthStart + 26) / 0.9F), (int) ((screenHeightStart + 41) / 0.9F), 0x252726, false);
        pGuiGraphics.pose().popPose();

        String statsText = Component.translatable("gui.tponr.stats").getString();
        pGuiGraphics.drawString(this.font, statsText, screenWidthStart + 36, screenHeightStart + 62, 0x11FC67, false);

        String healthText = Component.translatable("stat.tponr.health").getString();
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().scale(0.9F, 0.9F, 1.0F);
        pGuiGraphics.drawString(this.font, healthText, (int) ((screenWidthStart + 38) / 0.9F), (int) ((screenHeightStart + 92) / 0.9F), 0xF6F6F6, false);
        pGuiGraphics.pose().popPose();

        String staminaText = Component.translatable("stat.tponr.stamina_capacity").getString();
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().scale(0.9F, 0.9F, 1.0F);
        pGuiGraphics.drawString(this.font, staminaText, (int) ((screenWidthStart + 38) / 0.9F), (int) ((screenHeightStart + 104) / 0.9F), 0xF6F6F6, false);
        pGuiGraphics.pose().popPose();

        String manaText = Component.translatable("stat.tponr.mana").getString();
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().scale(0.9F, 0.9F, 1.0F);
        pGuiGraphics.drawString(this.font, "§k" + manaText, (int) ((screenWidthStart + 38) / 0.9F), (int) ((screenHeightStart + 117) / 0.9F), 0xF6F6F6, false);
        pGuiGraphics.pose().popPose();



        String currentHealthText = String.valueOf((int) player.getHealth());
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().scale(0.9F, 0.9F, 1.0F);
        pGuiGraphics.drawString(this.font, currentHealthText, (int) ((screenWidthStart + 94 - font.width(currentHealthText)) / 0.9F), (int) ((screenHeightStart + 92) / 0.9F), 0xF6F6F6, false);
        pGuiGraphics.pose().popPose();

        AtomicReference<String> currentStaminaText = new AtomicReference<>();
        player.getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(stamina -> {
            currentStaminaText.set(String.valueOf(stamina.getStamina()));
        });
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().scale(0.9F, 0.9F, 1.0F);
        pGuiGraphics.drawString(this.font, currentStaminaText.get(), (int) ((screenWidthStart + 94 - font.width(currentStaminaText.get())) / 0.9F), (int) ((screenHeightStart + 105) / 0.9F), 0xF6F6F6, false);
        pGuiGraphics.pose().popPose();

        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().scale(0.9F, 0.9F, 1.0F);
        pGuiGraphics.drawString(this.font, "???", (int) ((screenWidthStart + 94 - font.width("???")) / 0.9F), (int) ((screenHeightStart + 117) / 0.9F), 0xF6F6F6, false);
        pGuiGraphics.pose().popPose();

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}