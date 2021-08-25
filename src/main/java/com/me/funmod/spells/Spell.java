package com.me.funmod.spells;

import com.me.funmod.projectiles.ZombieProjectile;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.List;

public class Spell {
    protected String name;
    public MovementType movementType = MovementType.Arc;
    public BlockCollisionType blockCollision = BlockCollisionType.Destroy;
    public EntityCollisionType entityCollision = EntityCollisionType.Damage;
    public float initialSpeed = 1.5F;
    public float entityDamage = 1;
    public int framesToLive = 80;

    public Spell(String name) {
        this.name = name;
        if(this.movementType == MovementType.Line) {
            this.initialSpeed *= 10;
            this.framesToLive = 1;
        }
    }
    public Spell(String name, MovementType movementType, BlockCollisionType blockCollision, EntityCollisionType entityeCollision,
                 float initialSpeed, float entityDamage, int framesToLive) {
        this.name = name;
        this.movementType = movementType;
        this.blockCollision = blockCollision;
        this.entityCollision = entityeCollision;
        this.initialSpeed = initialSpeed;
        this.entityDamage = entityDamage;
        this.framesToLive = framesToLive;

        if(this.movementType == MovementType.Line) {
            this.initialSpeed *= 10;
            this.framesToLive = 1;
        }
    }
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Spell)) {
            return false;
        }

        Spell s = (Spell) o;
        return (s.name.equals(this.name));
    }

    public String getName() {
        return this.name;
    }

    public void doTheOldThing (World world, PlayerEntity player) {
        SnowballEntity snowballEntity = new SnowballEntity(world, player);
        snowballEntity.setProperties(player, player.pitch, player.yaw, 0.0F, 1.5F, 1.0F);
        world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_WOLF_DEATH, SoundCategory.NEUTRAL, 8,2);
        world.spawnEntity(snowballEntity);
    }
    public void doTheThing (World world, PlayerEntity player) {
        ZombieProjectile spellProjectile = new ZombieProjectile(world, player);
        spellProjectile.setProperties(player, player.pitch, player.yaw, 0.0F, 1.5F, 1.0F);
        world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_WOLF_DEATH, SoundCategory.NEUTRAL, 8,2);
        world.spawnEntity(spellProjectile);
    }

    public static void spawnSpells(World world, PlayerEntity player, List<Spell> spells) {
        if(spells.size() == 0) {
            return;
        }
        SpellProjectileEntity spellProjectile = new SpellProjectileEntity(world, player, spells);
        spellProjectile.setProperties(player, player.pitch, player.yaw, 0.0F, 0.0F);
        world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_WOLF_DEATH, SoundCategory.NEUTRAL, 8,2);
        world.spawnEntity(spellProjectile);
    }

    public static Spell fromTag(NbtCompound tag) {

        Spell spell = new Spell(tag.getString("name"));
        spell.framesToLive = tag.getInt("framesToLive");
        spell.entityCollision = EntityCollisionType.values()[tag.getInt("entityCollision")];
        spell.blockCollision = BlockCollisionType.values()[tag.getInt("blockCollision")];
        spell.movementType = MovementType.values()[tag.getInt("movementType")];
        spell.initialSpeed = tag.getFloat("initialSpeed");
        spell.entityDamage = tag.getFloat("entityDamage");
        return spell;
    }
    public static final Spell EMPTY = new Spell("empty");

    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();
        tag.putString("name", this.name);
        tag.putInt("movementType", this.movementType.ordinal());
        tag.putInt("blockCollision", this.blockCollision.ordinal());
        tag.putInt("entityCollision", this.entityCollision.ordinal());
        tag.putFloat("initialSpeed", this.initialSpeed);
        tag.putFloat("entityDamage", this.entityDamage);
        tag.putInt("framesToLive", this.framesToLive);
        return tag;
    }
    public enum MovementType {
        Arc,
        Line,
        Straight
    }
    public enum BlockCollisionType {
        Die,
        Bounce,
        Destroy
    }
    public enum EntityCollisionType {
        Damage,
        Die,
        Swap
    }

}
