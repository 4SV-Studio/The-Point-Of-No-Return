package org.studio4sv.tponr.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.studio4sv.tponr.client.armor.hazmat_suit.HazmatSuitRenderer;
import org.studio4sv.tponr.client.item.HazmatSuitItemRenderer;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class HazmatSuitItem extends DyeableArmorItem implements GeoItem, DyeableLeatherItem {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public HazmatSuitItem(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private HazmatSuitRenderer renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
                                                                   EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new HazmatSuitRenderer();

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new HazmatSuitItemRenderer();
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    public int getColor(ItemStack stack) {
        return 0xFF0000;
    }
}
