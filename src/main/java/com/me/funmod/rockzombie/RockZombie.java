package com.me.funmod.rockzombie;

import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;

public class RockZombie extends ZombieEntity {
    public RockZombie(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
        {
    }
}



    public static DefaultAttributeContainer.Builder createZombieAttributes() {
        return ZombieEntity.createZombieAttributes().add(
                EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0D).add (

                EntityAttributes.GENERIC_ATTACK_DAMAGE, 15.0D).add(
                EntityAttributes.GENERIC_ARMOR, 2.0D).add(
                EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS);
    }
}