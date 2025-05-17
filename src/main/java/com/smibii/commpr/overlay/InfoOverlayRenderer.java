package com.smibii.commpr.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.smibii.commpr.enums.InfoItemTypes;
import com.smibii.commpr.enums.PlayerActivity;
import com.smibii.commpr.player.ComPlayer;
import com.smibii.commpr.player.ComPlayerUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.*;

public class InfoOverlayRenderer {
    private static final List<InfoItem> infoList = new ArrayList<>();
    private static final int MAX_ITEMS = 5;

    private static final int itemHeight = 12;
    private static final int xPadding = 10;
    private static final int yPadding = 10;

    private static void addInfo(
            InfoItemTypes type,
            String content,
            Entity targetEntity,
            @Nullable Entity sourceEntity,
            DamageSource damageSource
    ) {
        InfoItem item = new InfoItem(
                type,
                content,
                targetEntity,
                sourceEntity,
                damageSource
        );

        if (infoList.size() >= MAX_ITEMS) {
            infoList.remove(0);
        }

        infoList.add(item);
    }

    public static void success(
        String content,
        Entity targetEntity,
        Entity sourceEntity,
        DamageSource damageSource
    ) {
        addInfo(
                InfoItemTypes.SUCCESS,
                content,
                targetEntity,
                sourceEntity,
                damageSource
        );
    }

    public static void info(
        String content,
        Entity targetEntity,
        Entity sourceEntity,
        DamageSource damageSource
    ) {
        addInfo(
                InfoItemTypes.INFO,
                content,
                targetEntity,
                sourceEntity,
                damageSource
        );
    }

    public static void warn(
        String content,
        Entity targetEntity,
        Entity sourceEntity,
        DamageSource damageSource
    ) {
        addInfo(
                InfoItemTypes.WARNING,
                content,
                targetEntity,
                sourceEntity,
                damageSource
        );
    }

    public static void error(
        String content,
        Entity targetEntity,
        Entity sourceEntity,
        DamageSource damageSource
    ) {
        addInfo(
                InfoItemTypes.ERROR,
                content,
                targetEntity,
                sourceEntity,
                damageSource
        );
    }

    public static void kill(
        Entity targetEntity,
        Entity sourceEntity,
        DamageSource damageSource
    ) {
        String content = sourceEntity.getName().getString() + " ︻デ═一 " + targetEntity.getName().getString();
        addInfo(
                InfoItemTypes.KILL,
                content,
                targetEntity,
                sourceEntity,
                damageSource
        );
    }

    public static void render(GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        Font font = mc.font;

        assert player != null;
        ComPlayer comPlayer = ComPlayerUtil.get(player);
        PlayerActivity playerActivity = comPlayer.getActivity();

        if (!EnumSet.of(PlayerActivity.INGAME, PlayerActivity.ELIMINATED, PlayerActivity.SPECTATOR).contains(playerActivity)) return;

        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        Iterator<InfoItem> iter = infoList.iterator();
        int y = yPadding;

        while (iter.hasNext()) {
            InfoItem item = iter.next();
            if (item.isExpired()) {
                iter.remove();
                continue;
            }

            int color = item.type.getColor();
            if (item.type == InfoItemTypes.KILL) {
                if (item.sourceEntity != null && item.sourceEntity.getUUID() == Objects.requireNonNull(player).getUUID()) {
                    color = ChatFormatting.GREEN.getColor();
                }
            }

            float alpha = item.getAlpha();
            int alphaInt = (int) Math.max(10, alpha * 255);
            int RGBA = (alphaInt << 24) | color;

            String content = item.content;
            int x = screenWidth - font.width(content) - xPadding;

            font.drawInBatch(
                    content,
                    x,
                    y,
                    RGBA,
                    true,
                    poseStack.last().pose(),
                    guiGraphics.bufferSource(),
                    Font.DisplayMode.NORMAL,
                    0,
                    15728880
            );

            y += itemHeight;
        }

        guiGraphics.flush();
        RenderSystem.disableBlend();
        poseStack.popPose();
    }
}
