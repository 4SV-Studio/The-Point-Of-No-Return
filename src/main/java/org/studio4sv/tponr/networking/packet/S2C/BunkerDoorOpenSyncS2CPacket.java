package org.studio4sv.tponr.networking.packet.S2C;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import org.studio4sv.tponr.blocks.entity.BunkerDoor.BunkerDoorBlockEntity;
import org.studio4sv.tponr.client.ClientAttributesData;

import java.util.function.Supplier;

public class BunkerDoorOpenSyncS2CPacket {
    private final BlockPos pos;
    private final boolean isOpen;

    public BunkerDoorOpenSyncS2CPacket(BlockPos pos, boolean isOpen) {
        this.pos = pos;
        this.isOpen = isOpen;
    }

    public BunkerDoorOpenSyncS2CPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.isOpen = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeBoolean(isOpen);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            Minecraft.getInstance().execute(() -> {
                Level level = Minecraft.getInstance().level;
                if (level != null && level.getBlockEntity(pos) instanceof BunkerDoorBlockEntity blockEntity) {
                    blockEntity.setOpen(isOpen);
                }
            });
        });
        return true;
    }
}
