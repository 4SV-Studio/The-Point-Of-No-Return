package org.studio4sv.tponr.client.item.SuitStandBlockItem;

import net.minecraft.resources.ResourceLocation;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.items.SuitStandBlockItem;
import software.bernie.geckolib.model.GeoModel;

public class SuitStandBlockItemModel extends GeoModel<SuitStandBlockItem> {
    @Override
    public ResourceLocation getModelResource(SuitStandBlockItem blockItem) {
        return TPONR.id("geo/suit_stand.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SuitStandBlockItem blockItem) {
        return TPONR.id("textures/block/suit_stand.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SuitStandBlockItem blockItem) {
        return TPONR.id("animations/empty.animation.json");
    }
}
