package org.studio4sv.tponr.items.HazmatSuits;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorMaterial;
import org.studio4sv.tponr.TPONR;

public class GreenSuitOneItem extends BaseHazmatSuitItem {

    public GreenSuitOneItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public ResourceLocation getModelResource() {
        return ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, BaseHazmatSuitItem.defaultModel);
    }

    @Override
    public ResourceLocation getTextureResource() {
        return ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "textures/armor/green_suit_1.png");
    }

    @Override
    public ResourceLocation getAnimationResource() {
        return ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, BaseHazmatSuitItem.defaultAnimation);
    }
}
