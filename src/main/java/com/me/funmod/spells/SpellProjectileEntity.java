package com.me.funmod.spells;

import com.me.funmod.FunMod;
import com.me.funmod.projectiles.EntitySpawnPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class SpellProjectileEntity  extends ThrownItemEntity implements FlyingItemEntity {
    private static final TrackedData<CompoundTag> SPELL;
    private static final TrackedData<CompoundTag> OTHERSPELLS;
    private boolean amDead = false;
    private int aliveTimer = 0;
    public SpellProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public SpellProjectileEntity(World world, LivingEntity owner) {
        super(FunMod.SPELLPROJECTILEENTITY, owner, world);
    }

    public SpellProjectileEntity(double x, double y, double z, World world, Entity owner, List<Spell> spells) {
        super(FunMod.SPELLPROJECTILEENTITY, x, y, z, world);
        this.initSpells(spells);
        this.setOwner(owner);
    }

    public SpellProjectileEntity(World world, LivingEntity owner, List<Spell> spells) {
        this(world, owner);
        this.initSpells(spells);
        System.out.println("Spawining spell " + this.getSpell().getName());

    }

    protected void initSpells(List<Spell> spells)
    {
        this.setSpell(spells.get(0));
        if(spells.size() > 1) {
            this.setOtherSpells(spells.subList(1, spells.size()));
        }

    }
    @Override
    protected Item getDefaultItem() {
        return FunMod.SPELL;
    }

    public Packet<?> createSpawnPacket() {
        return EntitySpawnPacket.create(this, FunMod.PacketID);
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            if(this.aliveTimer < 5) {
                return;
            }
            Spell spell = this.getSpell();
            System.out.println("Spell " + spell.getName() + " just died");
            this.spawnNextSpell();
            this.remove();
            //this.amDead = true;
        }
    }

    public void tick() {
        super.tick();
        this.aliveTimer += 1;

        if(this.world.isClient) {
            this.produceParticles(ParticleTypes.DRAGON_BREATH, this.getBlockPos());
        }
    }

    protected void spawnNextSpell() {
        List<Spell> spells = this.getOtherSpells();
        if(!spells.isEmpty()) {
            SpellProjectileEntity spellProjectile = new SpellProjectileEntity(this.getX(), this.getY(), this.getZ(), this.world, this.getOwner(), this.getOtherSpells());
            Vec3d myVel = this.getVelocity().negate();
            spellProjectile.setVelocity(myVel.x, Math.max(myVel.y + .5F, 1.5F), myVel.z, 1.5F, 3.0F);
            //spellProjectile.setProperties(this, this.pitch - 180F, this.yaw - 180F, 0.0F, 1.5F, 1.0F);
            world.playSound((PlayerEntity) this.getOwner(), this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_WOLF_DEATH, SoundCategory.NEUTRAL, 8, 2);
            world.spawnEntity(spellProjectile);
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

    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(SPELL, Spell.EMPTY.toTag());
        this.getDataTracker().startTracking(OTHERSPELLS, new CompoundTag());
    }

    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.put("Spell", this.getRawSpell());

    }

    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.setRawSpell(tag.getCompound("Spell"));
    }
    public void setSpell(Spell spell) {
        this.getDataTracker().set(SPELL, spell.toTag());
    }
    public void setRawSpell(CompoundTag spell) {
        this.getDataTracker().set(SPELL, spell);
    }

    void setOtherSpells(List<Spell> spells) {
        CompoundTag tag = new CompoundTag();
        for(int i = 0;i < spells.size(); i++) {
            String key = String.format("OtherSpell%d",i);
            tag.put(key, spells.get(i).toTag());
        }
        this.getDataTracker().set(OTHERSPELLS, tag);
    }

    ArrayList<Spell> getOtherSpells() {
        CompoundTag tag = this.getDataTracker().get(OTHERSPELLS);
        ArrayList<Spell> spells = new ArrayList<Spell>();
        int i = 0;
        while(true) {
            String key = String.format("OtherSpell%d",i);
            if( !tag.contains(key)) {
                break;
            }
            Spell spell = Spell.fromTag(tag.getCompound(key));
            spells.add(spell);
            i += 1;
        }
        return spells;
    }

    protected CompoundTag getRawSpell() {
        return this.getDataTracker().get(SPELL);
    }

    protected Spell getSpell() {
        return Spell.fromTag((CompoundTag)this.getDataTracker().get(SPELL));
    }

    static {
        SPELL = DataTracker.registerData(SpellProjectileEntity.class, TrackedDataHandlerRegistry.TAG_COMPOUND);
        OTHERSPELLS = DataTracker.registerData(SpellProjectileEntity.class, TrackedDataHandlerRegistry.TAG_COMPOUND);
    }

}
