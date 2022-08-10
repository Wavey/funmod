package com.me.funmod.spells;

import com.me.funmod.spells.SpellProjectileEntity;
import com.me.funmod.projectiles.ZombieProjectile;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

public class Spell {
    protected String name;
    public MovementType movementType = MovementType.None;
    public BlockCollisionType blockCollision = BlockCollisionType.None;
    public EntityCollisionType entityCollision = EntityCollisionType.None;
    public float initialSpeed = 0;
    public float entityDamage = 0;
    public int framesToLive = 0;
    public int fireTime = 0;
    public List<StatusEffectInstance> effects = new ArrayList<>();


    public Spell(String name) {
        this.name = name;
    }
    public Spell(String name, MovementType movementType, BlockCollisionType blockCollision, EntityCollisionType entityeCollision,
                 float initialSpeed, float entityDamage, int fireTime, int framesToLive, StatusEffectInstance statusEffect) {
        this.name = name;
        this.movementType = movementType;
        this.blockCollision = blockCollision;
        this.entityCollision = entityeCollision;
        this.initialSpeed = initialSpeed;
        this.entityDamage = entityDamage;
        this.framesToLive = framesToLive;
        this.fireTime = fireTime;
        if(statusEffect != null) {
            effects.add(statusEffect);
        }



        if(this.movementType == MovementType.Line) {
            this.initialSpeed *= 10;
            this.framesToLive = 2;
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

    protected void combine(Spell other) {
        if (other.movementType != MovementType.None) {
            this.movementType = other.movementType;
        }
        if (other.blockCollision != BlockCollisionType.None) {
            this.blockCollision = other.blockCollision;
        }
        if (other.entityCollision != EntityCollisionType.None) {
            this.entityCollision = other.entityCollision;
        }
        this.effects.addAll(other.effects);
        initialSpeed += other.initialSpeed;
        entityDamage += other.entityDamage;
        fireTime += other.fireTime;
        framesToLive += other.framesToLive;
    }

    public String getName() {
        return this.name;
    }

    public void doTheOldThing (World world, PlayerEntity player) {
        SnowballEntity snowballEntity = new SnowballEntity(world, player);
        snowballEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 1.5F, 1.0F);
        world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_WOLF_DEATH, SoundCategory.NEUTRAL, 8,2);
        world.spawnEntity(snowballEntity);
    }
    public void doTheThing (World world, PlayerEntity player) {
        ZombieProjectile spellProjectile = new ZombieProjectile(world, player);
        spellProjectile.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 1.5F, 1.0F);
        world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_WOLF_DEATH, SoundCategory.NEUTRAL, 8,2);
        world.spawnEntity(spellProjectile);
    }
    public void setFrom(Spell other) {
        this.framesToLive = other.framesToLive;
        this.fireTime = other.fireTime;
        this.blockCollision = other.blockCollision;
        this.initialSpeed = other.initialSpeed;
        this.entityDamage = other.entityDamage;
        this.entityCollision = other.entityCollision;
        this.movementType = other.movementType;
        this.effects = new ArrayList<>();
        this.effects.addAll(other.effects);
    }

    public List<Spell> parseSpells(List<Spell> spells) {
        for(int i = 0;i < spells.size(); ++i) {
            Spell currentSpell = spells.get(i);
            if (currentSpell.movementType != MovementType.None) {
                // We have found our projectile
                setFrom(currentSpell);
                List<Spell> remainingSpells = new ArrayList<Spell>(spells.subList(i +1, spells.size()));
                if (i > 0) {
                    // Modifiers
                    List<Spell> modifiers = spells.subList(0, i);
                    for (Spell mod : modifiers) {
                        combine(mod);
                    }
                }
                return remainingSpells;
            }

        }
        return new ArrayList<Spell>();
    }

    public static void fireSpellsFromEntity(World world, PlayerEntity player, List<Spell> spells) {
        Vec3d playerPos = new Vec3d(player.getX(), player.getEyeY()- .1, player.getZ());
        SpellProjectileEntity spellProjectile = SpellProjectileEntity.createFromSpells(world, player,
                playerPos,spells);
        if (spellProjectile == null) {
            return;
        }
        spellProjectile.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 0.0F);
        world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_WOLF_DEATH, SoundCategory.NEUTRAL, 8,2);
    }
    public static String getSpellDebugNames(List<Spell> spells) {
        if(spells.isEmpty()) {
            return "Empty";
        }

        ArrayList<String> spellNames = new ArrayList<String>(spells.size());
        for(Spell s : spells) {
            spellNames.add(s.getName());
        }
        return String.join(" - ", spellNames);
    }

    public static Spell fromTag(NbtCompound tag) {
        Spell spell = new Spell(tag.getString("name"));
        spell.framesToLive = tag.getInt("framesToLive");
        spell.entityCollision = EntityCollisionType.values()[tag.getInt("entityCollision")];
        spell.blockCollision = BlockCollisionType.values()[tag.getInt("blockCollision")];
        spell.movementType = MovementType.values()[tag.getInt("movementType")];
        spell.fireTime = tag.getInt("fireTime");
        spell.initialSpeed = tag.getFloat("initialSpeed");
        spell.entityDamage = tag.getFloat("entityDamage");

        spell.effects = new ArrayList<StatusEffectInstance>();

        NbtCompound effectTag = tag.getCompound("effects");
        int count = 0;
        while(effectTag.contains(String.valueOf(count))) {
            NbtCompound etag = effectTag.getCompound(String.valueOf(count));
            StatusEffectInstance instance = StatusEffectInstance.fromNbt(etag);
            spell.effects.add(instance);
            count ++;
        }

        return spell;
    }
    public static final Spell EMPTY = new Spell("empty");

    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();
        tag.putString("name", this.name);
        tag.putInt("movementType", this.movementType.ordinal());
        tag.putInt("blockCollision", this.blockCollision.ordinal());
        tag.putInt("entityCollision", this.entityCollision.ordinal());
        tag.putFloat("fireTime", this.fireTime);
        tag.putFloat("initialSpeed", this.initialSpeed);
        tag.putFloat("entityDamage", this.entityDamage);
        tag.putInt("framesToLive", this.framesToLive);
        NbtCompound effectTag = new NbtCompound();
        int count = 0;
        for (StatusEffectInstance s: this.effects) {
            NbtCompound stag = new NbtCompound();
            s.writeNbt(stag);
            effectTag.put(String.valueOf(count), stag);
            count ++;
        }
        tag.put("effects", effectTag);

        return tag;
    }
    public enum MovementType {
        None,
        Arc,
        Line,
        Straight,
        CastOnSelf
    }
    public enum BlockCollisionType {
        None,
        Anvil,
        Die,
        Bounce,
        Destroy,
        Blast
    }
    public enum EntityCollisionType {
        None,
        Anvil,
        Damage,
        Die,
        Swap,
        PotionEffect,
        Blast
    }

}
