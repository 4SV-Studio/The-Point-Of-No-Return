package org.studio4sv.tponr.networking.packet.S2C;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import org.studio4sv.tponr.blocks.entity.BunkerDoor.BunkerDoorSubBlockEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BunkerDoorSubsSyncS2CPacket {
    private final List<BlockPos> subs;
    private final BlockPos main;

    public BunkerDoorSubsSyncS2CPacket(List<BlockPos> subs, BlockPos main) {
        this.subs = subs;
        this.main = main;
    }

    public BunkerDoorSubsSyncS2CPacket(FriendlyByteBuf buf) {
        int size = buf.readInt();
        this.subs = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            subs.add(buf.readBlockPos());
        }
        this.main = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(subs.size());
        for (BlockPos blockPos : subs) {
            buf.writeBlockPos(blockPos);
        }
        buf.writeBlockPos(main);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            Minecraft.getInstance().execute(() -> {
                for (BlockPos blockPos : subs) {
                    ClientLevel level = Minecraft.getInstance().level;
                    if (level != null) {
                        BlockEntity be = level.getBlockEntity(blockPos);
                        if (be instanceof BunkerDoorSubBlockEntity blockEntity) {
                            blockEntity.setMainBlockPos(main);
                        }
                    }
                }
            });
        });
        return true;
    }
}
