package org.studio4sv.tponr.client.entity.SuitDyerBlockItem;

import net.minecraft.resources.ResourceLocation;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.blocks.entity.SuitDyer.SuitDyerBlockEntity;
import software.bernie.geckolib.model.GeoModel;

public class SuitDyerBlockModel extends GeoModel<SuitDyerBlockEntity> {
    @Override
    public ResourceLocation getModelResource(SuitDyerBlockEntity bunkerDoorBlockItem) {
        return TPONR.id("geo/suit_dyer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SuitDyerBlockEntity bunkerDoorBlockItem) {
        return TPONR.id("textures/block/suit_dyer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SuitDyerBlockEntity bunkerDoorBlockItem) {
        return TPONR.id("animations/suit_dyer.animation.json");
    }
}
