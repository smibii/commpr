package com.smibii.commpr.common.game.settings;

import com.smibii.commpr.common.network.NetworkHandler;
import com.smibii.commpr.common.network.SyncGameSettingsPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;

public class GameSettingsUtil {
    public static void sync() {
        Map<String, String> data = new HashMap<>();

        for (GameSettings gs : GameSettings.values()) {
            Setting<?> setting = gs.getSetting();
            Object value = setting.getValue();
            data.put(setting.getName(), value.toString());
        }

        SyncGameSettingsPacket packet = new SyncGameSettingsPacket(data);
        NetworkHandler.CHANNEL.send(PacketDistributor.ALL.noArg(), packet);
    }

    public static void syncPlayer(ServerPlayer serverPlayer) {
        Map<String, String> data = new HashMap<>();

        for (GameSettings gs : GameSettings.values()) {
            Setting<?> setting = gs.getSetting();
            Object value = setting.getValue();
            data.put(setting.getName(), value.toString());
        }

        SyncGameSettingsPacket packet = new SyncGameSettingsPacket(data);
        NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), packet);
    }
}
