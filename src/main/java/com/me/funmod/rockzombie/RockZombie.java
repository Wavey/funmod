package com.me.funmod.rockzombie;

import com.me.funmod.FunMod;
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

    public static void convertToRockZombie(ZombieEntity entity) {
        if(!(entity instanceof RockZombie)) {
            ZombieEntity zombieEntity = (ZombieEntity)entity.method_29243(FunMod.ROCKZOMBIE, true);
        }

    }



    public static DefaultAttributeContainer.Builder createZombieAttributes() {
        return ZombieEntity.createZombieAttributes().add(
                EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0D).add (

                EntityAttributes.GENERIC_ATTACK_DAMAGE, 15.0D).add(
                EntityAttributes.GENERIC_ARMOR, 2.0D).add(
                EntityAttributes.GENERIC_MAX_HEALTH, 40.0d).add(
                EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS);

    }
}