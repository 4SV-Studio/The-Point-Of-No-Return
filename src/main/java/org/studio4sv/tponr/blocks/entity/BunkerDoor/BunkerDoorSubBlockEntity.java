package org.studio4sv.tponr.blocks.entity.BunkerDoor;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
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
        ListTag list = new ListTag();
        if (mainBlockPos != null) {
            list.add(IntTag.valueOf(mainBlockPos.getX()));
            list.add(IntTag.valueOf(mainBlockPos.getY()));
            list.add(IntTag.valueOf(mainBlockPos.getZ()));
            tag.put("MainBlockPos", list);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("MainBlockPos", ListTag.TAG_LIST)) {
            ListTag list = tag.getList("MainBlockPos", ListTag.TAG_LIST);
            if (list.size() == 3) {
                mainBlockPos = new BlockPos(list.getInt(0), list.getInt(1), list.getInt(2));
            }
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        ListTag list = new ListTag();
        if (mainBlockPos != null) {
            list.add(IntTag.valueOf(mainBlockPos.getX()));
            list.add(IntTag.valueOf(mainBlockPos.getY()));
            list.add(IntTag.valueOf(mainBlockPos.getZ()));
            tag.put("MainBlockPos", list);
        }
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        if (tag.contains("MainBlockPos", ListTag.TAG_LIST)) {
            ListTag list = tag.getList("MainBlockPos", ListTag.TAG_LIST);
            if (list.size() == 3) {
                mainBlockPos = new BlockPos(list.getInt(0), list.getInt(1), list.getInt(2));
            }
        }
    }
}
