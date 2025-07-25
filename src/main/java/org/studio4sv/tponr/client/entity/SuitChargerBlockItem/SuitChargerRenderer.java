package org.studio4sv.tponr.client.entity.SuitChargerBlockItem;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.studio4sv.tponr.blocks.entity.SuitCharger.SuitChargerBlockEntity;
import org.studio4sv.tponr.items.FakeSuitItem;
import org.studio4sv.tponr.items.HazmatSuitPackItem;
import org.studio4sv.tponr.registers.ModItems;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class SuitChargerRenderer extends GeoBlockRenderer<SuitChargerBlockEntity> {
    public SuitChargerRenderer(BlockEntityRendererProvider.Context context) {
        super(new SuitChargerModel());
    }

    @Override
    public void actuallyRender(PoseStack poseStack, SuitChargerBlockEntity animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

        ItemStack storedItem = animatable.getStoredItem();
        if (!storedItem.isEmpty()) {
            ItemStack displayStack = new ItemStack(ModItems.FAKE_SUIT.get());
            if (displayStack.getItem() instanceof FakeSuitItem dyeable) {
                if (!storedItem.isEmpty() && storedItem.getItem() instanceof HazmatSuitPackItem) {
                    dyeable.setColor(displayStack, ((HazmatSuitPackItem) storedItem.getItem()).getColor(storedItem));
                }
            }

            poseStack.pushPose();
            poseStack.translate(0.5F, 0.15F, 0.5F);
            poseStack.scale(1.0F, 1.0F, 1.0F);

            Minecraft.getInstance().getItemRenderer().renderStatic(
                    displayStack,
                    ItemDisplayContext.HEAD,
                    packedLight,
                    packedOverlay,
                    poseStack,
                    bufferSource,
                    animatable.getLevel(),
                    0
            );

            poseStack.popPose();
        }
    }
}
