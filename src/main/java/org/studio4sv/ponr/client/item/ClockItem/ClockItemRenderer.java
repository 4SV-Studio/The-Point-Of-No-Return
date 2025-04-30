package org.studio4sv.ponr.client.item.ClockItem;

import org.studio4sv.ponr.items.ClockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ClockItemRenderer extends GeoItemRenderer<ClockItem> {
    public ClockItemRenderer() {
        super(new ClockItemModel());
    }
}