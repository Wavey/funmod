package com.me.funmod;

import com.me.funmod.DiamondZombie.DiamondZombie;
import com.me.funmod.Porjectiles.ZombieProjectile;
import com.me.funmod.rockzombie.RockZombie;
import com.me.funmod.spells.WandItem;
import com.me.zombie.NewZombieEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.biome.SpawnSettings;

public class FunMod implements ModInitializer {
    public static final String ModID = "funmod"; // This is just so we can refer to our ModID easier.
    public static final  Item WAND = new WandItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1));
    public static final Item HEALTHY_SOUP = new Item(new FabricItemSettings().group(ItemGroup.FOOD).maxCount(1).food(new FoodComponent.Builder().hunger(0).saturationModifier(0f).alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.HUNGER,20*30),1).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,20*4),0.7f).statusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS,20*20),1).statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,20*10),1).build()));
    public static final Item HEALTHY_JUICE = new Item(new FabricItemSettings().group(ItemGroup.FOOD).maxCount(1).food(new FoodComponent.Builder().hunger(0).saturationModifier(0f).alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.HUNGER,20*20),1).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,20*4),1).statusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS,20*10),1).build()));
    public static final EntityType<NewZombieEntity> NEWZOMBIE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("funmod", "newzombie"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, NewZombieEntity::new).dimensions(
                    EntityDimensions.fixed(1.0f, 2.0f)).build()
    );
    public static final EntityType<RockZombie> ROCKZOMBIE = Registry.register(
           Registry.ENTITY_TYPE,
            new Identifier("funmod", "rockzombie"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RockZombie::new).dimensions(
                    EntityDimensions.fixed(1.0f, 2.0f)).build()
    );
    public static final EntityType<DiamondZombie> DIAMONDZOMBIE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("funmod", "diamondzombie"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DiamondZombie::new).dimensions(
                    EntityDimensions.fixed(1.0f, 2.0f)).build()
    );
//    public static final EntityType<ZombieProjectile> ZOMBIEPROJECTILE = Registry.register(
//            Registry.ENTITY_TYPE,
//            new Identifier("funmod","zombieprojectile"),
//            FabricEntityTypeBuilder.<ZombieProjectile>create(SpawnGroup.MISC,ZombieProjectile::new )
//            .dimensions(EntityDimensions.fixed(0.25f,0.25f))
//            .trackRangeBlocks(4).trackedUpdateRate(10)
//            .build()
//    );



    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("funmod","wand"),WAND);
        Registry.register(Registry.ITEM, new Identifier("funmod", "healthy_soup"), HEALTHY_SOUP);
        Registry.register(Registry.ITEM, new Identifier("funmod", "healthy_juice"), HEALTHY_JUICE);
        FabricDefaultAttributeRegistry.register(NEWZOMBIE, NewZombieEntity.createZombieAttributes());
        FabricDefaultAttributeRegistry.register(ROCKZOMBIE, RockZombie.createZombieAttributes());
    FabricDefaultAttributeRegistry.register(DIAMONDZOMBIE, DiamondZombie.createZombieAttributes());
    
        //BuiltinRegistries.BIOME.get(BiomeKeys.PLAINS).getSpawnSettings().getSpawnEntry(SpawnGroup.MONSTER).add(new SpawnSettings.SpawnEntry(NEWZOMBIE, 100, 2, 5));
        //Biomes.PLAINS.getEntitySpawnList(EntityCategory.Monster).add(new Biome.SpawnEntry(NEWZOMBIE, 100, 2, 5));

    }
}
