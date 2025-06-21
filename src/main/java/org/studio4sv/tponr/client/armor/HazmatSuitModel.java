package org.studio4sv.tponr.client.armor;

import net.minecraft.resources.ResourceLocation;
import org.studio4sv.tponr.items.HazmatSuits.BaseHazmatSuitItem;
import software.bernie.geckolib.model.GeoModel;

public class HazmatSuitModel extends GeoModel<BaseHazmatSuitItem> {
    @Override
    public ResourceLocation getModelResource(BaseHazmatSuitItem animatable) {
        return animatable.getModelResource();
    }

    @Override
    public ResourceLocation getTextureResource(BaseHazmatSuitItem animatable) {
        return animatable.getTextureResource();
    }

    @Override
    public ResourceLocation getAnimationResource(BaseHazmatSuitItem animatable) {
        return animatable.getAnimationResource();
    }
}
