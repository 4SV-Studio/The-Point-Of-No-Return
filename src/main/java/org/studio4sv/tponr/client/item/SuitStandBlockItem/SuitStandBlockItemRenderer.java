package org.studio4sv.tponr.client.item.SuitStandBlockItem;

import org.studio4sv.tponr.items.SuitStandBlockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SuitStandBlockItemRenderer extends GeoItemRenderer<SuitStandBlockItem> {
    public SuitStandBlockItemRenderer() {
        super(new SuitStandBlockItemModel());
    }
}
