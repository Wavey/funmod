package com.me.funmod.spells;

import com.me.funmod.FunMod;
import com.me.funmod.projectiles.EntitySpawnPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.util.sat4j.core.Vec;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class SpellProjectileEntity  extends ThrownItemEntity implements FlyingItemEntity {
    private static final TrackedData<NbtCompound> SPELL;
    private static final TrackedData<NbtCompound> OTHERSPELLS;
    private boolean amDead = false;
    private int aliveTimer = 0;
    private int blocksHit = 0;
    private BlockPos oldPos;
    private int invicibilityTimer = 1;
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
    public void setProperties(Entity user, float pitch, float yaw, float roll, float divergence) {
        Spell spell = this.getSpell();
        super.setProperties(user, pitch, yaw, roll, spell.initialSpeed, divergence);
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

    protected void die() {
        this.spawnNextSpell();
        this.remove();
        Spell spell = this.getSpell();
        System.out.println("Spell " + spell.getName() + " just died");
    }

    protected void onBlockHit(BlockHitResult hitResult) {
        super.onBlockHit(hitResult);
        if (!this.world.isClient) {
            if(this.invicibilityTimer > 0) {
                return;
            }
            Spell spell = this.getSpell();
            switch(spell.blockCollision) {
                case Die:
                    die();
                    break;
                case Bounce:
                    Vec3d vel = this.getVelocity();
                    Vec3d reflectVel = new Vec3d(hitResult.getSide().getUnitVector());
                    Vec3d reflected = vel.subtract(reflectVel.multiply((2*vel.dotProduct(reflectVel))));
                    this.setVelocity(reflected);
                    this.invicibilityTimer = 1;
                    break;
                case Destroy:
                    this.blocksHit += 1;
                    if(this.blocksHit > 7) {
                        die();
                        return;
                    }
                    BlockPos pos = hitResult.getBlockPos();
                    for(int x = -1; x < 2; x++) {
                        for (int y = -1; y < 2; y++) {
                            for (int z = -1; z < 3; z++) {
                                this.world.breakBlock(pos.add(x, y, z), true);
                            }
                        }
                    }
                    break;
            }
        }

    }

    protected void SwapOwnerWithEntity(EntityHitResult hitResult) {
        Entity owner = this.getOwner();
        if(owner == null) {
            return;
        }
        Entity other = hitResult.getEntity();
        Vec3d ownerPos = owner.getPos();
        Vec3d otherPos = other.getPos();
        owner.teleport(otherPos.x, otherPos.y, otherPos.z);
        other.teleport(ownerPos.x, ownerPos.y, ownerPos.z);
        System.out.println("Swapping player and entity");

    }

    protected void onEntityHit(EntityHitResult hitResult) {
        Spell spell = this.getSpell();
        switch (spell.entityCollision) {
            case Swap:
                this.SwapOwnerWithEntity(hitResult);
                die();
                break;
            case Damage:
                hitResult.getEntity().damage(DamageSource.GENERIC, spell.entityDamage);
                die();
                break;
            case Die:
                die();
                break;
            default:
                break;
        }
    }

    public void tick() {
        this.oldPos = this.getBlockPos();
        super.tick();
        if(this.invicibilityTimer > 0) {
            this.invicibilityTimer -= 1;
        }
        this.aliveTimer += 1;
        Spell spell = this.getSpell();

        if(this.world.isClient) {
            if(spell.movementType == Spell.MovementType.Line) {
                this.produceParticlesAlongLine(ParticleTypes.DRAGON_BREATH, this.oldPos, this.getBlockPos());
            }
            else {
                this.produceParticlesFromPoint(ParticleTypes.DRAGON_BREATH, this.getBlockPos());
            }
        }
        else {
            if(this.aliveTimer > spell.framesToLive) {
                die();
            }
        }
    }
    protected void spawnNextSpell() {
    }
    protected void spawnNextSpell_() {
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
    public void produceParticlesFromPoint(ParticleEffect parameters, BlockPos pos) {
        for(int i = 0; i < 10; ++i) {
            double d = this.random.nextGaussian() * 0.02D;
            double e = this.random.nextGaussian() * 0.02D;
            double f = this.random.nextGaussian() * 0.02D;
            this.world.addParticle(parameters, pos.getX(), pos.getY(), pos.getZ(), d, e, f);
        }

    }

    @Environment(EnvType.CLIENT)
    public void produceParticlesAlongLine(ParticleEffect parameters, BlockPos posStart, BlockPos posEnd) {
        Vec3f vStart = new Vec3f(posStart.getX(), posStart.getY(), posStart.getZ());
        Vec3f vEnd = new Vec3f(posEnd.getX(), posEnd.getY(), posEnd.getZ());
        Vec3f vDiff = vEnd.copy();
        vDiff.subtract(vStart);
        Vec3f pos;
        for(int i = 0; i < 100; ++i) {

            pos = vDiff.copy(); pos.scale((float)this.random.nextGaussian());
            pos.add(vStart);
            double d = this.random.nextGaussian() * 0.02D;
            double e = this.random.nextGaussian() * 0.02D;
            double f = this.random.nextGaussian() * 0.02D;
            this.world.addParticle(parameters, pos.getX(), pos.getY(), pos.getZ(), d, e, f);
        }

    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(SPELL, Spell.EMPTY.toTag());
        this.getDataTracker().startTracking(OTHERSPELLS, new NbtCompound());
    }

    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.put("Spell", this.getRawSpell());

    }

    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        this.setRawSpell(tag.getCompound("Spell"));
    }
    public void setSpell(Spell spell) {
        this.getDataTracker().set(SPELL, spell.toTag());
    }
    public void setRawSpell(NbtCompound spell) {
        this.getDataTracker().set(SPELL, spell);
    }

    void setOtherSpells(List<Spell> spells) {
        NbtCompound tag = new NbtCompound();
        for(int i = 0;i < spells.size(); i++) {
            String key = String.format("OtherSpell%d",i);
            tag.put(key, spells.get(i).toTag());
        }
        this.getDataTracker().set(OTHERSPELLS, tag);
    }

    ArrayList<Spell> getOtherSpells() {
        NbtCompound tag = this.getDataTracker().get(OTHERSPELLS);
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

    protected NbtCompound getRawSpell() {
        return this.getDataTracker().get(SPELL);
    }

    protected Spell getSpell() {
        return Spell.fromTag((NbtCompound)this.getDataTracker().get(SPELL));
    }

    static {
        SPELL = DataTracker.registerData(SpellProjectileEntity.class, TrackedDataHandlerRegistry.TAG_COMPOUND);
        OTHERSPELLS = DataTracker.registerData(SpellProjectileEntity.class, TrackedDataHandlerRegistry.TAG_COMPOUND);
    }

}
