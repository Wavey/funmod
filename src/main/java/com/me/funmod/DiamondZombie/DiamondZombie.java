package com.me.funmod.DiamondZombie;

import com.me.funmod.AI.TeleportFollow;
import com.me.funmod.FunMod;
import com.me.funmod.rockzombie.RockZombie;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class DiamondZombie extends ZombieEntity {
    public DiamondZombie(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
        {
        }
    }

    @Override
    protected void initGoals() {

        this.goalSelector.add(2, new ZombieAttackGoal(this, 1.0D, false));
        this.goalSelector.add(2,new TeleportFollow(this,PlayerEntity.class,false));

    }
}
