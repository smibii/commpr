package com.smibii.commpr.network;

import com.smibii.commpr.COMMPR;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            COMMPR.asResource("main"),
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    private static int packetId = 0;

    public static void register() {
        CHANNEL.registerMessage(packetId++, SyncPlayerPacket.class,
                SyncPlayerPacket::encode,
                SyncPlayerPacket::decode,
                SyncPlayerPacket::handle);
    }
}
