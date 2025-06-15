package org.studio4sv.tponr.client.item.ClockItem;

import org.studio4sv.tponr.items.ClockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ClockItemRenderer extends GeoItemRenderer<ClockItem> {
    public ClockItemRenderer() {
        super(new ClockItemModel());
    }
}