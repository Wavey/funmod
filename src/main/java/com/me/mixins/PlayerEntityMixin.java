package com.me.mixins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
abstract class PlayerEntityMixin extends LivingEntity {
    PlayerEntityMixin(EntityType<? extends LivingEntity> entity, World world) {
        super(entity, world);
    }

    @Inject(at = @At("RETURN"), method = "canFoodHeal()Z", cancellable = true)
    private void onCanFoodHeal(CallbackInfoReturnable<Boolean> info) {
        if(info.getReturnValue()) {
            float health = this.getHealth();
            boolean retval = health < 9.5f || health >= 10.5f;
            //System.out.println("Health = " + health + ", retval = " + retval);
            info.setReturnValue(retval);
        }
    }

}
