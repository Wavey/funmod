package com.me.zombie;

import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.entity.mob.ZombieEntity;

public class NewZombieEntityModel<T extends ZombieEntity> extends ZombieEntityModel<T> {

    public NewZombieEntityModel(float scale, boolean bl) {
        this(scale, 0.0F, 64, bl ? 32 : 64);
    }

    protected NewZombieEntityModel(float f, float g, int i, int j) {
        super(f, g, i, j);
    }

    public boolean isAttacking(T zombieEntity) {
        return zombieEntity.isAttacking();
    }
}
