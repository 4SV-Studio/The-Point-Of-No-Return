package org.studio4sv.tponr.client.entity.ClockBlockItem;

import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.blocks.entity.ClockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ClockBlockModel extends GeoModel<ClockEntity> {
    @Override
    public ResourceLocation getModelResource(ClockEntity Item) {
        return TPONR.id("geo/clock.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ClockEntity Item) {
        return TPONR.id("textures/block/clock.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ClockEntity Item) {
        return TPONR.id("animations/clock.animation.json");
    }
}
