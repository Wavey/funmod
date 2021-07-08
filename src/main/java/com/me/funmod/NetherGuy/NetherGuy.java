package com.me.funmod.NetherGuy;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class NetherGuy extends WitherSkeletonEntity {
    public NetherGuy(EntityType<? extends NetherGuy> entityType, World world) {
        super(entityType, world);
    }
    public static DefaultAttributeContainer.Builder createNetherGuyAttributes() {
        return AbstractSkeletonEntity.createAbstractSkeletonAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE,75).add(
                EntityAttributes.GENERIC_MOVEMENT_SPEED, .3);


    }
    @Override
    protected void initGoals() {

        this.goalSelector.add(2,
                new FollowTargetGoal<PlayerEntity>(this, PlayerEntity.class,10,
                                        false,false, new PlayerPredicate()));

    }
    public class PlayerPredicate implements Predicate<LivingEntity> {
        public boolean test (LivingEntity t) {
            return true;
            //PlayerEntityMixin player = (PlayerEntityMixin)t;
            //if(player == null) {
            //    return false;
            //}
            //System.out.printf("Checking player entity with nether timer of %d \n", player.nethertimer);
            //if(player.nethertimer > 0) {
            //    System.out.println("Not adding");
            //    return false;
            //}
            //System.out.println("Adding");
            //return true;
        }
    }

}
