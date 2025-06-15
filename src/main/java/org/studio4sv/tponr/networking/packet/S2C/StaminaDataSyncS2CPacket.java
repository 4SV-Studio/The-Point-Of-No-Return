package org.studio4sv.tponr.networking.packet.S2C;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.studio4sv.tponr.client.ClientStaminaData;
import org.studio4sv.tponr.client.hud.StaminaHud;

import java.util.function.Supplier;

public class StaminaDataSyncS2CPacket {
    private final int stamina;
    private final int maxStamina;


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
        });
        return true;
    }
}