package com.me.mixins;

import com.me.funmod.FunMod;
import com.me.funmod.rockzombie.RockZombie;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.SpawnSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombieEntity.class)
public class RockZombiemixin {


    @Redirect(method = "tick",
             at = @At(value = "INVOKE", target="Lnet/minecraft/entity/mob/ZombieEntity;isConvertingInWater()Z"))
    private boolean onTick(ZombieEntity entity) {
        RockZombie.convertToRockZombie(entity);
        return entity.isConvertingInWater();
        //entity.tick();
    }


}
