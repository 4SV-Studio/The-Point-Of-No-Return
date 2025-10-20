package org.studio4sv.tponr.client.entity.SuitStandBlockItem;

import net.minecraft.resources.ResourceLocation;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.blocks.entity.SuitStandEntity;
import software.bernie.geckolib.model.GeoModel;

public class SuitStandBlockModel extends GeoModel<SuitStandEntity> {
    @Override
    public ResourceLocation getModelResource(SuitStandEntity bunkerDoorBlockItem) {
        return TPONR.id("geo/suit_stand.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SuitStandEntity bunkerDoorBlockItem) {
        return TPONR.id("textures/block/suit_stand.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SuitStandEntity bunkerDoorBlockItem) {
        return TPONR.id("animations/empty.animation.json");
    }
}
