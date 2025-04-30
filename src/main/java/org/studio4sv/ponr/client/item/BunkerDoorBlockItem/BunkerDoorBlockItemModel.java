package org.studio4sv.ponr.client.item.BunkerDoorBlockItem;

import org.studio4sv.ponr.PONR;
import org.studio4sv.ponr.items.BunkerDoorBlockItem;
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
