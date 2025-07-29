package org.studio4sv.tponr.client.item.FilterBlockItem;

import org.studio4sv.tponr.items.FilterBlockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class FilterBlockItemRenderer extends GeoItemRenderer<FilterBlockItem> {
    public FilterBlockItemRenderer() {
        super(new FilterBlockItemModel());
    }
}