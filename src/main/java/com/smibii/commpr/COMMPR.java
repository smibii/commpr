package com.smibii.commpr;

import com.mojang.logging.LogUtils;
import com.smibii.commpr.client.events.CapabilityEvents;
import com.smibii.commpr.client.events.HudRenderEvents;
import com.smibii.commpr.common.player.ComPlayer;
import com.smibii.commpr.server.config.ServerConfig;
import com.smibii.commpr.server.events.PlayerEvents;
import com.smibii.commpr.common.network.NetworkHandler;
import com.smibii.commpr.server.register.CommandRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(COMMPR.MODID)
public class COMMPR {
    public static final String MODID = "commpr";
    public static final Logger LOGGER = LogUtils.getLogger();
    public IEventBus eventBus;

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public COMMPR() {
        eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new PlayerEvents());
        MinecraftForge.EVENT_BUS.register(new CommandRegister());
        MinecraftForge.EVENT_BUS.register(new CapabilityEvents());
        MinecraftForge.EVENT_BUS.register(new HudRenderEvents());

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::register);
    }
}
