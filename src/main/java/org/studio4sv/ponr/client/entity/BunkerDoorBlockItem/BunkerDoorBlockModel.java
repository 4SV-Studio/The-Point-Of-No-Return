package org.studio4sv.ponr.client.entity.BunkerDoorBlockItem;

import org.studio4sv.ponr.PONR;
import org.studio4sv.ponr.blocks.entity.BunkerDoorBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BunkerDoorBlockModel extends GeoModel<BunkerDoorBlockEntity> {
    @Override
    public ResourceLocation getModelResource(BunkerDoorBlockEntity bunkerDoorBlockItem) {
        return new ResourceLocation(PONR.MOD_ID, "geo/bunker_door.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BunkerDoorBlockEntity bunkerDoorBlockItem) {
        return new ResourceLocation(PONR.MOD_ID, "textures/block/bunker_door.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BunkerDoorBlockEntity bunkerDoorBlockItem) {
        return new ResourceLocation(PONR.MOD_ID, "animations/bunker_door.animation.json");
    }
}
