package org.studio4sv.tponr.client.entity.SuitDyerBlockItem;

import net.minecraft.resources.ResourceLocation;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.blocks.entity.SuitDyer.SuitDyerBlockEntity;
import software.bernie.geckolib.model.GeoModel;

public class SuitDyerBlockModel extends GeoModel<SuitDyerBlockEntity> {
    @Override
    public ResourceLocation getModelResource(SuitDyerBlockEntity bunkerDoorBlockItem) {
        return ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "geo/suit_dyer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SuitDyerBlockEntity bunkerDoorBlockItem) {
        return ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "textures/block/suit_dyer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SuitDyerBlockEntity bunkerDoorBlockItem) {
        return ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "animations/empty.animation.json");
    }
}
