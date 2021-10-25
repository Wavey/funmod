package com.me.funmod.mimic;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.world.World;

public class Mimic extends CreeperEntity {

    public Mimic(EntityType<? extends CreeperEntity> entityType, World world) {
        super(entityType, world);
    }
}
