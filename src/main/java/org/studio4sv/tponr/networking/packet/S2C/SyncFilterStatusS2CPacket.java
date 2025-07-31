package org.studio4sv.tponr.networking.packet.S2C;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import org.studio4sv.tponr.blocks.entity.FilterBlockEntity;
import org.studio4sv.tponr.client.hud.HungerHud;
import org.studio4sv.tponr.client.hud.StaminaHud;
import org.studio4sv.tponr.client.hud.XpHud;

import java.util.function.Supplier;

public class SyncFilterStatusS2CPacket {
    private final BlockPos pos;
    private final boolean enabled;

    public SyncFilterStatusS2CPacket(BlockPos pos, boolean enabled) {
        this.pos = pos;
        this.enabled = enabled;
    }

    public SyncFilterStatusS2CPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.enabled = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeBoolean(enabled);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            if (Minecraft.getInstance().level != null) {
                BlockEntity blockEntity = Minecraft.getInstance().level.getBlockEntity(pos);
                if (blockEntity instanceof FilterBlockEntity filterBlockEntity) {
                    filterBlockEntity.setEnabled(enabled);
                    Minecraft.getInstance().level.sendBlockUpdated(pos, filterBlockEntity.getBlockState(), filterBlockEntity.getBlockState(), 3);
                }

            }

        });
        return true;
    }
}