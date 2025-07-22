package org.studio4sv.tponr.blocks.entity.SuitDyer;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.studio4sv.tponr.armor.HazmatSuitItem;
import org.studio4sv.tponr.items.HazmatSuitPackItem;
import org.studio4sv.tponr.registers.ModBlockEntities;
import org.studio4sv.tponr.registers.ModItems;
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
    }

    public ItemStack getStoredItem() {
        return storedItem;
    }

    public ItemStack[] getStoredArmor() {

        if (!(storedItem.getItem() instanceof HazmatSuitPackItem)) {
            return new ItemStack[] {
                    ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY
            };
        }

        CompoundTag tag = storedItem.getOrCreateTag();
        int charge = tag.getInt("charge");
        int color;

        if (storedItem.getItem() instanceof HazmatSuitPackItem dyeable) {
            color = dyeable.getColor(storedItem);
        } else {
            color = 0xFFFFFF;
        }


        ItemStack helmet = new ItemStack(ModItems.HAZMAT_SUIT_HELMET.get());
        ItemStack chest = new ItemStack(ModItems.HAZMAT_SUIT_CHESTPLATE.get());
        ItemStack legs = new ItemStack(ModItems.HAZMAT_SUIT_LEGGINGS.get());
        ItemStack boots = new ItemStack(ModItems.HAZMAT_SUIT_BOOTS.get());

        for (ItemStack piece : new ItemStack[]{helmet, chest, legs, boots}) {
            CompoundTag pieceTag = piece.getOrCreateTag();
            if (piece.getItem() instanceof HazmatSuitItem dyeable) {
                dyeable.setColor(piece, color);
            }
            pieceTag.putInt("charge", charge);
        }

        return new ItemStack[] {helmet, chest, legs, boots};
    }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<SuitDyerBlockEntity> state) {
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
            return true;
        }
        return false;
    }
}

