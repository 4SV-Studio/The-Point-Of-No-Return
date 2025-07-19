package org.studio4sv.tponr.blocks.entity.BunkerDoor;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.studio4sv.tponr.registers.ModBlockEntities;

public class BunkerDoorSubBlockEntity extends BlockEntity {
    private BlockPos mainBlockPos;

    public BunkerDoorSubBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BUNKER_DOOR_SUB_ENTITY.get(), pos, state);
    }

    public void setMainBlockPos(BlockPos mainPos) {
        this.mainBlockPos = mainPos;
        setChanged();
    }

    public BlockPos getMainBlockPos() {
        return mainBlockPos;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (mainBlockPos != null) {
            tag.putInt("MainX", mainBlockPos.getX());
            tag.putInt("MainY", mainBlockPos.getY());
            tag.putInt("MainZ", mainBlockPos.getZ());
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("MainX")) {
            this.mainBlockPos = new BlockPos(tag.getInt("MainX"), tag.getInt("MainY"), tag.getInt("MainZ"));
        }
    }
}
