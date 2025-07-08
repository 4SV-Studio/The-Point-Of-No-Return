package org.studio4sv.tponr.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.studio4sv.tponr.registers.ModItems;

public class HazmatSuitPackItem extends Item implements DyeableLeatherItem {
    public HazmatSuitPackItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean hasCustomColor(@NotNull ItemStack stack) {
        return DyeableLeatherItem.super.hasCustomColor(stack);
    }

    @Override
    public int getColor(@NotNull ItemStack stack) {
        return DyeableLeatherItem.super.hasCustomColor(stack) ? DyeableLeatherItem.super.getColor(stack) : 0xFFFFFF;
    }


    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide) {
            boolean emptyArmor = true;
            for (ItemStack armor : pPlayer.getArmorSlots()) {
                if (!armor.isEmpty()) {
                    emptyArmor = false;
                    break;
                }
            }

            if (!emptyArmor) return InteractionResultHolder.fail(stack);

            CompoundTag tag = stack.getOrCreateTag();
            int color = getColor(stack);
            int charge = tag.contains("charge") ? tag.getInt("charge") : 0;

            pPlayer.setItemSlot(EquipmentSlot.HEAD, createPiece(ModItems.HAZMAT_SUIT_HELMET.get(), color, charge));
            pPlayer.setItemSlot(EquipmentSlot.CHEST, createPiece(ModItems.HAZMAT_SUIT_CHESTPLATE.get(), color, charge));
            pPlayer.setItemSlot(EquipmentSlot.LEGS, createPiece(ModItems.HAZMAT_SUIT_LEGGINGS.get(), color, charge));
            pPlayer.setItemSlot(EquipmentSlot.FEET, createPiece(ModItems.HAZMAT_SUIT_BOOTS.get(), color, charge));

            stack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(stack, pLevel.isClientSide());
    }

    private ItemStack createPiece(Item item, int color, int charge) {
        ItemStack stack = new ItemStack(item);
        stack.getOrCreateTag().putInt("charge", charge);
        DyeableLeatherItem dyeable = (DyeableLeatherItem) item;
        dyeable.setColor(stack, color);
        return stack;
    }
}
