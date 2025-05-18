package com.smibii.commpr.client.overlay;

import com.smibii.commpr.common.enums.ui.InfoItemTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

public class InfoItem {
    public final InfoItemTypes type;
    public final String content;
    public final Entity targetEntity;
    public final @Nullable Entity sourceEntity;
    public final DamageSource damageSource;
    private final long timestamp;

    public static final int lifetime = 5000;
    private static final int fadeoutStart = 1000;

    public InfoItem(
        InfoItemTypes type,
        String content,
        Entity targetEntity,
        @Nullable Entity sourceEntity,
        DamageSource damageSource
    ) {
        this.type = type;
        this.content = content;

        this.targetEntity = targetEntity;
        this.sourceEntity = sourceEntity;
        this.damageSource = damageSource;

        this.timestamp = System.currentTimeMillis();
    }

    public float getAge() {
        return System.currentTimeMillis() - timestamp;
    }

    public float getAlpha() {
        float age = getAge();
        if (age >= lifetime) return 0f;
        if (age >= lifetime - fadeoutStart) {
            float remainingTime = lifetime - age;
            return remainingTime / fadeoutStart;
        }
        return 1f;
    }

    public boolean isExpired() {
        return getAge() >= lifetime;
    }
}
