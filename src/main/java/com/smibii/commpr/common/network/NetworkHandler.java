package com.smibii.commpr.common.network;

import com.smibii.commpr.COMMPR;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            COMMPR.asResource("main"),
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    private static int packetId = 0;

    public static void register() {
        CHANNEL.messageBuilder(SyncPlayerPacket.class, packetId++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(SyncPlayerPacket::encode)
                .decoder(SyncPlayerPacket::decode)
                .consumerMainThread(SyncPlayerPacket::handle)
                .add();

        CHANNEL.messageBuilder(SyncGameSettingsPacket.class, packetId++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(SyncGameSettingsPacket::encode)
                .decoder(SyncGameSettingsPacket::decode)
                .consumerMainThread(SyncGameSettingsPacket::handle)
                .add();
    }
}
