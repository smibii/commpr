package com.smibii.commpr.client.listeners;

import com.smibii.commpr.common.enums.gameplay.PlayerActivity;
import com.smibii.commpr.common.player.ComPlayer;
import com.smibii.commpr.common.player.ComPlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class HudRenderEvents {
    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Pre event) {
        String overlayId = event.getOverlay().id().getPath();
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.player.isDeadOrDying() || mc.level == null) return;

        ComPlayer comPlayer = ComPlayerUtil.get(mc.player);
        if (!comPlayer.getActivity().equals(PlayerActivity.INGAME)) {
            if (
                    overlayId.equals("player_health") ||
                    overlayId.equals("food_level") ||
                    overlayId.equals("air_level") ||
                    overlayId.equals("armor_level")
            ) event.setCanceled(true);
        };

        if (overlayId.equals("experience_bar")) event.setCanceled(true);
    }
}
