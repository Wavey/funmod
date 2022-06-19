package com.me.funmod.spells;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SpellFactory {
    public SpellFactory() {};

    static private final HashMap<SpellItem, Spell> spellItemToSpell = new HashMap<SpellItem, Spell>();
    static private final HashMap<Spell, SpellItem> spellToSpellItem = new HashMap<Spell, SpellItem>();
    public static final List<SpellItem> SPELL_ITEMS = new ArrayList<SpellItem>(5);
    static private Spell defaultSpell;
    static private SpellItem defaultSpellItem;
    static private List<Spell> spells = new ArrayList<Spell> (Arrays.asList(
            new Spell("ArcProjectile",
                    Spell.MovementType.Arc,
                    Spell.BlockCollisionType.Die,
                    Spell.EntityCollisionType.Die,
                    1.5f,
                    100,
                     80 ),
            new Spell("DamageEntities",
                    Spell.MovementType.None,
                    Spell.BlockCollisionType.None,
                    Spell.EntityCollisionType.Damage,
                    0,
                    0,
                    0 ),
            new Spell("BounceOffBlocks",
                    Spell.MovementType.None,
                    Spell.BlockCollisionType.Bounce,
                    Spell.EntityCollisionType.None,
                    0,
                    0,
                    0 ),
            new Spell("DestroyBlocks",
                    Spell.MovementType.None,
                    Spell.BlockCollisionType.Destroy,
                    Spell.EntityCollisionType.None,
                    0,
                    0,
                    0 ),
            new Spell("DeathRayProjectile",
                    Spell.MovementType.Line,
                    Spell.BlockCollisionType.Die,
                    Spell.EntityCollisionType.Die,
                    1.5f * 10.0f,
                    5,
                    1 ),
            new Spell("Speed2",
                    Spell.MovementType.None,
                    Spell.BlockCollisionType.None,
                    Spell.EntityCollisionType.None,
                    1.5f,
                    0,
                    0 ),
            new Spell("MoreDamage",
                    Spell.MovementType.None,
                    Spell.BlockCollisionType.None,
                    Spell.EntityCollisionType.None,
                    0.0f,
                    30,
                    0 ),
            new Spell("LessDamage",
                    Spell.MovementType.None,
                    Spell.BlockCollisionType.None,
                    Spell.EntityCollisionType.None,
                    0.0f,
                    -30,
                    0 ),
            new Spell("LiveLonger",
                    Spell.MovementType.None,
                    Spell.BlockCollisionType.None,
                    Spell.EntityCollisionType.None,
                    0.0f,
                    0,
                    30 ),
            new Spell("TeleportOnHit",
                    Spell.MovementType.None,
                    Spell.BlockCollisionType.None,
                    Spell.EntityCollisionType.Swap,
                    0.0f,
                    0,
                    0 )));


    static public void initSpells() {
        for(Spell spell : spells) {
            SpellItem item = new SpellItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1));
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
