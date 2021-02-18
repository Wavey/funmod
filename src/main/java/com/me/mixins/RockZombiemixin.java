package com.me.mixins;

import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.biome.SpawnSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
abstract class RockZombiemixin {

    @Redirect(method = "tick",
             at = @At(value = "INVOKE", target="Lnet/minecraft/entity/mob/ZombieEntity;tick()V"))
    private void myTick(ZombieEntity entity) {
        System.out.println("in ontick()");
        //entity.tick();
    }

}
