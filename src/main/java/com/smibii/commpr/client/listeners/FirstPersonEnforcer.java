package com.smibii.commpr.client.listeners;

import com.smibii.commpr.COMMPR;
import com.smibii.commpr.common.enums.gameplay.PlayerActivity;
import com.smibii.commpr.common.player.ComPlayer;
import com.smibii.commpr.common.player.ComPlayerUtil;
import com.smibii.commpr.server.events.PlayerActivityChangeEvent;
import net.minecraft.client.CameraType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.EnumSet;

import static org.lwjgl.glfw.GLFW.*;

@Mod.EventBusSubscriber(modid = COMMPR.MODID, value = Dist.CLIENT)
public class FirstPersonEnforcer {
    private static final EnumSet<PlayerActivity> ENFORCED_ACTIVITIES = EnumSet.of(
            PlayerActivity.INGAME,
            PlayerActivity.SPECTATOR,
            PlayerActivity.ELIMINATED,
            PlayerActivity.FREECAM
    );

    @SubscribeEvent
    public static void onPlayerActivityChange(PlayerActivityChangeEvent event) {
        Minecraft mc = Minecraft.getInstance();
        PlayerActivity activity = event.activity;

        if (ENFORCED_ACTIVITIES.contains(activity)) {
            if (mc.options.getCameraType() != CameraType.FIRST_PERSON) {
                mc.options.setCameraType(CameraType.FIRST_PERSON);
                mc.options.save();
            }
        }
    }

    @SubscribeEvent
    public static void onKeyPress(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        ComPlayer comPlayer = ComPlayerUtil.get(player);
        PlayerActivity activity = comPlayer.getActivity();

        if (ENFORCED_ACTIVITIES.contains(activity)) {
            KeyMapping perspectiveKey = mc.options.keyTogglePerspective;
            if (event.getKey() == perspectiveKey.getKey().getValue() && event.getAction() == GLFW_PRESS) {
                perspectiveKey.setDown(false);
                event.setCanceled(true);
            }
        }
    }
}