package com.me.funmod;

import com.me.funmod.NetherGuy.NetherGuy;
import com.me.funmod.diamondzombie.DiamondZombie;
import com.me.funmod.hunterIlliger.Hunterilliger;
import com.me.funmod.projectiles.ZombieProjectile;
import com.me.funmod.rockzombie.RockZombie;
import com.me.funmod.spells.SpellFactory;
import com.me.funmod.spells.SpellItem;
import com.me.funmod.spells.SpellProjectileEntity;
import com.me.funmod.spells.WandItem;
import com.me.funmod.wandstation.WandStationBlock;
import com.me.funmod.wandstation.WandStationBlockEntity;
import com.me.funmod.wandstation.WandStationGuiDescription;
import com.me.funmod.zombie.NewZombieEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FunMod implements ModInitializer {
    public static final String ModID = "funmod"; // This is just so we can refer to our ModID easier.
    public static final Identifier PacketID = new Identifier(FunMod.ModID, "spawn_packet");
    public static final Item WAND = new WandItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1));
    public static final Item HEALTHY_SOUP = new Item(new FabricItemSettings().group(ItemGroup.FOOD).maxCount(1).food(new FoodComponent.Builder().hunger(0).saturationModifier(0f).alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.HUNGER,20*30),1).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,20*4),0.7f).statusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS,20*20),1).statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,20*10),1).build()));
    public static final Item HEALTHY_JUICE = new Item(new FabricItemSettings().group(ItemGroup.FOOD).maxCount(1).food(new FoodComponent.Builder().hunger(0).saturationModifier(0f).alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.HUNGER,20*20),1).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,20*4),1).statusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS,20*10),1).build()));
    public static final Item SPELL = new SpellItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1));
    public static final Item ZOMBIEPROJECTILEITEM = new Item(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1));
    public static final Block WANDSTATION_BLOCK = new WandStationBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f));
    public static BlockEntityType<WandStationBlockEntity> WANDSTATION_BLOCK_ENTITY;
    public static ScreenHandlerType<WandStationGuiDescription> SCREEN_HANDLER_TYPE;


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
    public static final EntityType<SpellProjectileEntity> SPELLPROJECTILEENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("funmod", "spellprojectile"),
            FabricEntityTypeBuilder.<SpellProjectileEntity>create(SpawnGroup.MISC, SpellProjectileEntity::new).dimensions(
                    EntityDimensions.fixed(1.0f, 1.0f)).build()
    );
    public static final EntityType<ZombieProjectile> ZOMBIEPROJECTILE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("funmod", "zombieprojectile"),
            FabricEntityTypeBuilder.<ZombieProjectile>create(SpawnGroup.MISC, ZombieProjectile::new).dimensions(
                    EntityDimensions.fixed(1.0f, 1.0f)).build()
    );
    public static final EntityType<Hunterilliger> HUNTERILLIGER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("funmod", "hunterilliger"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, Hunterilliger::new).dimensions(
                    EntityDimensions.fixed(1.0f, 2.0f)).build()
            );
    public  static final EntityType<NetherGuy> NETHERGUY = Registry.register(
            Registry.ENTITY_TYPE,
             new Identifier("funmod", "netherguy"),
         FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, NetherGuy::new).dimensions(
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
        Registry.register(Registry.ITEM, new Identifier("funmod","spell"),SPELL);
        Registry.register(Registry.ITEM, new Identifier("funmod","zombieprojectile"),ZOMBIEPROJECTILEITEM);
        Registry.register(Registry.ITEM, new Identifier("funmod", "healthy_soup"), HEALTHY_SOUP);
        Registry.register(Registry.ITEM, new Identifier("funmod", "healthy_juice"), HEALTHY_JUICE);

        Registry.register(Registry.BLOCK, new Identifier("funmod", "wandstation_block"), WANDSTATION_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("funmod", "wandstation_block"), new BlockItem(WANDSTATION_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));
        WANDSTATION_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "funmod:wandstation", BlockEntityType.Builder.create(WandStationBlockEntity::new, WANDSTATION_BLOCK).build(null));
        SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerSimple(new Identifier("funmod", "wandstation_block"),
                (syncId, inventory) -> new WandStationGuiDescription(syncId, inventory, ScreenHandlerContext.EMPTY));
        FabricDefaultAttributeRegistry.register(NEWZOMBIE, NewZombieEntity.createZombieAttributes());
        FabricDefaultAttributeRegistry.register(ROCKZOMBIE, RockZombie.createZombieAttributes());
        FabricDefaultAttributeRegistry.register(HUNTERILLIGER, Hunterilliger.createHostileAttributes());
        FabricDefaultAttributeRegistry.register(DIAMONDZOMBIE, DiamondZombie.createZombieAttributes());
        FabricDefaultAttributeRegistry.register(NETHERGUY, NetherGuy.createNetherGuyAttributes());

        SpellFactory.initSpells();

        //BuiltinRegistries.BIOME.get(BiomeKeys.PLAINS).getSpawnSettings().getSpawnEntry(SpawnGroup.MONSTER).add(new SpawnSettings.SpawnEntry(NEWZOMBIE, 100, 2, 5));
        //Biomes.PLAINS.getEntitySpawnList(EntityCategory.Monster).add(new Biome.SpawnEntry(NEWZOMBIE, 100, 2, 5));

    }
}
