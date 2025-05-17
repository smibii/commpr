package com.smibii.commpr.events;

import com.smibii.commpr.COMMPR;
import com.smibii.commpr.overlay.InfoOverlayRenderer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
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
        String targetName = target.getName().getString();

        // -------------------- Makes it fire once!
        if (!handledDeaths.add(target.getUUID())) {
            handledDeaths.remove(target.getUUID());
            return;
        }

        DamageSource damageSource = event.getSource();
        Entity sourceEntity = damageSource.getEntity();
        boolean sourceExists = sourceEntity != null;

        String sourceName = damageSource.toString();
        if (sourceExists) sourceName = sourceEntity.getName().getString();

        InfoOverlayRenderer.kill(
                target,
                sourceEntity,
                damageSource
        );
    }
}
