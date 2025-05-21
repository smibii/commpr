package com.smibii.commpr.client.listeners;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber({Dist.CLIENT})
public class InspectOverrideEvent {
    @SubscribeEvent
    public static void onInspectPress(InputEvent.Key event) {
    }
}
