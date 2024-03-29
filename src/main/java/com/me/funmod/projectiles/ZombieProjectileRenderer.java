package com.me.funmod.projectiles;

import com.me.funmod.FunMod;
import com.me.funmod.projectiles.ZombieProjectile;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class ZombieProjectileRenderer extends EntityRenderer<ZombieProjectile> {
    private static final Identifier TEXTURE = new Identifier("funmod:textures/item/zombieprojectile.png");
    private final ItemRenderer itemRenderer;
    private final float scale;
    private final boolean lit;

    public ZombieProjectileRenderer(EntityRendererFactory.Context context, ItemRenderer itemRenderer, float scale, boolean lit) {
        super(context);
        this.itemRenderer = itemRenderer;
        this.scale = scale;
        this.lit = lit;
    }

    public ZombieProjectileRenderer(EntityRendererFactory.Context context, ItemRenderer itemRenderer) {
        this(context, itemRenderer, 1.0F, false);
    }

    protected int getBlockLight(ZombieProjectile entity, BlockPos blockPos) {
        return this.lit ? 15 : super.getBlockLight(entity, blockPos);
    }

    public void render(ZombieProjectile entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (entity.age >= 2 || this.dispatcher.camera.getFocusedEntity().squaredDistanceTo(entity) >= 12.25D) {
            matrices.push();
            matrices.scale(this.scale, this.scale, this.scale);
            matrices.multiply(this.dispatcher.getRotation());
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
            this.itemRenderer.renderItem(entity.getStack(), ModelTransformation.Mode.GROUND, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);
            matrices.pop();
            super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        }
    }

    @Override
    public Identifier getTexture(ZombieProjectile entity) {
        return TEXTURE;
    }
}
