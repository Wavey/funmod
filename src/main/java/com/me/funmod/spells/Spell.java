package com.me.funmod.spells;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;

public class Spell {
    protected final String name;
    public Spell(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
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