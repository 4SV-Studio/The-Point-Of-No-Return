package org.studio4sv.tponr.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.networking.packet.*;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(TPONR.id("messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        // Server 2 Client
        net.messageBuilder(StaminaDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(StaminaDataSyncS2CPacket::new)
                .encoder(StaminaDataSyncS2CPacket::toBytes)
                .consumerMainThread(StaminaDataSyncS2CPacket::handle)
                .add();

        net.messageBuilder(ToggleWidgetS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ToggleWidgetS2CPacket::new)
                .encoder(ToggleWidgetS2CPacket::toBytes)
                .consumerMainThread(ToggleWidgetS2CPacket::handle)
                .add();

        net.messageBuilder(AttributesDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(AttributesDataSyncS2CPacket::new)
                .encoder(AttributesDataSyncS2CPacket::toBytes)
                .consumerMainThread(AttributesDataSyncS2CPacket::handle)
                .add();

        net.messageBuilder(BunkerDoorOpenSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(BunkerDoorOpenSyncS2CPacket::new)
                .encoder(BunkerDoorOpenSyncS2CPacket::toBytes)
                .consumerMainThread(BunkerDoorOpenSyncS2CPacket::handle)
                .add();

        net.messageBuilder(SuitDyerDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SuitDyerDataSyncS2CPacket::new)
                .encoder(SuitDyerDataSyncS2CPacket::toBytes)
                .consumerMainThread(SuitDyerDataSyncS2CPacket::handle)
                .add();

        net.messageBuilder(SuitChargerDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SuitChargerDataSyncS2CPacket::new)
                .encoder(SuitChargerDataSyncS2CPacket::toBytes)
                .consumerMainThread(SuitChargerDataSyncS2CPacket::handle)
                .add();

        net.messageBuilder(SyncFilterStatusS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncFilterStatusS2CPacket::new)
                .encoder(SyncFilterStatusS2CPacket::toBytes)
                .consumerMainThread(SyncFilterStatusS2CPacket::handle)
                .add();

        // Client 2 Server
        net.messageBuilder(DyerChangeColorS2CPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(DyerChangeColorS2CPacket::new)
                .encoder(DyerChangeColorS2CPacket::toBytes)
                .consumerMainThread(DyerChangeColorS2CPacket::handle)
                .add();

        net.messageBuilder(UpgradeStatsC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UpgradeStatsC2SPacket::new)
                .encoder(UpgradeStatsC2SPacket::toBytes)
                .consumerMainThread(UpgradeStatsC2SPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}