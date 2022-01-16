package com.me.funmod.mimic;

import com.me.funmod.rockzombie.RockZombie;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.CreeperEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.CreeperChargeFeatureRenderer;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;


@Environment(EnvType.CLIENT)
public class MimicRenderer extends MobEntityRenderer<Mimic, CreeperEntityModel<Mimic>> {
    private static final Identifier TEXTURE = new Identifier("funmod:textures/entity/testmimic.png");

    public MimicRenderer(EntityRendererFactory.Context context) {
        super(context, new CreeperEntityModel(context.getPart(EntityModelLayers.CREEPER)), 0.5F);
    }

    protected void scale(Mimic mimicEntity, MatrixStack matrixStack, float f) {
        float g = mimicEntity.getClientFuseTime(f);
        float h = 1.0F + MathHelper.sin(g * 100.0F) * g * 0.01F;
        g = MathHelper.clamp(g, 0.0F, 1.0F);
        g *= g;
        g *= g;
        float i = (1.0F + g * 0.4F) * h;
        float j = (1.0F + g * 0.1F) / h;
        matrixStack.scale(i, j, i);
    }

    protected float getAnimationCounter(Mimic mimicEntity, float f) {
        float g = mimicEntity.getClientFuseTime(f);
        return (int)(g * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(g, 0.5F, 1.0F);
    }

    public Identifier getTexture(Mimic mimicEntity) {
        return TEXTURE;
    }
}








