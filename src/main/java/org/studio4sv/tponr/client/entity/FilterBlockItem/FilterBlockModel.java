package org.studio4sv.tponr.client.entity.FilterBlockItem;

import net.minecraft.resources.ResourceLocation;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.blocks.entity.FilterBlockEntity;
import software.bernie.geckolib.model.GeoModel;

public class FilterBlockModel extends GeoModel<FilterBlockEntity> {
    @Override
    public ResourceLocation getModelResource(FilterBlockEntity Item) {
        return TPONR.id("geo/filter.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FilterBlockEntity Item) {
        return TPONR.id("textures/block/filter.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FilterBlockEntity Item) {
        return TPONR.id("animations/filter.animation.json");
    }
}
