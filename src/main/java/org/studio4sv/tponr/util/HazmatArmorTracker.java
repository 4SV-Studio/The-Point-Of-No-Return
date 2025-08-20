package org.studio4sv.tponr.util;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import org.studio4sv.tponr.armor.HazmatSuitItem;

public class HazmatArmorTracker {
    public static boolean headVisible = true;
    public static boolean bodyVisible = true;
    public static boolean legsVisible = true;

    public static void updateForPlayer(Player player) {
        if (!(player instanceof AbstractClientPlayer)) return;

        headVisible = !(player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof HazmatSuitItem);
        bodyVisible = !(player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof HazmatSuitItem);
        legsVisible = !(player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof HazmatSuitItem &&
                        player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof HazmatSuitItem);
    }
}
