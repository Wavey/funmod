package com.me.funmod.diamondzombie;

import com.me.funmod.ai.DiamondZombieAIGoal;
import com.me.funmod.ai.DiamondZombieMovementAI;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
//import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
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
        this.moveControl = new DZMoveControl(this);
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
        public NbtCompound toTag(NbtCompound tag) {
            tag.putString("effect", this.type.asString());
            tag.putInt("posx", this.pos.getX());
            tag.putInt("posy", this.pos.getY());
            tag.putInt("posz", this.pos.getZ());
            tag.putFloat("duration", this.duration);
            return tag;
        }
        public boolean hurtByFire(){return false;}

        public static Effect fromTag(NbtCompound tag) {

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
    public static DefaultAttributeContainer.Builder createZombieAttributes() {
        return ZombieEntity.createZombieAttributes() .add(
                EntityAttributes.GENERIC_MAX_HEALTH, 175d);
    }

    public DZMoveControl getDzMoveControl() {
        return (DZMoveControl)this.moveControl;
    }

    protected Effect currentEffect = null;

    @Override
    protected void initGoals() {
        this.goalSelector.add(2, new DiamondZombieMovementAI(this,1.7d,false,this));

        //this.goalSelector.add(2,new FollowMobGoal<PlayerEntity>(this, PlayerEntity.class,false));
        this.goalSelector.add(2,new DiamondZombieAIGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<PlayerEntity>(this, PlayerEntity.class, true));

    }

    @Override
    public void tick() {
        super.tick();
        if(this.world.isClient) {
            runEffects();
        }

    }

    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        if(this.currentEffect != null) {
            tag.put("current_effect", this.currentEffect.toTag(new NbtCompound()));
        }
    }

   public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
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
