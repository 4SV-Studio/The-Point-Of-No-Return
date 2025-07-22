package org.studio4sv.tponr.client.gui.SuitDyerGui;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.studio4sv.tponr.blocks.custom.SuitDyer.SuitDyerBlock;
import org.studio4sv.tponr.registers.ModMenus;

public class SuitDyerMenu extends AbstractContainerMenu {
    private final BlockPos pos;
    private final Level level;

    public SuitDyerMenu(int windowId, BlockPos pos, Inventory playerInventory, Player player) {
        super(ModMenus.SUIT_DYE_MENU.get(), windowId);
        this.pos = pos;
        this.level = player.level();
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return level.getBlockState(pos).getBlock() instanceof SuitDyerBlock &&
               player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64;
    }
}
