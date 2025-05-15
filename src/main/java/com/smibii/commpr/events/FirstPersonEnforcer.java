package com.smibii.commpr.events;

import com.smibii.commpr.COMMPR;
import net.minecraft.client.CameraType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = COMMPR.MODID, value = Dist.CLIENT)
public class FirstPersonEnforcer {

    private static boolean wasPressed = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) return;

        KeyMapping camKey = mc.options.keyTogglePerspective;
        boolean isPressed = camKey.isDown();

        if (isPressed && !wasPressed) {
            mc.options.setCameraType(CameraType.FIRST_PERSON);
            mc.options.save();
        }

        wasPressed = isPressed;
    }
}