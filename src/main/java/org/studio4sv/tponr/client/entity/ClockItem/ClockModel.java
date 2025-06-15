package org.studio4sv.tponr.client.entity.ClockItem;

import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.blocks.entity.ClockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ClockModel extends GeoModel<ClockEntity> {
    @Override
    public ResourceLocation getModelResource(ClockEntity Item) {
        return new ResourceLocation(TPONR.MOD_ID, "geo/clock.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ClockEntity Item) {
        return new ResourceLocation(TPONR.MOD_ID, "textures/block/clock.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ClockEntity Item) {
        return new ResourceLocation(TPONR.MOD_ID, "animations/clock.animation.json");
    }
}
