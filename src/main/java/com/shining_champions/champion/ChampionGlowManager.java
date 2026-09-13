package com.shining_champions.champion;

import net.minecraft.util.FastColor;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.champions.api.champion.Champion;
import top.theillusivec4.champions.api.champion.ChampionTier;
import top.theillusivec4.champions.platform.ChampionAttachmentProvider;

import javax.annotation.Nullable;
import java.util.Optional;

public class ChampionGlowManager {

    public static boolean hasChampionGlow(LivingEntity entity) {
        return getGlowColor(entity) != null;
    }

    /**
     * 获取冠军的颜色
     */
    @Nullable
    public static GlowColor getGlowColor(LivingEntity entity) {
        if (entity == null) {
            return null;
        }
        Optional<Champion.Client> championOpt = ChampionAttachmentProvider.Holder.get().getClient(entity);

//        Optional<IChampion> championOpt = Champion.getCapability(entity).resolve();
        if (championOpt.isEmpty()) {
            return null;
        }

        Champion.Client champion = championOpt.get();
        ChampionTier rankOpt = champion.tier();

        int rankNum = rankOpt.level();

        try {
//            int color = Rank.getColor(colorCode);
            int color = champion.tier().display().color();
            return new GlowColor(color, rankNum);
        } catch (Exception e) {
            return null;
        }
    }

    public static class GlowColor {
        public final int color;
        public final int rank;
        public final float r;
        public final float g;
        public final float b;

        public GlowColor(int color, int rank) {
            this.color = color;
            this.rank = rank;

            // 将ARGB颜色分解为RGB分量
            this.r = (float) FastColor.ARGB32.red(color) / 255.0f;
            this.g = (float) FastColor.ARGB32.green(color) / 255.0f;
            this.b = (float) FastColor.ARGB32.blue(color) / 255.0f;
        }

        /**
         * 获取脉动强度
         */
        public float getPulsingIntensity(long gameTime) {
            float pulseSpeed = 1.0f + (rank * 0.1f);
            float timeFactor = gameTime * 0.1f * pulseSpeed;
            return 0.8f + 0.2f * (float)Math.sin(timeFactor);
        }

        /**
         * 获取完整的轮廓颜色（带脉动效果）
         */
        public int getOutlineColor(long gameTime) {
            float intensity = getPulsingIntensity(gameTime);
            float brightness = 0.7f + (rank * 0.03f);

            int red = (int)(r * 255 * brightness * intensity);
            int green = (int)(g * 255 * brightness * intensity);
            int blue = (int)(b * 255 * brightness * intensity);

            // 确保颜色值在有效范围内
            red = Math.min(Math.max(red, 0), 255);
            green = Math.min(Math.max(green, 0), 255);
            blue = Math.min(Math.max(blue, 0), 255);

            return (red << 16) | (green << 8) | blue;
        }
    }
}
