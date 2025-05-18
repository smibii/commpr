package com.smibii.commpr.common.enums.ui;

import net.minecraft.ChatFormatting;

public enum InfoItemTypes {
    WARNING(ChatFormatting.GOLD),
    INFO(ChatFormatting.AQUA),
    ERROR(ChatFormatting.RED),
    SUCCESS(ChatFormatting.GREEN),
    KILL(ChatFormatting.WHITE);

    private final int color;

    InfoItemTypes(ChatFormatting formatting) {
        this.color = formatting.getColor() != null ? formatting.getColor() : 0xFFFFFF;
    }

    public int getColor() {
        return color;
    }
}
