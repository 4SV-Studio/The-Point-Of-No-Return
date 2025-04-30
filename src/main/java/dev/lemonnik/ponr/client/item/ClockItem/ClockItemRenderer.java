package dev.lemonnik.ponr.client.item.ClockItem;

import dev.lemonnik.ponr.items.ClockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ClockItemRenderer extends GeoItemRenderer<ClockItem> {
    public ClockItemRenderer() {
        super(new ClockItemModel());
    }
}