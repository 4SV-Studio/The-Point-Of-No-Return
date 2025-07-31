package org.studio4sv.tponr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.mechanics.attributes.PlayerAttributesProvider;
import org.studio4sv.tponr.networking.ModMessages;
import org.studio4sv.tponr.networking.packet.C2S.UpgradeStatsC2SPacket;
import org.studio4sv.tponr.util.TextOnlyButton;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class UpgradeScreen extends Screen {
    private final Map<String, Integer> addedPoints = new HashMap<>();
    private Map<String, Integer> currentStats = new HashMap<>();

    private int upgradeScreenWidth;
    private int upgradeScreenHeight;
    private int screenWidthStart;
    private int screenHeightStart;

    private int currentExp;
    private int currentExpEdited;

    private int unAppliedLevels;

    private int calculateStamina() {
        return 300 + currentStats.get("Stamina") * 8;
    }

    private int calculateLevel() {
        AtomicInteger level = new AtomicInteger(1);
        currentStats.forEach((key, value) -> {
            if (value > 10) {
                level.addAndGet(value - 10);
            }
        });
        return level.get();
    }

    private int getNeededXP() {
        return 804 + (calculateLevel() - 1 + unAppliedLevels) * 3;
    }

    private int newHp;
    private int newStamina;

    public UpgradeScreen() {
        super(Component.translatable("gui.tponr.upgrade_screen"));
    }

    private void addPointTo(String stat) {
        if (currentExpEdited > getNeededXP()) {
            currentExpEdited = currentExpEdited - getNeededXP();
            addedPoints.put(stat, addedPoints.get(stat) + 1);
            unAppliedLevels += 1;
        }
    }

    private void removePointFrom(String stat) {
        if (addedPoints.get(stat) > 0) {
            addedPoints.put(stat, addedPoints.get(stat) - 1);
            currentExpEdited = currentExpEdited + getNeededXP();
            unAppliedLevels -= 1;
        }
    }

    @Override
    protected void init() {
        upgradeScreenWidth = 286;
        upgradeScreenHeight = 168;
        screenWidthStart = this.width / 2 - upgradeScreenWidth / 2;
        screenHeightStart = this.height / 2 - upgradeScreenHeight / 2;

        assert this.getMinecraft().player != null;
        this.getMinecraft().player.getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES).ifPresent(attributes -> currentStats = attributes.getAttributes());

        newHp = (int) this.getMinecraft().player.getMaxHealth();
        newStamina = calculateStamina();

        currentExp = this.getMinecraft().player.totalExperience;
        currentExpEdited = currentExp;

        addedPoints.put("Health", 0);
        addedPoints.put("Stamina", 0);
        addedPoints.put("Strength", 0);
        addedPoints.put("Agility", 0);
        addedPoints.put("Intelligence", 0);
        addedPoints.put("Luck", 0);

        float buttonScale = 0.7F;

        this.addRenderableWidget(new TextOnlyButton(
                screenWidthStart + 265,
                screenHeightStart + 39,
                font.width(">"),
                font.lineHeight,
                Component.literal(">"),
                button -> {
                    addPointTo("Health");
                    newHp = (int) this.getMinecraft().player.getMaxHealth() + addedPoints.get("Health") * 2;
                },
                buttonScale,
                0x11FC67
        ));
        this.addRenderableWidget(new TextOnlyButton(
                screenWidthStart + 240,
                screenHeightStart + 39,
                font.width("<"),
                font.lineHeight,
                Component.literal("<"),
                button -> {
                    removePointFrom("Health");
                    newHp = (int) this.getMinecraft().player.getMaxHealth() + addedPoints.get("Health") * 2;
                },
                buttonScale,
                0x11FC67
        ));

        this.addRenderableWidget(new TextOnlyButton(
                screenWidthStart + 265,
                screenHeightStart + 51,
                font.width(">"),
                font.lineHeight,
                Component.literal(">"),
                button -> {
                    addPointTo("Stamina");
                    newStamina = calculateStamina() + addedPoints.get("Stamina") * 8;
                },
                buttonScale,
                0x11FC67
        ));
        this.addRenderableWidget(new TextOnlyButton(
                screenWidthStart + 240,
                screenHeightStart + 51,
                font.width("<"),
                font.lineHeight,
                Component.literal("<"),
                button -> {
                    removePointFrom("Stamina");
                    newStamina = calculateStamina() + addedPoints.get("Stamina") * 8;
                },
                buttonScale,
                0x11FC67
        ));

        this.addRenderableWidget(new TextOnlyButton(
                screenWidthStart + 265,
                screenHeightStart + 63,
                font.width(">"),
                font.lineHeight,
                Component.literal(">"),
                button -> addPointTo("Strength"),
                buttonScale,
                0x11FC67
        ));
        this.addRenderableWidget(new TextOnlyButton(
                screenWidthStart + 240,
                screenHeightStart + 63,
                font.width("<"),
                font.lineHeight,
                Component.literal("<"),
                button -> removePointFrom("Strength"),
                buttonScale,
                0x11FC67
        ));

        this.addRenderableWidget(new TextOnlyButton(
                screenWidthStart + 265,
                screenHeightStart + 75,
                font.width(">"),
                font.lineHeight,
                Component.literal(">"),
                button -> addPointTo("Agility"),
                buttonScale,
                0x11FC67
        ));
        this.addRenderableWidget(new TextOnlyButton(
                screenWidthStart + 240,
                screenHeightStart + 75,
                font.width("<"),
                font.lineHeight,
                Component.literal("<"),
                button -> removePointFrom("Agility"),
                buttonScale,
                0x11FC67
        ));

        this.addRenderableWidget(new TextOnlyButton(
                screenWidthStart + 265,
                screenHeightStart + 87,
                font.width(">"),
                font.lineHeight,
                Component.literal(">"),
                button -> addPointTo("Intelligence"),
                buttonScale,
                0x11FC67
        ));
        this.addRenderableWidget(new TextOnlyButton(
                screenWidthStart + 240,
                screenHeightStart + 87,
                font.width("<"),
                font.lineHeight,
                Component.literal("<"),
                button -> removePointFrom("Intelligence"),
                buttonScale,
                0x11FC67
        ));

        this.addRenderableWidget(new TextOnlyButton(
                screenWidthStart + 265,
                screenHeightStart + 99,
                font.width(">"),
                font.lineHeight,
                Component.literal(">"),
                button -> addPointTo("Luck"),
                buttonScale,
                0x11FC67
        ));
        this.addRenderableWidget(new TextOnlyButton(
                screenWidthStart + 240,
                screenHeightStart + 99,
                font.width("<"),
                font.lineHeight,
                Component.literal("<"),
                button -> removePointFrom("Luck"),
                buttonScale,
                0x11FC67
        ));

        this.addRenderableWidget(new TextOnlyButton(
                screenWidthStart + 146,
                screenHeightStart + 116,
                131,
                10,
                Component.translatable("gui.tponr.level_up"),
                button -> {
                    if (currentExp < getNeededXP()) return;

                    ModMessages.sendToServer(new UpgradeStatsC2SPacket(addedPoints, currentExp - currentExpEdited)); // TODO: DONT TRUST THE CLIENT

                    for (Map.Entry<String, Integer> entry : currentStats.entrySet()) {
                        String key = entry.getKey();
                        Integer v = entry.getValue();
                        currentStats.put(key, v + addedPoints.get(key));
                    }

                    currentExp = currentExpEdited;
                    newStamina = calculateStamina();
                    addedPoints.forEach((stat, value) -> addedPoints.put(stat, 0));
                },
                0.8F,
                0x0B0E0E,
                1
        ));

        super.init();
    }

    private void renderScaledText(GuiGraphics pGuiGraphics, String text, int x, int y, int color, float scale, int mouseX, int mouseY, String tooltipKey) {
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().scale(scale, scale, 1.0F);
        pGuiGraphics.drawString(this.font, text, (int) ((x) / scale), (int) ((y) / scale), color, false);
        pGuiGraphics.pose().popPose();

        if (tooltipKey != null) {
            int width = this.font.width(text);
            int height = this.font.lineHeight;

            if (mouseX >= x && mouseX <= x + width * scale && mouseY >= y && mouseY <= y + height * scale) {
                pGuiGraphics.renderTooltip(this.font, Component.translatable(tooltipKey), mouseX, mouseY);
            }
        }
    }

    private void renderStatText(GuiGraphics pGuiGraphics, String stat, int x, int y) {
        if(addedPoints.get(stat) > 0) {
            pGuiGraphics.drawString(this.font, String.valueOf(currentStats.get(stat) + addedPoints.get(stat)), x - font.width(String.valueOf(currentStats.get(stat) + addedPoints.get(stat))) / 2, y, 0x26A042, false);
        } else {
            pGuiGraphics.drawString(this.font, String.valueOf(currentStats.get(stat)), x - font.width(String.valueOf(currentStats.get(stat))) / 2, y, 0xF6F6F6, false);
        }
    }

    private String getTranslation(String key) {
        return Component.translatable(key).getString();
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);

        Player player = Minecraft.getInstance().player;
        if (player == null) return;


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

        pGuiGraphics.drawString(this.font, getTranslation("gui.tponr.level") + " " + calculateLevel(), screenWidthStart + 36, screenHeightStart + 21, 0x11FC67, false);

        renderScaledText(pGuiGraphics, "EXP: " + getNeededXP() + " / " + currentExpEdited, screenWidthStart + 26, screenHeightStart + 41, 0x252726, 0.9F, pMouseX, pMouseY, null);

        renderScaledText(pGuiGraphics, getTranslation("gui.tponr.stats"), screenWidthStart + 36, screenHeightStart + 62, 0x11FC67, 1.0F, pMouseX, pMouseY, null);

        renderScaledText(pGuiGraphics, getTranslation("gui.tponr.health_capacity"), screenWidthStart + 38, screenHeightStart + 91, 0xF6F6F6, 0.9F, pMouseX, pMouseY, null);
        renderScaledText(pGuiGraphics, getTranslation("gui.tponr.stamina_capacity"), screenWidthStart + 38, screenHeightStart + 104, 0xF6F6F6, 0.9F, pMouseX, pMouseY, null);
        renderScaledText(pGuiGraphics, "§k" + getTranslation("gui.tponr.mana_capacity"), screenWidthStart + 38, screenHeightStart + 118, 0xF6F6F6, 0.9F, pMouseX, pMouseY, null);

        renderScaledText(pGuiGraphics, String.valueOf((int) player.getHealth()), screenWidthStart + 94 - font.width(String.valueOf((int) player.getHealth())), screenHeightStart + 91, 0xF6F6F6, 0.9F, pMouseX, pMouseY, null);
        renderScaledText(pGuiGraphics, String.valueOf(calculateStamina()), screenWidthStart + 94 - font.width(String.valueOf(calculateStamina())), screenHeightStart + 105, 0xF6F6F6, 0.9F, pMouseX, pMouseY, null);
        renderScaledText(pGuiGraphics, "???", screenWidthStart + 94 - font.width("???"), screenHeightStart + 117, 0xF6F6F6, 0.9F, pMouseX, pMouseY, null);

        renderScaledText(pGuiGraphics, String.valueOf(newHp), screenWidthStart + 101, screenHeightStart + 91, (newHp != player.getMaxHealth()) ? 0x26A042 : 0xF6F6F6, 0.9F, pMouseX, pMouseY, null);
        renderScaledText(pGuiGraphics, String.valueOf(newStamina), screenWidthStart + 101, screenHeightStart + 105, (newStamina != calculateStamina()) ? 0x26A042 : 0xF6F6F6, 0.9F, pMouseX, pMouseY, null);
        renderScaledText(pGuiGraphics, "???", screenWidthStart + 101, screenHeightStart + 117, 0xF6F6F6, 0.9F, pMouseX, pMouseY, null);

        renderScaledText(pGuiGraphics, getTranslation("gui.tponr.attributes"), screenWidthStart + 172, screenHeightStart + 20, 0x11FC67, 1.0F, pMouseX, pMouseY, null);

        renderScaledText(pGuiGraphics, getTranslation("stat.tponr.health"), screenWidthStart + 166, screenHeightStart + 39, 0xF6F6F6, 0.9F, pMouseX, pMouseY, "tooltip.tponr.health");
        renderScaledText(pGuiGraphics, getTranslation("stat.tponr.stamina"), screenWidthStart + 166, screenHeightStart + 52, 0xF6F6F6, 0.9F, pMouseX, pMouseY, "tooltip.tponr.stamina");
        renderScaledText(pGuiGraphics, getTranslation("stat.tponr.strength"), screenWidthStart + 166, screenHeightStart + 64, 0xF6F6F6, 0.9F, pMouseX, pMouseY, "tooltip.tponr.strength");
        renderScaledText(pGuiGraphics, getTranslation("stat.tponr.agility"), screenWidthStart + 166, screenHeightStart + 76, 0xF6F6F6, 0.9F, pMouseX, pMouseY, "tooltip.tponr.agility");
        renderScaledText(pGuiGraphics, getTranslation("stat.tponr.intelligence"), screenWidthStart + 166, screenHeightStart + 88, 0xF6F6F6, 0.9F, pMouseX, pMouseY, "tooltip.tponr.intelligence");
        renderScaledText(pGuiGraphics, getTranslation("stat.tponr.luck"), screenWidthStart + 166, screenHeightStart + 100, 0xF6F6F6, 0.9F, pMouseX, pMouseY, "tooltip.tponr.luck");

        renderStatText(pGuiGraphics, "Health", screenWidthStart + 255,  screenHeightStart + 39);
        renderStatText(pGuiGraphics, "Stamina", screenWidthStart + 255, screenHeightStart + 52);
        renderStatText(pGuiGraphics, "Strength", screenWidthStart + 255, screenHeightStart + 64);
        renderStatText(pGuiGraphics, "Agility", screenWidthStart + 255, screenHeightStart + 76);
        renderStatText(pGuiGraphics, "Intelligence", screenWidthStart + 255, screenHeightStart + 88);
        renderStatText(pGuiGraphics, "Luck", screenWidthStart + 255, screenHeightStart + 100);

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}