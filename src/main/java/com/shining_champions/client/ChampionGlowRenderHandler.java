package com.shining_champions.client;

import com.shining_champions.champion.ChampionGlowManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT)
public final class ChampionGlowRenderHandler {


    @SubscribeEvent
    public static void onLivingRenderPre(RenderLivingEvent.Pre<?, ?> event) {


        LivingEntity entity = event.getEntity();

        if (!entity.isCurrentlyGlowing()
                || entity.getTeam() != null
                || !ChampionGlowManager.hasChampionGlow(entity)) {
            return;
        }
        ChampionGlowManager.GlowColor glowColor = ChampionGlowManager.getGlowColor(event.getEntity());

        if (glowColor == null) {
            return;
        }

        int rgb = glowColor.getOutlineColor(event.getEntity().level().getGameTime());


        Minecraft.getInstance()
                .renderBuffers()
                .outlineBufferSource()
                .setColor(
                        (rgb >> 16) & 0xFF,
                        (rgb >> 8) & 0xFF,
                        rgb & 0xFF,
                        255
                );
    }
}
