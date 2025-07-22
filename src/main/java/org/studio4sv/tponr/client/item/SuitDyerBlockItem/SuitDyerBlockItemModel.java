package org.studio4sv.tponr.client.item.SuitDyerBlockItem;

import net.minecraft.resources.ResourceLocation;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.items.SuitDyerBlockItem;
import software.bernie.geckolib.model.GeoModel;

public class SuitDyerBlockItemModel extends GeoModel<SuitDyerBlockItem> {
    @Override
    public ResourceLocation getModelResource(SuitDyerBlockItem suitDyerBlockItem) {
        return ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "geo/suit_dyer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SuitDyerBlockItem suitDyerBlock) {
        return ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "textures/block/suit_dyer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SuitDyerBlockItem suitDyerBlock) {
        return ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "animations/suit_dyer.animation.json");
    }
}
