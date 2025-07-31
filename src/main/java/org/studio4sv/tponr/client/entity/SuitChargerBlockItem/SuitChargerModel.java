package org.studio4sv.tponr.client.entity.SuitChargerBlockItem;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.blocks.entity.SuitCharger.SuitChargerBlockEntity;
import software.bernie.geckolib.model.GeoModel;

public class SuitChargerModel extends GeoModel<SuitChargerBlockEntity> {
    @Override
    public ResourceLocation getModelResource(SuitChargerBlockEntity animatable) {
        return TPONR.id("geo/charger.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SuitChargerBlockEntity animatable) {
        return TPONR.id("textures/block/charger.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SuitChargerBlockEntity animatable) {
        return TPONR.id("animations/empty.animation.json");
    }
}
