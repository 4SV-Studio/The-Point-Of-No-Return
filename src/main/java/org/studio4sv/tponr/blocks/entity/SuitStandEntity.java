package org.studio4sv.tponr.blocks.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.studio4sv.tponr.items.HazmatSuitPackItem;
import org.studio4sv.tponr.networking.ModMessages;
import org.studio4sv.tponr.networking.packet.S2C.SuitStandDataSyncS2CPacket;
import org.studio4sv.tponr.registers.ModBlockEntities;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

public class SuitStandEntity extends BlockEntity implements GeoBlockEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private ItemStack storedItem = ItemStack.EMPTY;

    public SuitStandEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STAND_ENTITY.get(), pos, state);
    }

    public void setStoredItem(ItemStack stack) {
        this.storedItem = stack;
        setChanged();
        if (!level.isClientSide) {
            for (ServerPlayer player : ((ServerLevel) level).players()) {
                ModMessages.sendToPlayer(new SuitStandDataSyncS2CPacket(this.getBlockPos(), this.storedItem), player);
            }
        }
    }

    public ItemStack getStoredItem() {
        return storedItem;
    }

    public int getColor() {
        if (storedItem.getItem() instanceof HazmatSuitPackItem dyeable) {
            return dyeable.getColor(storedItem);
        }
        return 0x000000;
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

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<SuitStandEntity> clockEntityAnimationState) {
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
}