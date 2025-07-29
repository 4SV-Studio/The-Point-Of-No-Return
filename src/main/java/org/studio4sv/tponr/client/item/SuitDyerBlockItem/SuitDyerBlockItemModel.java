package org.studio4sv.tponr.client.item.SuitDyerBlockItem;

import net.minecraft.resources.ResourceLocation;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.items.SuitDyerBlockItem;
import software.bernie.geckolib.model.GeoModel;

public class SuitDyerBlockItemModel extends GeoModel<SuitDyerBlockItem> {
    @Override
    public ResourceLocation getModelResource(SuitDyerBlockItem suitDyerBlockItem) {
        return TPONR.id("geo/suit_dyer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SuitDyerBlockItem suitDyerBlock) {
        return TPONR.id("textures/block/suit_dyer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SuitDyerBlockItem suitDyerBlock) {
        return TPONR.id("animations/suit_dyer.animation.json");
    }
}
