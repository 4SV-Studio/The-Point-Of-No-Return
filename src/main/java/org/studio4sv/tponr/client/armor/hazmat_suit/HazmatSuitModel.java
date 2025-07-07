package org.studio4sv.tponr.client.armor.hazmat_suit;

import net.minecraft.resources.ResourceLocation;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.armor.HazmatSuitItem;
import software.bernie.geckolib.model.GeoModel;

public class HazmatSuitModel extends GeoModel<HazmatSuitItem> {

    @Override
    public ResourceLocation getModelResource(HazmatSuitItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "geo/hazmat_suit.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HazmatSuitItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "textures/models/armor/hazmat_suit.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HazmatSuitItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "animations/empty.animation.json");
    }
}
