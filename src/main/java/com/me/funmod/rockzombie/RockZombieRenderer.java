package com.me.funmod.rockzombie;

import com.me.zombie.NewZombieEntity;
import com.me.zombie.NewZombieEntityModel;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.util.Identifier;

public class RockZombieRenderer extends ZombieBaseEntityRenderer<RockZombie, RockZombieModel<RockZombie>> {

    private static final Identifier TEXTURE = new Identifier("funmod:textures/entity/rock_zombie.png");

    public RockZombieRenderer(EntityRenderDispatcher entityRenderDispatcher) {

        super(entityRenderDispatcher, new RockZombieModel<>(0.0F, false), new RockZombieModel<>(0.5F, true), new RockZombieModel<>(1.0F, true));
    }
    public Identifier getTexture(RockZombie zombieEntity) {
        return TEXTURE;
    }
}
