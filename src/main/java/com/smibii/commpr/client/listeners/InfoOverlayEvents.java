package com.smibii.commpr.client.listeners;

import com.mojang.blaze3d.platform.InputConstants;
import com.smibii.commpr.COMMPR;
import com.smibii.commpr.client.overlay.InfoOverlayRenderer;
import com.tacz.guns.client.input.AimKey;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = COMMPR.MODID, value = Dist.CLIENT)
public class InfoOverlayEvents {
    private static final HashSet<UUID> handledDeaths = new HashSet<>();

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        Entity target = event.getEntity();

        // -------------------- Makes it fire once!
        if (!handledDeaths.add(target.getUUID())) {
            handledDeaths.remove(target.getUUID());
            return;
        }

        DamageSource damageSource = event.getSource();
        Entity sourceEntity = damageSource.getEntity();

        if (sourceEntity == null) return;

        boolean midAir = !sourceEntity.onGround();
        boolean noScope = !AimKey.AIM_KEY.isDown();

        InfoOverlayRenderer.kill(
                target,
                sourceEntity,
                midAir,
                noScope,
                damageSource
        );
    }
}
