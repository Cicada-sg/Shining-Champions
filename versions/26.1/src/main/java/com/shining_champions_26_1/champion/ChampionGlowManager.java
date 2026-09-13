package com.shining_champions_26_1.champion;

import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.champions.api.championmob.ChampionMobProperty;
import top.theillusivec4.champions.api.championmob.ChampionMobPropertyHelper;

import javax.annotation.Nullable;


public final class ChampionGlowManager {
    private ChampionGlowManager() {
    }

    public static boolean hasChampionGlow(LivingEntity entity) {
        return getGlowColor(entity) != null;
    }

    @Nullable
    public static GlowColor getGlowColor(LivingEntity entity) {
        if (entity == null) {
            return null;
        }

        ChampionMobProperty property = ChampionMobPropertyHelper.get(entity);
        TextColor textColor = property.color();
        if (property == ChampionMobProperty.EMPTY || property.tier() <= 0 || textColor == null) {
            return null;
        }

        return new GlowColor(textColor.getValue(), property.tier());
    }

    public static final class GlowColor {
        public final int color;
        public final int rank;
        public final float r;
        public final float g;
        public final float b;

        public GlowColor(int color, int rank) {
            this.color = color;
            this.rank = rank;
            this.r = ((color >> 16) & 0xFF) / 255.0f;
            this.g = ((color >> 8) & 0xFF) / 255.0f;
            this.b = (color & 0xFF) / 255.0f;
        }

        public float getPulsingIntensity(long gameTime) {
            float pulseSpeed = 1.0f + (rank * 0.1f);
            float timeFactor = gameTime * 0.1f * pulseSpeed;
            return 0.8f + 0.2f * (float) Math.sin(timeFactor);
        }

        public int getOutlineColor(long gameTime) {
            float intensity = getPulsingIntensity(gameTime);
            float brightness = 0.7f + (rank * 0.03f);

            int red = clampColor(r * 255 * brightness * intensity);
            int green = clampColor(g * 255 * brightness * intensity);
            int blue = clampColor(b * 255 * brightness * intensity);
            return (red << 16) | (green << 8) | blue;
        }

        private static int clampColor(float value) {
            return Math.min(Math.max((int) value, 0), 255);
        }
    }
}
