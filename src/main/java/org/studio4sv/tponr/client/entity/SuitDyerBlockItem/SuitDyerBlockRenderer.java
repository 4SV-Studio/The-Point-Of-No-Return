package org.studio4sv.tponr.client.entity.SuitDyerBlockItem;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.studio4sv.tponr.blocks.entity.SuitDyer.SuitDyerBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class SuitDyerBlockRenderer extends GeoBlockRenderer<SuitDyerBlockEntity> {
    public SuitDyerBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new SuitDyerBlockModel());
    }
}