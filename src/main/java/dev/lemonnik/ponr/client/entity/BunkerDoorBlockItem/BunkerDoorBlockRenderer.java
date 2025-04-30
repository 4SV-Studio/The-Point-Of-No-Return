package dev.lemonnik.ponr.client.entity.BunkerDoorBlockItem;

import dev.lemonnik.ponr.blocks.entity.BunkerDoorBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class BunkerDoorBlockRenderer extends GeoBlockRenderer<BunkerDoorBlockEntity> {
    public BunkerDoorBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new BunkerDoorBlockModel());
    }
}