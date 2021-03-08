package com.me.funmod.AI;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class TeleportFollow<T extends LivingEntity> extends FollowTargetGoal {

    public TeleportFollow(MobEntity mob, Class<T> targetClass, boolean checkVisibility) {
        super(mob,targetClass,checkVisibility);

    }


    public void TeleportTime(){
        PlayerEntity NeerestPlayer = this.mob.world.getClosestPlayer(this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        //this.teleport(NeerestPlayer.getBlockPos());

    }


}
