package org.studio4sv.tponr.client.entity.BunkerDoorBlockItem;

import org.studio4sv.tponr.blocks.entity.BunkerDoor.BunkerDoorBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class BunkerDoorBlockRenderer extends GeoBlockRenderer<BunkerDoorBlockEntity> {
    public BunkerDoorBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new BunkerDoorBlockModel());
    }
}