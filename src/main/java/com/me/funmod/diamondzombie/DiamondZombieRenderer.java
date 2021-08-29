package com.me.funmod.diamondzombie;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.VillagerClothingFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.client.render.entity.model.ZombieVillagerEntityModel;
import net.minecraft.util.Identifier;

public class DiamondZombieRenderer extends ZombieBaseEntityRenderer<DiamondZombie, DiamondZombieModel<DiamondZombie>> {

    private static final Identifier TEXTURE = new Identifier("funmod:textures/entity/diamond_zombie.png");

    public DiamondZombieRenderer(EntityRendererFactory.Context context) {
        this(context, EntityModelLayers.ZOMBIE, EntityModelLayers.ZOMBIE_INNER_ARMOR, EntityModelLayers.ZOMBIE_OUTER_ARMOR);
    }

    public DiamondZombieRenderer(EntityRendererFactory.Context ctx, EntityModelLayer layer, EntityModelLayer legsArmorLayer, EntityModelLayer bodyArmorLayer) {
        super(ctx, new DiamondZombieModel<>(ctx.getPart(layer)), new DiamondZombieModel<>(ctx.getPart(legsArmorLayer)), new DiamondZombieModel<>(ctx.getPart(bodyArmorLayer)));
    }

    public Identifier getTexture(DiamondZombie zombieEntity) {
        return TEXTURE;
    }
}
