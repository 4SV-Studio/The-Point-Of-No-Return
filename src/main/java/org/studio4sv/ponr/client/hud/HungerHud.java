package org.studio4sv.ponr.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.studio4sv.ponr.PONR;

public class HungerHud {
    private static final ResourceLocation[] HUNGER_STAGES = new ResourceLocation[6];

    static {
        for (int i = 0; i < 6; i++) {
            HUNGER_STAGES[i] = new ResourceLocation(PONR.MOD_ID,
                    "textures/gui/hunger/hunger_" + (i + 1) + ".png");
        }
    }

    private static int currentHungerStage = 1;

    public static void setHungerStage(int stage) {
        currentHungerStage = Math.max(1, Math.min(6, stage));
    }

    public static final IGuiOverlay HUD_HUNGER = (gui, poseStack, partialTick, screenWidth, screenHeight) -> {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F); // R, G, B, A
        RenderSystem.setShaderTexture(0, HUNGER_STAGES[currentHungerStage - 1]);

        int padding = 10;

        int width = 35;
        int height = 35;
        int x = screenWidth - width - padding;
        int y = height + (padding * 2);

        poseStack.blit(HUNGER_STAGES[currentHungerStage - 1], x, y, 0, 0, width, height, width, height); // texture, x, y, u, v, width, height, textureWidth, textureHeight
    };
}
