package dev.lemonnik.ponr.client.entity.ClockItem;

import dev.lemonnik.ponr.PONR;
import dev.lemonnik.ponr.blocks.entity.ClockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ClockModel extends GeoModel<ClockEntity> {
    @Override
    public ResourceLocation getModelResource(ClockEntity Item) {
        return new ResourceLocation(PONR.MOD_ID, "geo/clock.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ClockEntity Item) {
        return new ResourceLocation(PONR.MOD_ID, "textures/block/clock.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ClockEntity Item) {
        return new ResourceLocation(PONR.MOD_ID, "animations/clock.animation.json");
    }
}
