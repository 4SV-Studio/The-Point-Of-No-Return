package org.studio4sv.tponr.blocks.entity.SuitDyer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.joml.Vector3f;
import org.studio4sv.tponr.items.HazmatSuitPackItem;
import org.studio4sv.tponr.networking.ModMessages;
import org.studio4sv.tponr.networking.packet.S2C.SuitDyerDataSyncS2CPacket;
import org.studio4sv.tponr.registers.ModBlockEntities;
import org.studio4sv.tponr.util.ColorUtils;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

public class SuitDyerBlockEntity extends BlockEntity implements GeoBlockEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private ItemStack storedItem = ItemStack.EMPTY;

    public SuitDyerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SUIT_DYER_ENTITY.get(), pPos, pBlockState);
    }

    public void setStoredItem(ItemStack stack) {
        this.storedItem = stack;
        setChanged();
        if (!level.isClientSide) {
            for (ServerPlayer player : ((ServerLevel) level).players()) {
                ModMessages.sendToPlayer(new SuitDyerDataSyncS2CPacket(this.getBlockPos(), this.storedItem), player);
            }
        }
    }

    public ItemStack getStoredItem() {
        return storedItem;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<SuitDyerBlockEntity> state) {
        if (storedItem != null && storedItem.getItem() instanceof HazmatSuitPackItem) {
            state.getController().setAnimation(RawAnimation.begin().then("on", Animation.LoopType.HOLD_ON_LAST_FRAME));
        } else {
            state.getController().setAnimation(RawAnimation.begin().then("off", Animation.LoopType.HOLD_ON_LAST_FRAME));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        CompoundTag itemTag = new CompoundTag();
        storedItem.save(itemTag);
        pTag.put("StoredItem", itemTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        if (pTag.contains("StoredItem")) {
            storedItem = ItemStack.of(pTag.getCompound("StoredItem"));
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();

        CompoundTag itemTag = new CompoundTag();
        storedItem.save(itemTag);
        tag.put("StoredItem", itemTag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag pTag) {
        super.handleUpdateTag(pTag);

        if (pTag.contains("StoredItem")) {
            storedItem = ItemStack.of(pTag.getCompound("StoredItem"));
        }
    }

    public boolean setColor(int color) {
        if (storedItem.getItem() instanceof HazmatSuitPackItem dyeable) {
            dyeable.setColor(storedItem, color);
            setChanged();
            if (!level.isClientSide) {
                for (ServerPlayer player : ((ServerLevel) level).players()) {
                    ModMessages.sendToPlayer(new SuitDyerDataSyncS2CPacket(this.getBlockPos(), this.storedItem), player);
                }
            }
            level.playSound(
                    null,
                    worldPosition,
                    SoundEvents.FIRE_EXTINGUISH,
                    SoundSource.BLOCKS,
                    0.5f,
                    1.5f
            );

            int[] rgb = ColorUtils.hexToRgb(color);
            Direction facing = level.getBlockState(worldPosition).getValue(BlockStateProperties.HORIZONTAL_FACING);
            BlockPos rightPos = worldPosition.relative(facing.getCounterClockWise());
            ((ServerLevel) level).sendParticles(
                    new DustParticleOptions(new Vector3f(rgb[0], rgb[1], rgb[2]), 1.0f),
                    rightPos.getX() + 0.5,
                    rightPos.getY() + 1.0,
                    rightPos.getZ() + 0.5,
                    20,
                    0.3, 0.3, 0.3,
                    0.0
            );
            return true;
        }
        return false;
    }
}

