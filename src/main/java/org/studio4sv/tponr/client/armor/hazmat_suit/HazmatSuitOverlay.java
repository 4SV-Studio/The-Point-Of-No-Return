package org.studio4sv.tponr.client.armor.hazmat_suit;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.armor.HazmatSuitItem;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class HazmatSuitOverlay extends GeoRenderLayer<HazmatSuitItem> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "textures/models/armor/hazmat_suit_overlay.png");

    public HazmatSuitOverlay(GeoRenderer<HazmatSuitItem> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, HazmatSuitItem animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        RenderType armorRenderType = RenderType.armorCutoutNoCull(TEXTURE);
        getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, armorRenderType,
                bufferSource.getBuffer(armorRenderType), partialTick, packedLight, packedOverlay, 255, 255, 255, 255);
    }
}
