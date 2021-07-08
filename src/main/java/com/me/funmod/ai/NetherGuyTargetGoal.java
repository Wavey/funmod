package com.me.funmod.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.mob.MobEntity;


public class NetherGuyTargetGoal<T extends LivingEntity> extends FollowTargetGoal {


    public NetherGuyTargetGoal(MobEntity mob, Class<T> targetClass, boolean checkVisibility, boolean checkCanNavigate) {
        super(mob, targetClass, checkVisibility, false);
    }
   // public NetherGuyTargetGoal(MobEntity mob, Class<T> targetClass, int reciprocalChance, boolean checkVisibility, boolean checkCanNavigate, @Nullable Predicate<LivingEntity> targetPredicate) {
       // super(mob, checkVisibility, checkCanNavigate);
      //  this.targetClass = targetClass;
       // this.reciprocalChance = reciprocalChance;
      //  this.setControls(EnumSet.of(Control.TARGET));
       // this.targetPredicate = (new TargetPredicate()).setBaseMaxDistance(this.getFollowRange()).setPredicate(targetPredicate);
   // }



}
