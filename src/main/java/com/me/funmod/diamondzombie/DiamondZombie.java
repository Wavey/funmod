package com.me.funmod.diamondzombie;

import com.me.funmod.ai.DiamondZombieAIGoal;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class DiamondZombie extends ZombieEntity {
    public DiamondZombie(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
        {
        }
    }
    public static class Effect {
        public DefaultParticleType type;
        public BlockPos pos;
        public float duration;
        public Effect(DefaultParticleType type, BlockPos pos, float duration) {
            this.type = type;
            this.pos = pos;
            this.duration = duration;
        }
        public CompoundTag toTag(CompoundTag tag) {
            tag.putString("effect", this.type.asString());
            tag.putInt("posx", this.pos.getX());
            tag.putInt("posy", this.pos.getY());
            tag.putInt("posz", this.pos.getZ());
            tag.putFloat("duration", this.duration);
            return tag;
        }

        public static Effect fromTag(CompoundTag tag) {

            float duration = tag.getFloat("duration");
            BlockPos pos =  new BlockPos(
                    tag.getInt("posx"),
                    tag.getInt("posy"),
                    tag.getInt("posz"));

            Identifier id = Identifier.tryParse(tag.getString("effect"));
            DefaultParticleType effect = (DefaultParticleType)Registry.PARTICLE_TYPE.get(id);

            return new Effect(effect, pos, duration);
        }
    }

    protected Effect currentEffect = null;

    @Override
    protected void initGoals() {

        this.goalSelector.add(2, new ZombieAttackGoal(this, 1.0D, false));
        this.goalSelector.add(2,new FollowTargetGoal(this, PlayerEntity.class,false));
        this.goalSelector.add(2,new DiamondZombieAIGoal(this));

    }

    @Override
    public void tick() {
        super.tick();
        if(this.world.isClient) {
            runEffects();
        }

    }

    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        if(this.currentEffect != null) {
            tag.put("current_effect", this.currentEffect.toTag(new CompoundTag()));
        }
    }

   public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        if(tag.contains("current_effect")) {
            this.currentEffect = Effect.fromTag(tag.getCompound("current_effect"));
        }
        else {
            this.currentEffect = null;
        }

   }

        @Environment(EnvType.CLIENT)
    protected void runEffects() {
        if(this.currentEffect != null) {
            produceParticles(this.currentEffect.type, this.currentEffect.pos);
            this.currentEffect.duration -= 1;
            if(this.currentEffect.duration <= 0) {
                this.currentEffect = null;
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if(status == 126) {
            spawnPlayerEffect();
        }
        else {
            super.handleStatus(status);
        }
    }

    public void sendPlayerSpawnEffect() {
        this.world.sendEntityStatus(this, (byte)126);

    }

    @Environment(EnvType.CLIENT)
    public void spawnPlayerEffect() {
        PlayerEntity nearestPlayer = this.world.getClosestPlayer(getX(), getY(), getZ(), 100, false);
        if( nearestPlayer != null) {
            addEffect(ParticleTypes.DRAGON_BREATH, nearestPlayer.getBlockPos().add(0,1,0) , 10);
        }
    }

    @Environment(EnvType.CLIENT)
    public void produceParticles(ParticleEffect parameters, BlockPos pos) {
        for(int i = 0; i < 10; ++i) {
            double d = this.random.nextGaussian() * 0.02D;
            double e = this.random.nextGaussian() * 0.02D;
            double f = this.random.nextGaussian() * 0.02D;
            this.world.addParticle(parameters, pos.getX(), pos.getY(), pos.getZ(), d, e, f);
        }

    }

    public void addEffect(DefaultParticleType params, BlockPos pos, float duration) {
        this.currentEffect = new DiamondZombie.Effect(params, pos, duration);

    }

}
