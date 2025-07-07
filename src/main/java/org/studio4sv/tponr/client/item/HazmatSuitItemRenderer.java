package org.studio4sv.tponr.client.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.studio4sv.tponr.armor.HazmatSuitItem;
import org.studio4sv.tponr.client.armor.hazmat_suit.HazmatSuitModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.core.object.DataTicket;

public class HazmatSuitItemRenderer extends DyeableGeoItemRenderer<HazmatSuitItem> {
    protected float partialTick;

    public HazmatSuitItemRenderer() {
        super(new HazmatSuitModel());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        this.currentItemStack = stack;
        this.animatable = (HazmatSuitItem) stack.getItem();
        this.renderPerspective = displayContext;
        this.partialTick = Minecraft.getInstance().getFrameTime();

        ArmorItem.Type armorType = ((ArmorItem)stack.getItem()).getType();

        long instanceId = getInstanceId(this.animatable);
        this.animatable.getAnimatableInstanceCache().getManagerForId(instanceId).setData(
                DataTickets.ITEM_RENDER_PERSPECTIVE, displayContext);
        this.animatable.getAnimatableInstanceCache().getManagerForId(instanceId).setData(
                new DataTicket<>("armorType", ArmorItem.Type.class), armorType);

        if (displayContext == ItemDisplayContext.GUI) {
            //GUI
            poseStack.pushPose();

            poseStack.scale(0.75F, 0.75F, 0.75F);

            applyGuiTransformations(poseStack, armorType);

            RenderType renderType = this.getRenderType(this.animatable, this.getTextureLocation(this.animatable), bufferSource, partialTick);
            VertexConsumer buffer = ItemRenderer.getFoilBufferDirect(bufferSource, renderType, false, stack.hasFoil());

            this.defaultRender(poseStack, this.animatable, bufferSource, renderType, buffer, 0.0F, partialTick, packedLight);

            poseStack.popPose();
        } else if (displayContext == ItemDisplayContext.GROUND) {
            //Ground, thanks Sherlock
            poseStack.pushPose();

            float scaleFactor = 0.35F;
            poseStack.scale(scaleFactor, scaleFactor, scaleFactor);

            applyGroundTransformations(poseStack, armorType);

            RenderType renderType = this.getRenderType(this.animatable, this.getTextureLocation(this.animatable), bufferSource, partialTick);
            VertexConsumer buffer = ItemRenderer.getFoilBufferDirect(bufferSource, renderType, false, stack.hasFoil());

            this.defaultRender(poseStack, this.animatable, bufferSource, renderType, buffer, 0.0F, partialTick, packedLight);

            poseStack.popPose();
        } else if (displayContext == ItemDisplayContext.FIXED) {
            // Item frames
            poseStack.pushPose();

            poseStack.scale(0.75F, 0.75F, 0.75F);

            applyFixedTransformations(poseStack, armorType);

            RenderType renderType = this.getRenderType(this.animatable, this.getTextureLocation(this.animatable), bufferSource, partialTick);
            VertexConsumer buffer = ItemRenderer.getFoilBufferDirect(bufferSource, renderType, false, stack.hasFoil());

            this.defaultRender(poseStack, this.animatable, bufferSource, renderType, buffer, 0.0F, partialTick, packedLight);

            poseStack.popPose();
        } else {
            // Default/other perspectives (FIRST_PERSON, THIRD_PERSON, etc.)
            poseStack.pushPose();

            float scaleFactor = 0.70F;
            poseStack.scale(scaleFactor, scaleFactor, scaleFactor);

            applyDefaultTransformations(poseStack, armorType, displayContext);

            RenderType renderType = this.getRenderType(this.animatable, this.getTextureLocation(this.animatable), bufferSource, partialTick);
            VertexConsumer buffer = ItemRenderer.getFoilBufferDirect(bufferSource, renderType, false, stack.hasFoil());

            this.defaultRender(poseStack, this.animatable, bufferSource, renderType, buffer, 0.0F, partialTick, packedLight);

            poseStack.popPose();
        }
    }

    private void applyGuiTransformations(PoseStack poseStack, ArmorItem.Type armorType) {
        switch (armorType) {
            case HELMET:
                poseStack.translate(0.7F, -1.5F, 0.0F);
                poseStack.mulPose(new Quaternionf().rotationXYZ(
                        (float)Math.toRadians(0),
                        (float)Math.toRadians(140),
                        (float)Math.toRadians(0)
                ));
                break;
            case CHESTPLATE:
                poseStack.translate(0.7F, -1F, 0.0F);
                poseStack.mulPose(new Quaternionf().rotationXYZ(
                        (float)Math.toRadians(0),
                        (float)Math.toRadians(140),
                        (float)Math.toRadians(0)
                ));
                break;
            case LEGGINGS:
                poseStack.translate(0.7F, -0.3F, 0.0F);
                poseStack.mulPose(new Quaternionf().rotationXYZ(
                        (float)Math.toRadians(0),
                        (float)Math.toRadians(140),
                        (float)Math.toRadians(0)
                ));
                break;
            case BOOTS:
                poseStack.translate(0.6F, 0.0F, 0.0F);
                poseStack.mulPose(new Quaternionf().rotationXYZ(
                        (float)Math.toRadians(0),
                        (float)Math.toRadians(140),
                        (float)Math.toRadians(0)
                ));
                break;
        }
    }

    private void applyGroundTransformations(PoseStack poseStack, ArmorItem.Type armorType) {
        switch (armorType) {
            case HELMET:
                poseStack.translate(1F, -0.7F, 1F);
                break;
            case CHESTPLATE:
                poseStack.translate(1F, -0.5F, 1F);
                break;
            case LEGGINGS:
                poseStack.translate(1F, 0.2F, 1F);
                break;
            case BOOTS:
                poseStack.translate(1F, 0.3F, 1F);
                break;
        }
    }

    private void applyFixedTransformations(PoseStack poseStack, ArmorItem.Type armorType) {
        switch (armorType) {
            case HELMET:
                poseStack.translate(0.15F, -1.5F, -0.1F);
                break;
            case CHESTPLATE:
                poseStack.translate(0.15F, -1F, 0.0F);
                break;
            case LEGGINGS:
                poseStack.translate(0.15F, -0.3F, 0.0F);
                break;
            case BOOTS:
                poseStack.translate(0.15F, 0.0F, 0.0F);
                break;
        }
    }

    private void applyDefaultTransformations(PoseStack poseStack, ArmorItem.Type armorType, ItemDisplayContext displayContext) {
        if (displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND ||
                displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND) {
            switch (armorType) {
                case HELMET:
                    poseStack.scale(0.6F, 0.6F, 0.6F);
                    poseStack.translate(1.65F, -1F, 1.5F);
                    poseStack.mulPose(new Quaternionf().rotationXYZ(
                            (float) Math.toRadians(0),
                            (float) Math.toRadians(180),
                            (float) Math.toRadians(0)
                    ));
                    break;
                case CHESTPLATE:
                    poseStack.scale(0.6F, 0.6F, 0.6F);
                    poseStack.translate(1.65F, -0.3F, 1.5F);
                    poseStack.mulPose(new Quaternionf().rotationXYZ(
                            (float) Math.toRadians(0),
                            (float) Math.toRadians(180),
                            (float) Math.toRadians(0)
                    ));
                    break;
                case LEGGINGS:
                    poseStack.scale(0.6F, 0.6F, 0.6F);
                    poseStack.translate(1.65F, 0.4F, 1.5F);
                    poseStack.mulPose(new Quaternionf().rotationXYZ(
                            (float) Math.toRadians(0),
                            (float) Math.toRadians(180),
                            (float) Math.toRadians(0)
                    ));
                    break;
                case BOOTS:
                    poseStack.scale(0.6F, 0.6F, 0.6F);
                    poseStack.translate(1.65F, 0.4F, 1.5F);
                    poseStack.mulPose(new Quaternionf().rotationXYZ(
                            (float) Math.toRadians(0),
                            (float) Math.toRadians(180),
                            (float) Math.toRadians(0)
                    ));
                    break;
            }
        } else if (displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND ||
                displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND) {
            switch (armorType) {
                case HELMET:
                    poseStack.translate(0.95F, -1.35F, 0.7F);
                    poseStack.mulPose(new Quaternionf().rotationXYZ(
                            (float) Math.toRadians(0),
                            (float) Math.toRadians(120),
                            (float) Math.toRadians(0)
                    ));
                    break;
                case CHESTPLATE:
                    poseStack.translate(1F, -0.8F, 0.7F);
                    poseStack.mulPose(new Quaternionf().rotationXYZ(
                            (float) Math.toRadians(0),
                            (float) Math.toRadians(120),
                            (float) Math.toRadians(0)
                    ));
                    break;
                case LEGGINGS:
                    poseStack.translate(1F, 0.05F, 1F);
                    poseStack.mulPose(new Quaternionf().rotationXYZ(
                            (float) Math.toRadians(0),
                            (float) Math.toRadians(125),
                            (float) Math.toRadians(0)
                    ));
                    break;
                case BOOTS:
                    poseStack.translate(1F, 0.4F, 1F);
                    poseStack.mulPose(new Quaternionf().rotationXYZ(
                            (float) Math.toRadians(0),
                            (float) Math.toRadians(120),
                            (float) Math.toRadians(0)
                    ));
                    break;
            }
        }
    }

    @Override
    protected boolean isBoneDyeable(GeoBone geoBone) {
        return true;
    }

    @Override
    protected @NotNull Color getColorForBone(GeoBone geoBone) {
        ItemStack itemStack = this.currentItemStack;

        if (itemStack.getItem() instanceof HazmatSuitItem armorItem) {
            int color = armorItem.getColor(itemStack);

            return applyDyeToBone(geoBone, color);
        }

        return Color.ofOpaque(0xFFFFFF);
    }

    public Color applyDyeToBone(GeoBone geoBone, int color) {
        return Color.ofOpaque(color);
    }

    @Override
    public RenderType getRenderType(HazmatSuitItem animatable, ResourceLocation texture,
                                    MultiBufferSource renderTypeBuffer, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }
}
