package com.me.funmod.rockzombie;

import com.me.funmod.zombie.NewZombieEntityModel;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

public class RockZombieRenderer extends ZombieBaseEntityRenderer<RockZombie, RockZombieModel<RockZombie>> {

    private static final Identifier TEXTURE = new Identifier("funmod:textures/entity/rock_zombie.png");

    public RockZombieRenderer(EntityRendererFactory.Context context) {
        this(context, EntityModelLayers.ZOMBIE, EntityModelLayers.ZOMBIE_INNER_ARMOR, EntityModelLayers.ZOMBIE_OUTER_ARMOR);
    }

    public RockZombieRenderer(EntityRendererFactory.Context ctx, EntityModelLayer layer, EntityModelLayer legsArmorLayer, EntityModelLayer bodyArmorLayer) {
        super(ctx, new RockZombieModel<>(ctx.getPart(layer)), new RockZombieModel<>(ctx.getPart(legsArmorLayer)), new RockZombieModel<>(ctx.getPart(bodyArmorLayer)));
    }
    public Identifier getTexture(RockZombie zombieEntity) {
        return TEXTURE;
    }
}
