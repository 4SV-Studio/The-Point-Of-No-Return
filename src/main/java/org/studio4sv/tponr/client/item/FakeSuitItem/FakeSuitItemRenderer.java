package org.studio4sv.tponr.client.item.FakeSuitItem;

import org.studio4sv.tponr.items.FakeSuitItem;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class FakeSuitItemRenderer extends GeoItemRenderer<FakeSuitItem> {
    public FakeSuitItemRenderer() {
        super(new FakeSuitItemModel());
    }

    @Override
    public Color getRenderColor(FakeSuitItem animatable, float partialTick, int packedLight) {
        return new Color(animatable.getColor(getCurrentItemStack()));
    }
}
