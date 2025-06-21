package org.studio4sv.tponr.client.armor;

import org.studio4sv.tponr.items.HazmatSuits.BaseHazmatSuitItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class HazmatSuitRenderer extends GeoArmorRenderer<BaseHazmatSuitItem> {
    public HazmatSuitRenderer() {
        super(new HazmatSuitModel());
    }
}
