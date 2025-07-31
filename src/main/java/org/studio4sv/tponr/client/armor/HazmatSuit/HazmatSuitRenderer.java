package org.studio4sv.tponr.client.armor.HazmatSuit;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.studio4sv.tponr.armor.HazmatSuitItem;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.renderer.DyeableGeoArmorRenderer;

public class HazmatSuitRenderer extends DyeableGeoArmorRenderer<HazmatSuitItem> {
    public HazmatSuitRenderer() {
        super(new HazmatSuitModel());
    }

    @Override
    protected boolean isBoneDyeable(GeoBone bone) {
        return true;
    }

    @Override
    protected @NotNull Color getColorForBone(GeoBone bone) {
        ItemStack itemStack = this.currentStack;

        if (itemStack.getItem() instanceof HazmatSuitItem armorItem) {
            int color = armorItem.getColor(itemStack);

            return Color.ofOpaque(color);
        }

        return Color.WHITE;
    }

    @Override
    public void setupAnim(Entity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {}
}
