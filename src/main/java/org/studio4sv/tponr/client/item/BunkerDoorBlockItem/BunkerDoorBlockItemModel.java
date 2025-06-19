package org.studio4sv.tponr.client.item.BunkerDoorBlockItem;

import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.items.BunkerDoorBlockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BunkerDoorBlockItemModel extends GeoModel<BunkerDoorBlockItem> {
    @Override
    public ResourceLocation getModelResource(BunkerDoorBlockItem bunkerDoorBlockItem) {
        return ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "geo/bunker_door.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BunkerDoorBlockItem bunkerDoorBlockItem) {
        return ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "textures/block/bunker_door.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BunkerDoorBlockItem bunkerDoorBlockItem) {
        return ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "animations/bunker_door.animation.json");
    }
}
