package com.me.mixins;

import com.me.funmod.FunMod;
import com.me.funmod.rockzombie.RockZombie;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
abstract class ZombieEntityMixin extends HostileEntity{
    ZombieEntityMixin(EntityType<? extends ZombieEntity> entity, World world) {
        super(entity, world);
        System.out.println("In constructior for ZombieEntityMixin");
    }
    @Shadow
    protected void convertTo(EntityType<? extends ZombieEntity> entityType) {}


    @Inject(at = @At("HEAD"), method = "tick()V", cancellable = true)
    private void onTick( CallbackInfo info) {
        if (!this.world.isClient && this.isAlive() && !this.isAiDisabled()) {
            if(((ZombieEntity)(Object)this) instanceof RockZombie) {
                return;
            }
            if(this.lastRenderY < 40.0f){
                convertTo(FunMod.ROCKZOMBIE);
            }
        }
    }
}




