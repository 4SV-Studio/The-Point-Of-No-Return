package org.studio4sv.tponr.client.item.BunkerDoorBlockItem;

import org.studio4sv.tponr.items.BunkerDoorBlockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class BunkerDoorBlockItemRenderer extends GeoItemRenderer<BunkerDoorBlockItem> {
    public BunkerDoorBlockItemRenderer() {
        super(new BunkerDoorBlockItemModel());
    }
}
