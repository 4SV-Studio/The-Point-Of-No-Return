package org.studio4sv.tponr.networking.packet.C2S;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.studio4sv.tponr.blocks.entity.SuitDyer.SuitDyerBlockEntity;
import org.studio4sv.tponr.client.ClientAttributesData;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DyerChangeColorS2CPacket {
    private final BlockPos pos;
    private final int color;

    public DyerChangeColorS2CPacket(int color, BlockPos pos) {
        this.color = color;
        this.pos = pos;
    }

    public DyerChangeColorS2CPacket(FriendlyByteBuf buf) {
        this.color = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(color);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            var player = context.getSender();
            if (player == null) return;

            var level = player.level();

            var blockEntity = level.getBlockEntity(pos);
            if (blockEntity != null) {
                if (blockEntity instanceof SuitDyerBlockEntity dyer) {
                    dyer.setColor(color);
                    level.sendBlockUpdated(pos, dyer.getBlockState(), dyer.getBlockState(), 3);
                }
            }
        });
        return true;
    }

}