package com.me.funmod.spells;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SpellFactory {
    public SpellFactory() {};

    static private final HashMap<SpellItem, Spell> spellItemToSpell = new HashMap<SpellItem, Spell>();
    static private final HashMap<Spell, SpellItem> spellToSpellItem = new HashMap<Spell, SpellItem>();
    public static final List<SpellItem> SPELL_ITEMS = new ArrayList<SpellItem>(13);
    static private Spell defaultSpell;
    static private SpellItem defaultSpellItem;
    @Nullable
    static private StatusEffectInstance status = null;
    static private StatusEffectInstance poison1 = new StatusEffectInstance(StatusEffects.POISON, 200, 1);
    static private List<Spell> spells = new ArrayList<Spell> (Arrays.asList(
            new Spell("CastOnSelf",
                    Spell.MovementType.CastOnSelf,
                    Spell.BlockCollisionType.None,
                    Spell.EntityCollisionType.None,
                    0.0f,
                    0,
                    0,
                    1,
                     null),
            new Spell("ArcProjectile",
                    Spell.MovementType.Arc,
                    Spell.BlockCollisionType.Die,
                    Spell.EntityCollisionType.Die,
                    0.5f,
                    10,
                    0,
                     80,
                    null),
            new Spell("StraightProjectile",
                    Spell.MovementType.Straight,
                    Spell.BlockCollisionType.Die,
                    Spell.EntityCollisionType.Die,
                    1.0f,
                    10,
                    0,
                    40 ,
                    null),
            new Spell("DamageEntities",
                    Spell.MovementType.None,
                    Spell.BlockCollisionType.None,
                    Spell.EntityCollisionType.Damage,
                    0,
                    0,
                    0,
                    0 ,
                    null),
            new Spell("BounceOffBlocks",
                    Spell.MovementType.None,
                    Spell.BlockCollisionType.Bounce,
                    Spell.EntityCollisionType.None,
                    0,
                    0,
                    0,
                    0,
                    null),
            new Spell("DestroyBlocks",
                    Spell.MovementType.None,
                    Spell.BlockCollisionType.Destroy,
                    Spell.EntityCollisionType.None,
                    0,
                    0,
                    0,
                    0 ,
                    null),
            new Spell("DeathRayProjectile",
                    Spell.MovementType.Line,
                    Spell.BlockCollisionType.Die,
                    Spell.EntityCollisionType.Die,
                    1.5f * 10.0f,
                    5,
                    0,
                    2 ,
                    null),
            new Spell("Speed2",
                    Spell.MovementType.None,
                    Spell.BlockCollisionType.None,
                    Spell.EntityCollisionType.None,
                    1.0f,
                    0,
                    0,
                    0,
                    null),
            new Spell("MoreDamage",
                    Spell.MovementType.None,
                    Spell.BlockCollisionType.None,
                    Spell.EntityCollisionType.None,
                    0.0f,
                    30,
                    0,
                    0 ,
                    null),
            new Spell("LessDamage",
                    Spell.MovementType.None,
                    Spell.BlockCollisionType.None,
                    Spell.EntityCollisionType.None,
                    0.0f,
                    -30,
                    0,
                    0 ,
                    null),
            new Spell("LiveLonger",
                    Spell.MovementType.None,
                    Spell.BlockCollisionType.None,
                    Spell.EntityCollisionType.None,
                    0.0f,
                    0,
                    0,
                    30,
                    null),
            new Spell("Fire",
                    Spell.MovementType.None,
                    Spell.BlockCollisionType.None,
                    Spell.EntityCollisionType.None,
                    0.0f,
                    0,
                    10,
                    0 ,
                    null),
            new Spell("TeleportOnHit",
                    Spell.MovementType.None,
                    Spell.BlockCollisionType.None,
                    Spell.EntityCollisionType.Swap,
                    0.0f,
                    0,
                    0,
                    0 ,
                    null),

            new Spell("poison",
                      Spell.MovementType.None,
                    Spell.BlockCollisionType.None,
                    Spell.EntityCollisionType.PotionEffect,
                    0.0f,
                    0,
                    0,
                    0,
                    new StatusEffectInstance(StatusEffects.POISON, 200, 0)),
            new Spell("float",
                    Spell.MovementType.None,
                    Spell.BlockCollisionType.None,
                    Spell.EntityCollisionType.PotionEffect,
                    0.0f,
                    0,
                    0,
                    0,
                    new StatusEffectInstance(StatusEffects.SLOW_FALLING, 100, 10)),
            new Spell("bigfloat",
                    Spell.MovementType.None,
                    Spell.BlockCollisionType.None,
                    Spell.EntityCollisionType.PotionEffect,
                    0.0f,
                    0,
                    0,
                    0,
                    new StatusEffectInstance(StatusEffects.LEVITATION, 15, 2)),
            new Spell("dash",
                    Spell.MovementType.None,
                    Spell.BlockCollisionType.None,
                    Spell.EntityCollisionType.PotionEffect,
                    0.0f,
                    0,
                    0,
                    0,
                    new StatusEffectInstance(StatusEffects.SPEED, 8, 50)),
            new Spell("Blast",
                    Spell.MovementType.None,
                    Spell.BlockCollisionType.Blast,
                    Spell.EntityCollisionType.Blast,
                    0.0f,
                    0,
                    0,
                    0 ,
                    null),
            new Spell("Anvil",
                    Spell.MovementType.None,
                    Spell.BlockCollisionType.Blast,
                    Spell.EntityCollisionType.Blast,
                    0.0f,
                    0,
                    0,
                    0 ,
                    null)
            ));




    static public void initSpells() {
        for(Spell spell : spells) {
            SpellItem item = new SpellItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(64));
            SPELL_ITEMS.add(item);
            Registry.register(Registry.ITEM, new Identifier("funmod", String.format("spell_%s", spell.name.toLowerCase())),
                    item);
            spellToSpellItem.put(spell, item);
            spellItemToSpell.put(item, spell);
        }
        // Create the spells that go along with each item

        defaultSpellItem = SPELL_ITEMS.get(0);
        defaultSpell = spells.get(0);
    }

    public static Spell spellFromSpellItem(SpellItem item) {
        return spellItemToSpell.getOrDefault(item, defaultSpell);
    }

    public static SpellItem spellItemFromSpell(Spell spell) {
        int index = spells.indexOf(spell);
        if (index == -1) {
            return defaultSpellItem;
        }
        return SPELL_ITEMS.get(index);
    }
    public static SpellItem getSpellItem(int i) {
        return SPELL_ITEMS.get(i);
    }
    public static Spell getSpell(int i) {
        return spells.get(i);
    }

}
