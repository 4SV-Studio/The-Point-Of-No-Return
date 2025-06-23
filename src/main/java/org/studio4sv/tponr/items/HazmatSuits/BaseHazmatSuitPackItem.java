package org.studio4sv.tponr.items.HazmatSuits;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public abstract class BaseHazmatSuitPackItem extends Item {
    public BaseHazmatSuitPackItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        return super.onItemUseFirst(stack, context);
    }
}