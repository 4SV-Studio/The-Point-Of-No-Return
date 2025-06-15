package org.studio4sv.tponr.networking.packet.S2C;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.studio4sv.tponr.client.hud.HungerHud;
import org.studio4sv.tponr.client.hud.StaminaHud;
import org.studio4sv.tponr.client.hud.XpHud;

import java.util.function.Supplier;

public class ToggleWidgetS2CPacket {
    private final String widget;

    public ToggleWidgetS2CPacket(String widget) {
        this.widget = widget;
    }

    public ToggleWidgetS2CPacket(FriendlyByteBuf buf) {
        this.widget = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(widget);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            switch (widget) {
                case "all":
                    HungerHud.toggle();
                    StaminaHud.toggle();
                    XpHud.toggle();
                    break;
                case "hunger":
                    HungerHud.toggle();
                    break;
                case "stamina":
                    StaminaHud.toggle();
                    break;
                case "points":
                    XpHud.toggle();
                    break;
            }
        });
        return true;
    }
}