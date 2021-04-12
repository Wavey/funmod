package com.me.funmod.spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class TestSpell extends Spell{
    public TestSpell(String name) {
        super(name);
    }
    @Override
    public void doTheThing (World world, PlayerEntity player) {


        SnowballEntity snowballEntity = new SnowballEntity(world, player);
        snowballEntity.setProperties(player, player.pitch, player.yaw, 0.0F, 1.5F, 1.0F);
        world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_WOLF_DEATH, SoundCategory.NEUTRAL, 8,2);
        world.spawnEntity(snowballEntity);



    }

}
