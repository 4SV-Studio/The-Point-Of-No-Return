package org.studio4sv.tponr.client.entity.FilterBlockItem;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.studio4sv.tponr.blocks.entity.FilterBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class FilterBlockRenderer extends GeoBlockRenderer<FilterBlockEntity> {
    public FilterBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new FilterBlockModel());
    }

    @Override
    public RenderType getRenderType(FilterBlockEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}