package org.studio4sv.tponr.blocks.entity.SuitCharger;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.studio4sv.tponr.registers.ModBlockEntities;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

public class SuitChargerSubBlockEntity extends BlockEntity implements GeoBlockEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private BlockPos mainBlockPos;

    public SuitChargerSubBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SUIT_CHARGER_SUB_ENTITY.get(), pPos, pBlockState);
    }

    public void setMainBlockPos(BlockPos mainBlockPos) {
        this.mainBlockPos = mainBlockPos;
        setChanged();
    }

    public BlockPos getMainBlockPos() {
        return mainBlockPos;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<SuitChargerSubBlockEntity> suitChargerSubBlockEntityAnimationState) {
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
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
