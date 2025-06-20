package org.studio4sv.tponr.client.entity.BunkerDoorBlockItem;

import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.blocks.entity.BunkerDoorBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BunkerDoorBlockModel extends GeoModel<BunkerDoorBlockEntity> {
    @Override
    public ResourceLocation getModelResource(BunkerDoorBlockEntity bunkerDoorBlockItem) {
        return ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "geo/bunker_door.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BunkerDoorBlockEntity bunkerDoorBlockItem) {
        return ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "textures/block/bunker_door.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BunkerDoorBlockEntity bunkerDoorBlockItem) {
        return ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "animations/bunker_door.animation.json");
    }
}
