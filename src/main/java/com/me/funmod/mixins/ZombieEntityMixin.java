package com.me.funmod.mixins;

import com.me.funmod.diamondzombie.DiamondZombie;
import com.me.funmod.FunMod;
import com.me.funmod.rockzombie.RockZombie;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
            if(((ZombieEntity)(Object)this) instanceof RockZombie || ((ZombieEntity)(Object)this) instanceof DiamondZombie) {
                return;
            }
            if(this.lastRenderY < 40.0f &&  this.isBaby() != true){
                convertTo(FunMod.ROCKZOMBIE);
            }
        }
    }
    @Inject(at = @At("HEAD"), method = "createZombieAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;", cancellable = true)
    private static void createZombieAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> info){
        info.setReturnValue( HostileEntity.createHostileAttributes().add(
                EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0D).add(
                        EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23000000417232513D).add(
                                EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D).add(
                                        EntityAttributes.GENERIC_ARMOR, 2.0D).add(
                                                EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS));



    }

}




