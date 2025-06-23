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
        super(Component.translatable("gui.tponr.upgrade_screen"));
    }

    @Override
    protected void init() {
        super.init();
    }

    private void renderScaledText(GuiGraphics pGuiGraphics, String text, int x, int y, int color, boolean pDropShadow, float scale) {
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().scale(scale, scale, 1.0F);
        pGuiGraphics.drawString(this.font, text, (int) ((x) / scale), (int) ((y) / scale), color, pDropShadow);
        pGuiGraphics.pose().popPose();
    }

    private String getTranslation(String key) {
        return Component.translatable(key).getString();
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);

        Player player = Minecraft.getInstance().player;

        int upgradeScreenWidth = 286;
        int upgradeScreenHeight = 168;
        pGuiGraphics.blit(ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "textures/gui/upgrade_screen.png"),
                this.width / 2 - upgradeScreenWidth / 2,
                this.height / 2 - upgradeScreenHeight / 2,
                0,
                0,
                upgradeScreenWidth,
                upgradeScreenHeight,
                upgradeScreenWidth,
                upgradeScreenHeight
        );

        int screenWidthStart = this.width / 2 - upgradeScreenWidth / 2;
        int screenHeightStart = this.height / 2 - upgradeScreenHeight / 2;

        pGuiGraphics.drawString(this.font, getTranslation("gui.tponr.level") + " " + ClientAttributesData.getValue("Level"), screenWidthStart + 36, screenHeightStart + 21, 0x11FC67, false);

        int level = ClientAttributesData.getValue("Level");
        int neededXP = 804 + 50 * (level - 1);
        renderScaledText(pGuiGraphics, "EXP: " + neededXP + " / " + player.totalExperience, screenWidthStart + 26, screenHeightStart + 41, 0x252726, false, 0.9F);

        renderScaledText(pGuiGraphics, getTranslation("gui.tponr.stats"), screenWidthStart + 36, screenHeightStart + 62, 0x11FC67, false, 1.0F);

        renderScaledText(pGuiGraphics, getTranslation("gui.tponr.health_capacity"), screenWidthStart + 38, screenHeightStart + 91, 0xF6F6F6, false, 0.9F);
        renderScaledText(pGuiGraphics, getTranslation("gui.tponr.stamina_capacity"), screenWidthStart + 38, screenHeightStart + 104, 0xF6F6F6, false, 0.9F);
        renderScaledText(pGuiGraphics, "§k" + getTranslation("gui.tponr.mana_capacity"), screenWidthStart + 38, screenHeightStart + 118, 0xF6F6F6, false, 0.9F);

        renderScaledText(pGuiGraphics, String.valueOf((int) player.getHealth()), screenWidthStart + 94 - font.width(String.valueOf((int) player.getHealth())), screenHeightStart + 91, 0xF6F6F6, false, 0.9F);

        AtomicReference<String> currentStaminaText = new AtomicReference<>();
        player.getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(stamina -> {
            currentStaminaText.set(String.valueOf(stamina.getStamina()));
        });
        renderScaledText(pGuiGraphics, currentStaminaText.get(), screenWidthStart + 94 - font.width(currentStaminaText.get()), screenHeightStart + 106, 0xF6F6F6, false, 0.9F);
        renderScaledText(pGuiGraphics, "???", screenWidthStart + 94 - font.width("???"), screenHeightStart + 116, 0xF6F6F6, false, 0.9F);

        renderScaledText(pGuiGraphics, getTranslation("gui.tponr.attributes"), screenWidthStart + 172, screenHeightStart + 20, 0x11FC67, false, 1.0F);

        renderScaledText(pGuiGraphics, getTranslation("stat.tponr.health"), screenWidthStart + 166, screenHeightStart + 39, 0xF6F6F6, false, 0.9F);
        renderScaledText(pGuiGraphics, getTranslation("stat.tponr.stamina"), screenWidthStart + 166, screenHeightStart + 52, 0xF6F6F6, false, 0.9F);
        renderScaledText(pGuiGraphics, getTranslation("stat.tponr.strength"), screenWidthStart + 166, screenHeightStart + 64, 0xF6F6F6, false, 0.9F);
        renderScaledText(pGuiGraphics, getTranslation("stat.tponr.agility"), screenWidthStart + 166, screenHeightStart + 76, 0xF6F6F6, false, 0.9F);
        renderScaledText(pGuiGraphics, getTranslation("stat.tponr.intelligence"), screenWidthStart + 166, screenHeightStart + 88, 0xF6F6F6, false, 0.9F);
        renderScaledText(pGuiGraphics, getTranslation("stat.tponr.luck"), screenWidthStart + 166, screenHeightStart + 100, 0xF6F6F6, false, 0.9F);

        renderScaledText(pGuiGraphics, getTranslation("gui.tponr.level_up"), screenWidthStart + 146 + 131 / 2 - font.width(getTranslation("gui.tponr.level_up")) / 2 + 5, screenHeightStart + 119, 0x0B0E0E, false, 0.7F);

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}