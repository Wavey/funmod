package com.me.funmod.spells;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.time.Clock;
import java.time.ZoneId;

public class WandItem extends Item {
    private static final Random random = new Random(Clock.systemDefaultZone().instant().getNano());
    private static final List<String> names = Arrays.asList( "Boom", "Fizzle", "Hmm", "KABOOOOM", "Bzzzzz", "Zap", "Slap", "Pow!");

    public WandItem( Item.Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        List<Spell> spells = WandItem.GetOrCreateSpells(itemStack, true);
        System.out.println("Casting: " + spells.get(0).getName() + " - " + spells.get(1).getName() + " - " + spells.get(2).getName());
        //spells.get(0).doTheThing(world,user);
        Spell.spawnSpells(world, user, spells);
        return TypedActionResult.pass(itemStack);

    }
    private static List<Spell>GetOrCreateSpells(ItemStack wandStack, boolean createIfNeeded) {

        ArrayList<Spell> spells = new ArrayList<>();
        List<CompoundTag> spellTags = getSpellsAsTags(wandStack, createIfNeeded);
        for(CompoundTag tag : spellTags) {
            spells.add(Spell.fromTag(tag));
        }

        return spells;

    }

    public static List<CompoundTag> getSpellsAsTags(ItemStack wandStack, boolean createIfNeeded) {
        ArrayList<CompoundTag> spells = new ArrayList<CompoundTag>();
        CompoundTag tag = wandStack.getOrCreateSubTag("Spells");
        if(tag.isEmpty()) {
            if(! createIfNeeded) {
                // don't create, just return
                return spells;
            }
            // we need to put in some spells, since we have not already
            // initialized this yet
            tag.put("spell1", SpellFactory.getSpell(random.nextInt(5)).toTag());
            tag.put("spell2", SpellFactory.getSpell(random.nextInt(5)).toTag());
            tag.put("spell3", SpellFactory.getSpell(random.nextInt(5)).toTag());
        }
        spells.add(tag.getCompound("spell1"));
        spells.add(tag.getCompound("spell2"));
        spells.add(tag.getCompound("spell3"));
        return spells;

    }

    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        List<Spell> spells = WandItem.GetOrCreateSpells(stack, false);
        if(!spells.isEmpty()) {
            tooltip.add(new LiteralText(spells.get(0).getName() + " - " + spells.get(1).getName() + " - " + spells.get(2).getName()));
        }
    }

}
