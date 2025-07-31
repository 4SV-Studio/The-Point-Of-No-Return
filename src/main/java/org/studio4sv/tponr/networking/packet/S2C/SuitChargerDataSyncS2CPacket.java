package org.studio4sv.tponr.networking.packet.S2C;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import org.studio4sv.tponr.blocks.entity.SuitCharger.SuitChargerBlockEntity;

import java.util.function.Supplier;

public class SuitChargerDataSyncS2CPacket {
    private final BlockPos pos;
    private final ItemStack storedItem;

    public SuitChargerDataSyncS2CPacket(BlockPos pos, ItemStack storedItem) {
        this.pos = pos;
        this.storedItem = storedItem;
    }

    public SuitChargerDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.storedItem = buf.readItem();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeItem(storedItem);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            Minecraft.getInstance().execute(() -> {
                Level level = Minecraft.getInstance().level;
                if (level != null && level.getBlockEntity(pos) instanceof SuitChargerBlockEntity blockEntity) {
                    blockEntity.setStoredItem(storedItem);
                }
            });
        });
        return true;
    }
}
