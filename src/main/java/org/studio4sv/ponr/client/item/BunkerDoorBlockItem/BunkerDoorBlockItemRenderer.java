package org.studio4sv.ponr.client.item.BunkerDoorBlockItem;

import org.studio4sv.ponr.items.BunkerDoorBlockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class BunkerDoorBlockItemRenderer extends GeoItemRenderer<BunkerDoorBlockItem> {
    public BunkerDoorBlockItemRenderer() {
        super(new BunkerDoorBlockItemModel());
    }
}
