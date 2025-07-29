package org.studio4sv.tponr.client.item.FilterBlockItem;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.studio4sv.tponr.items.FilterBlockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class FilterBlockItemRenderer extends GeoItemRenderer<FilterBlockItem> {
    public FilterBlockItemRenderer() {
        super(new FilterBlockItemModel());
    }

    @Override
    public RenderType getRenderType(FilterBlockItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}