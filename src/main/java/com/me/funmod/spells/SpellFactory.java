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
            new Spell("Fire",
                    Spell.MovementType.Arc,
                    Spell.BlockCollisionType.Die,
                    Spell.EntityCollisionType.Damage,
                    1.5f,
                    100,
                     80 ),
            new Spell("Ouchie",
                    Spell.MovementType.Arc,
                    Spell.BlockCollisionType.Bounce,
                    Spell.EntityCollisionType.Damage,
                    1.5f,
                    100,
                    80 ),
            new Spell("Destroyer",
                    Spell.MovementType.Arc,
                    Spell.BlockCollisionType.Destroy,
                    Spell.EntityCollisionType.Damage,
                    1.5f,
                    100,
                    80 ),
            new Spell("Death Ray",
                    Spell.MovementType.Line,
                    Spell.BlockCollisionType.Destroy,
                    Spell.EntityCollisionType.Damage,
                    1.5f,
                    1,
                    80 ),
            new Spell("Swapper",
                    Spell.MovementType.Arc,
                    Spell.BlockCollisionType.Bounce,
                    Spell.EntityCollisionType.Swap,
                    1.5f,
                    1,
                    80 )));


    static public void initSpells() {
        for(int i = 0; i < 5; i++) {
            SpellItem item = new SpellItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1));
            SPELL_ITEMS.add(item);
            Registry.register(Registry.ITEM, new Identifier("funmod", String.format("spell%d", i+1)),
                    SPELL_ITEMS.get(i));
            Spell spell = spells.get(i);
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
