package com.smibii.commpr.common.network;

import com.smibii.commpr.common.game.settings.GameSettings;
import com.smibii.commpr.common.game.settings.Setting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SyncGameSettingsPacket {
    private final Map<String, String> settings;

    public SyncGameSettingsPacket(Map<String, String> settings) {
        this.settings = settings;
    }

    public static void encode(SyncGameSettingsPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.settings.size());
        msg.settings.forEach((k, v) -> {
            buf.writeUtf(k);
            buf.writeUtf(v);
        });
    }

    public static SyncGameSettingsPacket decode(FriendlyByteBuf buf) {
        int size = buf.readInt();
        Map<String, String> settings = new HashMap<>();
        for (int i = 0; i < size ; i++) {
            settings.put(buf.readUtf(), buf.readUtf());
        }
        return new SyncGameSettingsPacket(settings);
    }

    public static void handle(SyncGameSettingsPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            for (Map.Entry<String, String> entry : msg.settings.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                for (GameSettings gs : GameSettings.values()) {
                    Setting<?> setting = gs.getSetting();
                    if (setting.getName().equals(key)) {
                        if (setting.getValue() instanceof Boolean) {
                            ((Setting<Boolean>) setting).setValue(Boolean.parseBoolean(value));
                        } else if (setting.getValue() instanceof Enum<?>) {
                            Class<?> enumType = setting.getValue().getClass();
                            Enum<?> enumValue = Enum.valueOf((Class<Enum>) enumType, value);
                            ((Setting<Enum<?>>) setting).setValue(enumValue);
                        }
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
