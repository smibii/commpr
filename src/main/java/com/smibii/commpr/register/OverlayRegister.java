package com.smibii.commpr.register;

import com.smibii.commpr.COMMPR;
import com.smibii.commpr.overlay.InfoOverlayRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = COMMPR.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OverlayRegister {
    public static final String INFO_OVERLAY_ID = "info";

    @SubscribeEvent
    public static void onRegisterOverlays(RegisterGuiOverlaysEvent event) {
        event.registerBelowAll(INFO_OVERLAY_ID, (gui, guiGraphics, partialTick, width, height) -> {
            InfoOverlayRenderer.render(guiGraphics, partialTick, width, height);
        });
    }
}
