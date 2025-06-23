package org.studio4sv.tponr.networking.packet.S2C;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.studio4sv.tponr.client.ClientAttributesData;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class AttributesDataSyncS2CPacket {
    private final Map<String, Integer> attributes;
    
    public AttributesDataSyncS2CPacket(Map<String, Integer> attributes) {
        this.attributes = attributes;
    }

    public AttributesDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.attributes = new HashMap<>();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            String key = buf.readUtf();
            int value = buf.readInt();
            this.attributes.put(key, value);
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(attributes.size());
        for (Map.Entry<String, Integer> entry : attributes.entrySet()) {
            buf.writeUtf(entry.getKey());
            buf.writeInt(entry.getValue());
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            ClientAttributesData.set(attributes);
        });
        return true;
    }
}