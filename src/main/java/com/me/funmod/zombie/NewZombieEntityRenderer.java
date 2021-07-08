package com.me.funmod.zombie;

import net.minecraft.client.render.entity.*;
import net.minecraft.util.Identifier;

public class NewZombieEntityRenderer extends ZombieBaseEntityRenderer <NewZombieEntity, NewZombieEntityModel<NewZombieEntity>> {
    private static final Identifier TEXTURE = new Identifier("funmod:textures/entity/newzombie.png");
    public NewZombieEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new NewZombieEntityModel<>(0.0F, false), new NewZombieEntityModel<>(0.5F, true), new NewZombieEntityModel<>(1.0F, true));
    }
    public Identifier getTexture(NewZombieEntity zombieEntity) {
        return TEXTURE;
    }
}
