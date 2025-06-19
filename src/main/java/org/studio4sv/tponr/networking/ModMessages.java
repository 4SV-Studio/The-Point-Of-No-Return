package org.studio4sv.tponr.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.networking.packet.S2C.AttributesDataSyncS2CPacket;
import org.studio4sv.tponr.networking.packet.S2C.StaminaDataSyncS2CPacket;
import org.studio4sv.tponr.networking.packet.S2C.ToggleWidgetS2CPacket;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(ResourceLocation.fromNamespaceAndPath(TPONR.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

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
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}