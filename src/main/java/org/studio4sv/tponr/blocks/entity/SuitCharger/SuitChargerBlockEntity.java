package org.studio4sv.tponr.blocks.entity.SuitCharger;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.studio4sv.tponr.items.HazmatSuitPackItem;
import org.studio4sv.tponr.networking.ModMessages;
import org.studio4sv.tponr.networking.packet.S2C.SuitChargerDataSyncS2CPacket;
import org.studio4sv.tponr.registers.ModBlockEntities;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

public class SuitChargerBlockEntity extends BlockEntity implements GeoBlockEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private ItemStack storedItem = ItemStack.EMPTY;

    public SuitChargerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SUIT_CHARGER_ENTITY.get(), pPos, pBlockState);
    }

    public void setStoredItem(ItemStack storedItem) {
        this.storedItem = storedItem;
        setChanged();
        if (!level.isClientSide) {
            for (ServerPlayer player : ((ServerLevel) level).players()) {
                ModMessages.sendToPlayer(new SuitChargerDataSyncS2CPacket(this.getBlockPos(), this.storedItem), player);
            }
        }
    }

    public ItemStack getStoredItem() {
        return storedItem;
    }

    public int getCharge() {
        if (storedItem.getItem() instanceof HazmatSuitPackItem) {
            return storedItem.getOrCreateTag().getInt("charge");
        }
        return 0;
    }

    public float getChargeFloat() {
        if (storedItem.getItem() instanceof HazmatSuitPackItem) {
            return storedItem.getOrCreateTag().getFloat("charge");
        }
        return 0.0f;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<SuitChargerBlockEntity> suitChargerBlockEntityAnimationState) {
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
}
