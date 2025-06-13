package org.studio4sv.ponr.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.studio4sv.ponr.client.ClientStaminaData;
import org.studio4sv.ponr.client.hud.StaminaHud;

import java.util.function.Supplier;

public class StaminaDataSyncS2CPacket {
    private final int stamina;
    private int maxStamina;


    public StaminaDataSyncS2CPacket(int stamina, int maxStamina) {
        this.stamina = stamina;
        this.maxStamina = maxStamina;
    }

    public StaminaDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.stamina = buf.readInt();
        this.maxStamina = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(stamina);
        buf.writeInt(maxStamina);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            StaminaHud.setStaminaAmount(stamina);
            ClientStaminaData.set(stamina);
            ClientStaminaData.setMax(maxStamina);
            System.out.println("Stamina Data Synced: " + stamina + " / " + maxStamina);
        });
        return true;
    }
}