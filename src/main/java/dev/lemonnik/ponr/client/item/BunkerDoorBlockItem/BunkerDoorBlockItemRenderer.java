package dev.lemonnik.ponr.client.item.BunkerDoorBlockItem;

import dev.lemonnik.ponr.items.BunkerDoorBlockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class BunkerDoorBlockItemRenderer extends GeoItemRenderer<BunkerDoorBlockItem> {
    public BunkerDoorBlockItemRenderer() {
        super(new BunkerDoorBlockItemModel());
    }
}
