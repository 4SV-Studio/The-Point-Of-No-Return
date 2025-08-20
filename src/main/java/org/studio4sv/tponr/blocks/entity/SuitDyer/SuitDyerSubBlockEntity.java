package org.studio4sv.tponr.blocks.entity.SuitDyer;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.studio4sv.tponr.registers.ModBlockEntities;

public class SuitDyerSubBlockEntity extends BlockEntity {
    private BlockPos mainBlockPos;

    public SuitDyerSubBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SUIT_DYER_SUB_ENTITY.get(), pPos, pBlockState);
    }

    public void setMainBlockPos(BlockPos mainPos) {
        this.mainBlockPos = mainPos;
        setChanged();
    }

    public BlockPos getMainBlockPos() {
        return mainBlockPos;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        ListTag pos = new ListTag();
        if (mainBlockPos != null) {
            pos.add(IntTag.valueOf(mainBlockPos.getX()));
            pos.add(IntTag.valueOf(mainBlockPos.getY()));
            pos.add(IntTag.valueOf(mainBlockPos.getZ()));
            pTag.put("mainBlockPos", pos);
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("mainBlockPos")) {
            ListTag pos = pTag.getList("mainBlockPos", 3);
            mainBlockPos = new BlockPos(pos.getInt(0), pos.getInt(1), pos.getInt(2));
        } else {
            mainBlockPos = null;
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
            tag.put("mainBlockPos", list);
        }
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        if (tag.contains("mainBlockPos", ListTag.TAG_LIST)) {
            ListTag list = tag.getList("mainBlockPos", ListTag.TAG_LIST);
            if (list.size() == 3) {
                mainBlockPos = new BlockPos(list.getInt(0), list.getInt(1), list.getInt(2));
            }
        }
    }
}
