package com.me.funmod.zombie;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.entity.mob.ZombieEntity;

public class NewZombieEntityModel<T extends ZombieEntity> extends ZombieEntityModel<T> {

    public NewZombieEntityModel(ModelPart modelPart) {
        super(modelPart);
    }

    public boolean isAttacking(T zombieEntity) {
        return zombieEntity.isAttacking();
    }

}
