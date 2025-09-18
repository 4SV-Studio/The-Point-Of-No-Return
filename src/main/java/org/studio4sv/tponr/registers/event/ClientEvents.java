package org.studio4sv.tponr.registers.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.studio4sv.tponr.client.UpgradeScreenKeyHandler;
import org.studio4sv.tponr.client.gui.UpgradeScreen;
import org.studio4sv.tponr.armor.HazmatSuitItem;
import org.studio4sv.tponr.client.hud.SuitOverlayHud;
import org.studio4sv.tponr.client.hud.XpHud;
import org.studio4sv.tponr.util.armor.HazmatArmorTracker;
import org.studio4sv.tponr.util.xpConverter;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void disableHud(RenderGuiOverlayEvent.Pre event) {
        if (event.getOverlay() == VanillaGuiOverlay.FOOD_LEVEL.type())
            event.setCanceled(true);
        if (event.getOverlay() == VanillaGuiOverlay.EXPERIENCE_BAR.type())
            event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (player.level().isClientSide && player == Minecraft.getInstance().player) {
            HazmatArmorTracker.updateForPlayer(player);
        }

        int level = player.experienceLevel;
        float progress = player.experienceProgress;
        int totalXP = xpConverter.calculate(level, progress);
        XpHud.setPoints(totalXP);

        SuitOverlayHud.setEnabled(player.hasItemInSlot(EquipmentSlot.HEAD) && (player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof HazmatSuitItem));
        if (player.hasItemInSlot(EquipmentSlot.CHEST)) {
            SuitOverlayHud.setCharge(player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTag().getFloat("charge"));
        }
    }

    @SubscribeEvent
    public static void onRenderPlayer(RenderLivingEvent.Pre<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> event) {
        if (!(event.getEntity() instanceof AbstractClientPlayer)) return;
        PlayerModel<AbstractClientPlayer> model = event.getRenderer().getModel();

        if (!HazmatArmorTracker.headVisible) {
            model.head.visible = false;
            model.hat.visible = false;
        }
        if (!HazmatArmorTracker.bodyVisible) {
            model.body.visible = false;
            model.leftArm.visible = false;
            model.rightArm.visible = false;
            model.jacket.visible = false;
            model.leftSleeve.visible = false;
            model.rightSleeve.visible = false;
        }
        if (!HazmatArmorTracker.legsVisible) {
            model.leftLeg.visible = false;
            model.rightLeg.visible = false;
            model.leftPants.visible = false;
            model.rightPants.visible = false;
        }
    }

    @SubscribeEvent
    public static void onRenderPlayerPost(RenderLivingEvent.Post<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> event) {
        if (!(event.getEntity() instanceof AbstractClientPlayer)) return;
        PlayerModel<AbstractClientPlayer> model = event.getRenderer().getModel();

        model.head.visible = true;
        model.hat.visible = true;
        model.body.visible = true;
        model.leftArm.visible = true;
        model.rightArm.visible = true;
        model.jacket.visible = true;
        model.leftSleeve.visible = true;
        model.rightSleeve.visible = true;
        model.leftLeg.visible = true;
        model.rightLeg.visible = true;
        model.leftPants.visible = true;
        model.rightPants.visible = true;
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (UpgradeScreenKeyHandler.OPEN_UPGRADE_SCREEN.consumeClick()) {
            Minecraft.getInstance().setScreen(new UpgradeScreen());
        }
    }
}
