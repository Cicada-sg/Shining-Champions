package com.shining_champions.client;

import com.shining_champions.Shining_champions;
import com.shining_champions.champion.ChampionGlowManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = Shining_champions.MODID,
        bus = Mod.EventBusSubscriber.Bus.FORGE,
        value = net.minecraftforge.api.distmarker.Dist.CLIENT
)
public final class ChampionGlowRenderHandler {


    @SubscribeEvent
    public static void onLivingRenderPre(RenderLivingEvent.Pre<?, ?> event) {

        LivingEntity entity = event.getEntity();

        if (!entity.isCurrentlyGlowing()
                || entity.getTeam() != null
                || !ChampionGlowManager.hasChampionGlow(entity)) {
            return;
        }

        ChampionGlowManager.GlowColor color = ChampionGlowManager.getGlowColor(entity);
        if (color == null) {
            return;
        }

        int rgb = color.getOutlineColor(entity.level().getGameTime());

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
