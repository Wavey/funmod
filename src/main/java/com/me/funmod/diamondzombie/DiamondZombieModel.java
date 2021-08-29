package com.me.funmod.diamondzombie;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.entity.mob.ZombieEntity;

public class DiamondZombieModel <T extends ZombieEntity> extends ZombieEntityModel<T> {
    public DiamondZombieModel(ModelPart modelPart) {
        super(modelPart);
    }


    public boolean isAttacking(T zombieEntity) {
        return zombieEntity.isAttacking();
    }

}