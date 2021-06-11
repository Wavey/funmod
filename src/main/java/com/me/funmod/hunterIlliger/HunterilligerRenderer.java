package com.me.funmod.hunterIlliger;

import com.me.funmod.diamondzombie.DiamondZombieModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class HunterilligerRenderer extends IllagerEntityRenderer<Hunterilliger> {

    private static final Identifier TEXTURE = new Identifier("funmod:textures/entity/hunter_pillager.png");
    public HunterilligerRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new IllagerEntityModel(0.0F, 0.0F, 64, 64), 0.5F);
        this.addFeature(new HeldItemFeatureRenderer(this));
    }

    public Identifier getTexture(Hunterilliger pillagerEntity) {
        return TEXTURE;
    }
    }

