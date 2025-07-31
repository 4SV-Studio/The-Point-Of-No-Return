package org.studio4sv.tponr.client.item.SuitChargerItem;

import net.minecraft.resources.ResourceLocation;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.blocks.entity.SuitCharger.SuitChargerBlockEntity;
import org.studio4sv.tponr.items.SuitChargerItem;
import software.bernie.geckolib.model.GeoModel;

public class SuitChargerItemModel extends GeoModel<SuitChargerItem> {
    @Override
    public ResourceLocation getModelResource(SuitChargerItem animatable) {
        return TPONR.id("geo/charger.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SuitChargerItem animatable) {
        return TPONR.id("textures/block/charger.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SuitChargerItem animatable) {
        return TPONR.id("animations/empty.animation.json");
    }
}
