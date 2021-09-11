package com.me.funmod.NetherGuy;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.util.Identifier;

public class NetherGuyRenderer extends SkeletonEntityRenderer {
     {

    }
    private static final Identifier TEXTURE = new Identifier("funmod:textures/entity/nether_guy.png");

    public NetherGuyRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    public Identifier getTexture(AbstractSkeletonEntity abstractSkeletonEntity) {
        return TEXTURE;
    }
}



