package org.studio4sv.tponr.client.item.FilterBlockItem;

import net.minecraft.resources.ResourceLocation;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.items.FilterBlockItem;
import software.bernie.geckolib.model.GeoModel;

public class FilterBlockItemModel extends GeoModel<FilterBlockItem> {
    @Override
    public ResourceLocation getModelResource(FilterBlockItem Item) {
        return TPONR.id("geo/filter.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FilterBlockItem Item) {
        return TPONR.id("textures/block/filter.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FilterBlockItem Item) {
        return TPONR.id("animations/filter.animation.json");
    }
}
