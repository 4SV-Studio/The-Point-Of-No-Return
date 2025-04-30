package dev.lemonnik.ponr.client.item.BunkerDoorBlockItem;

import dev.lemonnik.ponr.PONR;
import dev.lemonnik.ponr.items.BunkerDoorBlockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BunkerDoorBlockItemModel extends GeoModel<BunkerDoorBlockItem> {
    @Override
    public ResourceLocation getModelResource(BunkerDoorBlockItem bunkerDoorBlockItem) {
        return new ResourceLocation(PONR.MOD_ID, "geo/bunker_door.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BunkerDoorBlockItem bunkerDoorBlockItem) {
        return new ResourceLocation(PONR.MOD_ID, "textures/block/bunker_door.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BunkerDoorBlockItem bunkerDoorBlockItem) {
        return new ResourceLocation(PONR.MOD_ID, "animations/bunker_door.animation.json");
    }
}
