package com.me.funmod.spells;

import com.me.funmod.FunMod;
import com.me.funmod.projectiles.EntitySpawnPacket;
import com.sun.jna.platform.win32.WinBase;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
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
import net.minecraft.stat.Stat;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.ArrayList;
import java.util.List;

public class SpellProjectileEntity  extends ThrownItemEntity implements FlyingItemEntity {
    private static final TrackedData<NbtCompound> SPELL;
    private static final TrackedData<NbtCompound> OTHERSPELLS;
    private boolean amDead = false;
    private int aliveTimer = 0;
    private int blocksHit = 0;
    private Vec3d oldPos;
    private int invicibilityTimer = 1;
    public BlockPos pos ;
    public BlockPos boxPos1 = new BlockPos(0,0,0);
    public BlockPos boxPos2 = new BlockPos(0,0,0);;

    public SpellProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public SpellProjectileEntity(World world, LivingEntity owner) {
        super(FunMod.SPELLPROJECTILEENTITY, owner, world);
    }

    public SpellProjectileEntity(double x, double y, double z, World world, Entity owner, Spell spell, List<Spell> remainingSpells) {
        super(FunMod.SPELLPROJECTILEENTITY, x, y, z, world);
        setSpell(spell);
        setOtherSpells(remainingSpells);
        this.setOwner(owner);
    }

    public SpellProjectileEntity(World world, LivingEntity owner, Spell spell, List<Spell> remainingSpells) {
        this(world, owner);
        setSpell(spell);
        setOtherSpells(remainingSpells);
        System.out.println("Spawining spell " + this.getSpell().getName());

    }
    public void setVelocity(Entity user, float pitch, float yaw, float roll, float divergence) {
        Spell spell = this.getSpell();
        super.setVelocity(user, pitch, yaw, roll, spell.initialSpeed, divergence);
    }

    @Override
    protected Item getDefaultItem() {
        return FunMod.SPELL;
    }

    public Packet<?> createSpawnPacket() {
        return EntitySpawnPacket.create(this, FunMod.PacketID);
    }

    public static SpellProjectileEntity createFromSpells(World world, LivingEntity player, Vec3d pos, List<Spell> spells) {
        if(spells.size() == 0) {
            return null;
        }
        System.out.println("Casting: " + Spell.getSpellDebugNames(spells));
        Spell initialSpell = new Spell("active");
        List<Spell> remainingSpells = initialSpell.parseSpells(spells);
        SpellProjectileEntity spellProjectile = new SpellProjectileEntity(pos.getX(), pos.getY(), pos.getZ(),
                world, player, initialSpell, remainingSpells);
        spellProjectile.setNoGravity(initialSpell.movementType != Spell.MovementType.Arc);
        if (initialSpell.fireTime > 0) {
            spellProjectile.setOnFireFor(100);
        }
        world.spawnEntity(spellProjectile);
        return spellProjectile;
    }

    protected void die() {
        this.spawnNextSpell();
        this.remove(RemovalReason.KILLED);
        Spell spell = this.getSpell();
        System.out.println("Spell " + spell.getName() + " just died");
    }

    protected void applyPush(Entity e, double radius) {
        Vec3d diff = e.getPos().subtract(getPos());
        Vec3d nDiff = diff.normalize();
        double dist = diff.length();
        double force = 1.0 - (dist / radius);
        if (force > 0) {
            e.addVelocity(force * nDiff.x,1,force * nDiff.z);
        }
    }

    protected List<Entity> getEntitiesWithinRadius(double radius) {
        boxPos1 = boxPos1.add(this.getX()+radius,this.getY()+radius,this.getZ()+radius);
        boxPos2 = boxPos2.add(this.getX()-radius,this.getY()-radius,this.getZ()-radius);
        return world.getOtherEntities(null, new Box(boxPos1,boxPos2));
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
                case Blast:
                    for (Entity e: getEntitiesWithinRadius(5.0)){
                        applyPush(e, 5.0);
                    }
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
        if (spell.fireTime > 0) {
            hitResult.getEntity().setOnFireFor(spell.fireTime);
        }
        LivingEntity livingEntity = (LivingEntity)hitResult.getEntity();
        if (livingEntity != null){
            for (StatusEffectInstance s: spell.effects) {
                // Make sure to use a copy here as the instance has state
                livingEntity.addStatusEffect(new StatusEffectInstance(s));
            }

        }

        switch (spell.entityCollision) {
            case Blast:
                for (Entity e: getEntitiesWithinRadius(5.0)){
                    applyPush(e, 5.0);
                }
                die();
                break;
            case Anvil:

                die();
                break;
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
        Vec3d vel = getVelocity();
        Vec3d pos = getPos();
        super.tick();
        if(this.invicibilityTimer > 0) {
            this.invicibilityTimer -= 1;
        }
        this.aliveTimer += 1;
        Spell spell = this.getSpell();

        if (spell.movementType == Spell.MovementType.CastOnSelf) {
            this.aliveTimer = spell.framesToLive + 1;
            Entity owner = getOwner();
            if (owner != null) {
                onEntityHit(new EntityHitResult(getOwner()));
            }
        }

        if(this.world.isClient) {
            if(spell.movementType == Spell.MovementType.Line) {
                this.produceParticlesAlongLine(ParticleTypes.DRAGON_BREATH, pos, pos.add(vel.normalize().multiply(200)));
            }
            else {
                this.produceParticlesFromPoint(ParticleTypes.DRAGON_BREATH, getPos());
            }
        }
        //else {
            if(this.aliveTimer > spell.framesToLive) {
                die();
            }
        //}
    }
    protected void spawnNextSpell() {
        List<Spell> spells = this.getOtherSpells();
        if(!spells.isEmpty()) {
            LivingEntity owner = (LivingEntity)getOwner();
            SpellProjectileEntity spellProjectile = createFromSpells(world, owner, getPos(), spells);
            if (spellProjectile == null) {
                return;
            }
            Spell currentSpell = getSpell();
            Vec3d myVel = this.getVelocity();
            if (currentSpell.movementType != Spell.MovementType.CastOnSelf) {
                myVel = myVel.negate();
                myVel = myVel.add(0, Math.max(myVel.y +.5, 1.5), 0);
            }

            spellProjectile.setVelocity(myVel.x, Math.max(myVel.y + .5F, 1.5F), myVel.z, 1.5F, 3.0F);
            world.playSound((PlayerEntity) owner, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_WOLF_DEATH, SoundCategory.NEUTRAL, 8, 2);
        }

    }
    @Override
    public void setVelocity(double x, double y, double z, float speed, float divergence) {
        //if (getSpell().movementType == Spell.MovementType.CastOnSelf) {
        //    speed = .1f;
        //    divergence = 0;
        //}
        super.setVelocity(x, y, z, speed, divergence);
    }


    @Environment(EnvType.CLIENT)
    public void produceParticlesFromPoint(ParticleEffect parameters, Vec3d pos) {
        for(int i = 0; i < 10; ++i) {
            double d = this.random.nextGaussian() * 0.02D;
            double e = this.random.nextGaussian() * 0.02D;
            double f = this.random.nextGaussian() * 0.02D;
            this.world.addParticle(parameters, pos.getX(), pos.getY(), pos.getZ(), d, e, f);
        }

    }

    @Environment(EnvType.CLIENT)
    public void produceParticlesAlongLine(ParticleEffect parameters, Vec3d posStart, Vec3d posEnd) {
        Vec3d vStart = new Vec3d(posStart.getX(), posStart.getY(), posStart.getZ());
        Vec3d vDiff = new Vec3d(posEnd.getX(), posEnd.getY(), posEnd.getZ());
        vDiff.subtract(vStart);
        for(int i = 0; i < 100; ++i) {
            Vec3d pos = new Vec3d(vDiff.getX(), vDiff.getY(), vDiff.getZ());

            pos.multiply(random.nextGaussian());
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
        SPELL = DataTracker.registerData(SpellProjectileEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);
        OTHERSPELLS = DataTracker.registerData(SpellProjectileEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);
    }

}
