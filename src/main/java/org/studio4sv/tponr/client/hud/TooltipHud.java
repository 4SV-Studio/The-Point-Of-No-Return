package org.studio4sv.tponr.client.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.studio4sv.tponr.blocks.custom.SuitCharger.SuitChargerBlock;
import org.studio4sv.tponr.blocks.custom.SuitCharger.SuitChargerSubBlock;
import org.studio4sv.tponr.blocks.entity.SuitCharger.SuitChargerBlockEntity;
import org.studio4sv.tponr.blocks.entity.SuitCharger.SuitChargerSubBlockEntity;

public class TooltipHud {
    public static final IGuiOverlay HUD_TOOLTIP = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        String finalText = "";

        Level level = Minecraft.getInstance().level;
        if (level == null) return;
        HitResult hitResult = Minecraft.getInstance().hitResult;
        if (hitResult == null) return;
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) hitResult;
            BlockPos pos = blockHit.getBlockPos();
            Block block = level.getBlockState(pos).getBlock();
            if (block instanceof SuitChargerBlock) {
                if (level.getBlockEntity(pos) instanceof SuitChargerBlockEntity blockEntity) {
                    if (blockEntity.getStoredItem().isEmpty()) return;
                    float charge = blockEntity.getChargeFloat();
                    finalText = String.format("%.2f", charge) + "%";
                }
            } else if (block instanceof SuitChargerSubBlock) {
                if (level.getBlockEntity(pos) instanceof SuitChargerSubBlockEntity subBlockEntity) {
                    BlockPos mainBlockPos = subBlockEntity.getMainBlockPos();
                    if (mainBlockPos != null && level.getBlockEntity(mainBlockPos) instanceof SuitChargerBlockEntity mainBlockEntity) {
                        if (mainBlockEntity.getStoredItem().isEmpty()) return;
                        float charge = mainBlockEntity.getChargeFloat();
                        finalText = String.format("%.2f", charge) + "%";
                    }
                }
            }
        }

        if (!finalText.isEmpty()) {
            drawOutline(gui, guiGraphics, screenWidth / 2 + 5, screenHeight / 2 - gui.getFont().lineHeight, finalText, 0x000000);
            guiGraphics.drawString(gui.getFont(), finalText, screenWidth / 2 + 5, screenHeight / 2 - gui.getFont().lineHeight, 0x70B8FC, false);
        }
    };

    private static void drawOutline(ForgeGui gui, GuiGraphics guiGraphics, int x, int y, String text, int color) {
        guiGraphics.drawString(gui.getFont(), text, x + 1, y, color, false);
        guiGraphics.drawString(gui.getFont(), text, x - 1, y, color, false);
        guiGraphics.drawString(gui.getFont(), text, x, y + 1, color, false);
        guiGraphics.drawString(gui.getFont(), text, x, y - 1, color, false);
    }
}