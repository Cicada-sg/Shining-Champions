package com.shining_champions.mixin;

import com.shining_champions.champion.ChampionGlowManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class ClientMinecraftMixin {

    @Inject(method = "shouldEntityAppearGlowing", at = @At("HEAD"), cancellable = true)
    private void shiningChampions$showChampionOutline(
            Entity entity,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (entity instanceof LivingEntity livingEntity
                && ChampionGlowManager.hasChampionGlow(livingEntity)) {
            cir.setReturnValue(true);
        }
    }
}
