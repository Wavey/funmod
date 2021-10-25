package com.me.funmod.mimic;

import com.me.funmod.rockzombie.RockZombie;
import net.minecraft.client.render.entity.CreeperEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.CreeperChargeFeatureRenderer;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.util.Identifier;

public class MimicRenderer extends CreeperEntityRenderer {




    private static final Identifier TEXTURE = new Identifier("funmod:textures/entity/testmimic.png");



    public MimicRenderer(EntityRendererFactory.Context context) {
        super(context);
    }
    public Identifier getTexture(Mimic mimicentity) {
        return TEXTURE;
    }



}



