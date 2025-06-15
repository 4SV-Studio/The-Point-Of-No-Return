package org.studio4sv.tponr.client.entity.ClockItem;

import org.studio4sv.tponr.blocks.entity.ClockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ClockRenderer extends GeoBlockRenderer<ClockEntity> {
    public ClockRenderer(BlockEntityRendererProvider.Context context) {
        super(new ClockModel());
    }
}