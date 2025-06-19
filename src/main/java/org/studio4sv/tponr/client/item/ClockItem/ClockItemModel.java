package org.studio4sv.tponr.client.item.ClockItem;

import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.items.ClockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ClockItemModel extends GeoModel<ClockItem> {
    @Override
    public ResourceLocation getModelResource(ClockItem Item) {
        return ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "geo/clock.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ClockItem Item) {
        return ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "textures/block/clock.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ClockItem Item) {
        return ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "animations/clock.animation.json");
    }
}
