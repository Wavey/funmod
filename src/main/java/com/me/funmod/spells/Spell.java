package com.me.funmod.spells;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class Spell {
    protected final String name;
    public Spell(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void doTheThing (World world, PlayerEntity player) {


           // SnowballEntity snowballEntity = new SnowballEntity(world, player);
            // snowballEntity.setProperties(player, player.pitch, player.yaw, 0.0F, 1.5F, 1.0F);
           // world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_WOLF_DEATH, SoundCategory.NEUTRAL, 8,2);
          //  world.spawnEntity(snowballEntity);



        }

    public static Spell fromTag(CompoundTag tag) {
        return new Spell(tag.getString("name"));
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putString("name", this.name);
        return tag;
    }

}