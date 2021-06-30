package com.me.mixins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
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
    public int nethertimer;
    private void nethertimerticksubtract(){
        if (nethertimer > 0){
            nethertimer --;
        }
    }
    private void nethertimertickadd(){
        if (nethertimer < 1000){
            nethertimer = nethertimer + 4;
        }
    }
    @Inject(at = @At("HEAD"), method = "tick()V", cancellable = true)
    private void onTick( CallbackInfo info) {

        if(!this.world.isClient) {
            if (this.world.getRegistryKey() == World.NETHER) {
                nethertimerticksubtract();

                System.out.println(nethertimer);
            }else{
                nethertimertickadd();
            }
        }
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
