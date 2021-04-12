package com.me.funmod.diamondzombie;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.util.Identifier;

public class DiamondZombieRenderer extends ZombieBaseEntityRenderer<DiamondZombie, DiamondZombieModel<DiamondZombie>> {

    private static final Identifier TEXTURE = new Identifier("funmod:textures/entity/diamond_zombie.png");

    public DiamondZombieRenderer(EntityRenderDispatcher entityRenderDispatcher) {

        super(entityRenderDispatcher, new DiamondZombieModel<>(0.0F, false), new DiamondZombieModel<>(0.5F, true), new DiamondZombieModel<>(1.0F, true));
    }
    public Identifier getTexture(DiamondZombie zombieEntity) {
        return TEXTURE;
    }
}
