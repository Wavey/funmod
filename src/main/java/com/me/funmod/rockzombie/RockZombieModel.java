package com.me.funmod.rockzombie;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.entity.mob.ZombieEntity;

public class RockZombieModel <T extends ZombieEntity> extends ZombieEntityModel<T> {
    public RockZombieModel(ModelPart modelPart) {
        super(modelPart);
    }

    public boolean isAttacking(T zombieEntity) {
        return zombieEntity.isAttacking();
    }


}
