package org.studio4sv.tponr.client.item.FakeSuitItem;

import net.minecraft.resources.ResourceLocation;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.items.FakeSuitItem;
import software.bernie.geckolib.model.GeoModel;

public class FakeSuitItemModel extends GeoModel<FakeSuitItem> {
    @Override
    public ResourceLocation getModelResource(FakeSuitItem animatable) {
        return TPONR.id("geo/fake_suit.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FakeSuitItem animatable) {
        return TPONR.id("textures/models/armor/hazmat_suit.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FakeSuitItem animatable) {
        return TPONR.id("animations/empty.animation.json");
    }
}
