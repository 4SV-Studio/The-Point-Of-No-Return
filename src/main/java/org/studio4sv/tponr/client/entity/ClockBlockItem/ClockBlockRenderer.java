package org.studio4sv.tponr.client.entity.ClockBlockItem;

import org.studio4sv.tponr.blocks.entity.ClockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ClockBlockRenderer extends GeoBlockRenderer<ClockEntity> {
    public ClockBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new ClockBlockModel());
    }
}