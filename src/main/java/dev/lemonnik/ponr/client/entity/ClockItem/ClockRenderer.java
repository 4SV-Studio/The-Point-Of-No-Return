package dev.lemonnik.ponr.client.entity.ClockItem;

import dev.lemonnik.ponr.blocks.entity.ClockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ClockRenderer extends GeoBlockRenderer<ClockEntity> {
    public ClockRenderer(BlockEntityRendererProvider.Context context) {
        super(new ClockModel());
    }
}