package com.me.funmod.NetherGuy;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.world.World;

public class NetherGuy extends WitherSkeletonEntity {
    public NetherGuy(EntityType<? extends WitherSkeletonEntity> entityType, World world) {
        super(entityType, world);
    }
    public static DefaultAttributeContainer.Builder createHostileAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE,75).add(
                EntityAttributes.GENERIC_MOVEMENT_SPEED, .3);


    }
}
