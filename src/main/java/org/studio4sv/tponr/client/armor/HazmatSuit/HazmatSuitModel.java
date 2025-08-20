package org.studio4sv.tponr.client.armor.HazmatSuit;

import net.minecraft.resources.ResourceLocation;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.armor.HazmatSuitItem;
import software.bernie.geckolib.model.GeoModel;

public class HazmatSuitModel extends GeoModel<HazmatSuitItem> {

    @Override
    public ResourceLocation getModelResource(HazmatSuitItem animatable) {
        return TPONR.id("geo/hazmat_suit.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HazmatSuitItem animatable) {
        return TPONR.id("textures/models/armor/hazmat_suit.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HazmatSuitItem animatable) {
        return TPONR.id("animations/empty.animation.json");
    }
}
