package com.smibii.commpr.server.events;

import com.smibii.commpr.COMMPR;
import com.smibii.commpr.server.config.ServerConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.smibii.commpr.COMMPR.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigEvents {
    @SubscribeEvent
    public static void onConfigLoad(ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == ServerConfig.SPEC) {
            ServerConfig.isLoaded = true;
            COMMPR.LOGGER.info("ServerConfig loaded successfully.");
        }
    }
}
