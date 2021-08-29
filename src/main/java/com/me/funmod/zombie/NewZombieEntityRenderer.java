package com.me.funmod.zombie;

import com.me.funmod.diamondzombie.NewZombieModel;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

public class NewZombieEntityRenderer extends ZombieBaseEntityRenderer <NewZombieEntity, NewZombieEntityModel<NewZombieEntity>> {
    private static final Identifier TEXTURE = new Identifier("funmod:textures/entity/newzombie.png");
    public NewZombieRenderer(EntityRendererFactory.Context context) {
        this(context, EntityModelLayers.ZOMBIE, EntityModelLayers.ZOMBIE_INNER_ARMOR, EntityModelLayers.ZOMBIE_OUTER_ARMOR);
    }

    public NewZombieRenderer(EntityRendererFactory.Context ctx, EntityModelLayer layer, EntityModelLayer legsArmorLayer, EntityModelLayer bodyArmorLayer) {
        super(ctx, new NewZombieModel<>(ctx.getPart(layer)), new NewZombieModel<>(ctx.getPart(legsArmorLayer)), new NewZombieModel<>(ctx.getPart(bodyArmorLayer)));
    }
    public Identifier getTexture(NewZombieEntity zombieEntity) {
        return TEXTURE;
    }
}
