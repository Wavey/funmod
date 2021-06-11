package com.me.funmod.hunterIlliger;

import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.mob.ZombieEntity;

public class HunterilligerModel <T extends PillagerEntity> extends IllagerEntityModel<T> {
    public HunterilligerModel(float scale, boolean bl) {
        this(scale, 0.0F, 64, bl ? 32 : 64);
    }

    protected HunterilligerModel(float f, float g, int i, int j) {
        super(f, g, i, j);
    }

}
