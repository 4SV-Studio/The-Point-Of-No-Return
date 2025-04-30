package dev.lemonnik.ponr.client.item.ClockItem;

import dev.lemonnik.ponr.PONR;
import dev.lemonnik.ponr.items.ClockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ClockItemModel extends GeoModel<ClockItem> {
    @Override
    public ResourceLocation getModelResource(ClockItem Item) {
        return new ResourceLocation(PONR.MOD_ID, "geo/clock.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ClockItem Item) {
        return new ResourceLocation(PONR.MOD_ID, "textures/block/clock.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ClockItem Item) {
        return new ResourceLocation(PONR.MOD_ID, "animations/clock.animation.json");
    }
}
