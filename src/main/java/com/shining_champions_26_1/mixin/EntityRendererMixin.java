package com.shining_champions_26_1.mixin;

import com.shining_champions_26_1.champion.ChampionGlowManager;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("resource")
@Mixin(EntityRenderer.class)
public class EntityRendererMixin {
    @Inject(method = "extractRenderState", at = @At("RETURN"))
    private void shiningChampions$setChampionOutline(
            Entity entity,
            EntityRenderState renderState,
            float partialTick,
            CallbackInfo ci
    ) {
        if (entity instanceof LivingEntity livingEntity) {
            ChampionGlowManager.GlowColor glowColor = ChampionGlowManager.getGlowColor(livingEntity);
            if (glowColor != null) {
                renderState.outlineColor = ARGB.opaque(
                        glowColor.getOutlineColor(entity.level().getGameTime())
                );
            }
        }
    }
}
