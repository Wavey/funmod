package com.me.mixins;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class PlayerEntityMixin {
    @Inject(at = @At("RETURN"), method = "heal(F)V")
    private void heal(CallbackInfo info) {
        System.out.println("** Heal called");

    }
}
