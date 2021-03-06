package com.me.funmod.mixins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.BowAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(AbstractSkeletonEntity.class)
abstract class SkeletonEntityMixin extends HostileEntity {
    SkeletonEntityMixin(EntityType<? extends SkeletonEntity> entity, World world) {
        super(entity, world);
        System.out.println("In constructor for SkeletonEntityMixin");
    }
    private final BowAttackGoal<AbstractSkeletonEntity> bowAttackGoal = new BowAttackGoal(this, 1.5D, 0, 15.0F);

    @Inject(at = @At("HEAD"), method = "createAbstractSkeletonAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;", cancellable = true)
    private static void createAbstractSkeletonAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> info){
      //  info.setReturnValue( HostileEntity.createHostileAttributes().add(
               // EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0D));





    }
}