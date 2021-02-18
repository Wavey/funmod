package com.me.zombie;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.world.World;

public class NewZombieEntity extends ZombieEntity {
    public NewZombieEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }
    public static void registerEntitySpawnEgg(EntityType<?> type, int color1, int color2){
        //SpawnEggItem item = new SpawnEggItem(type, color1, color2, new FabricItemSettings().group(ItemGroup.MISC));
        //registerEntitySpawnEgg();

    }

    public static DefaultAttributeContainer.Builder createZombieAttributes() {
        return ZombieEntity.createZombieAttributes().add(
                EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0D).add (
                EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.33000000417232513D).add(
                EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D).add(
                EntityAttributes.GENERIC_ARMOR, 2.0D).add(
                EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS);
    }

    @Override
    public void setBaby(boolean baby) {

    }
}
