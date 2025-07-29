package org.studio4sv.tponr.client.entity.BunkerDoorBlockItem;

import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.blocks.entity.BunkerDoor.BunkerDoorBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BunkerDoorBlockModel extends GeoModel<BunkerDoorBlockEntity> {
    @Override
    public ResourceLocation getModelResource(BunkerDoorBlockEntity bunkerDoorBlockItem) {
        return TPONR.id("geo/bunker_door.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BunkerDoorBlockEntity bunkerDoorBlockItem) {
        return TPONR.id("textures/block/bunker_door.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BunkerDoorBlockEntity bunkerDoorBlockItem) {
        return TPONR.id("animations/bunker_door.animation.json");
    }
}
