package com.me.funmod.spells;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class SpellProjectileEntityModel extends Model {
    public ModelPart sphere;
    private final float scale;

    public SpellProjectileEntityModel(float scale) {
        this(RenderLayer::getEntityCutoutNoCull, scale);
    }
    public SpellProjectileEntityModel(Function<Identifier, RenderLayer> function, float scale) {
        super(function);
        this.scale = scale;
        this.sphere = new ModelPart(this, 0, 0);
        this.sphere.addCuboid(0,0,0, 10, 10, 10, scale);

    }

    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        matrices.push();
        this.sphere.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        matrices.pop();
    }
}
