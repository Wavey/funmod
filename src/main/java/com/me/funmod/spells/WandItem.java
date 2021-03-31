package com.me.funmod.spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

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
        CompoundTag tag = itemStack.getOrCreateSubTag("Spells");
        if(tag.isEmpty()) {
            // we need to put in some spells, since we have not already
            // initialized this yet
            tag.put("spell1", new Spell(WandItem.names.get(random.nextInt(names.size() - 1))).toTag());
            tag.put("spell2", new Spell(WandItem.names.get(random.nextInt(names.size() - 1))).toTag());
            tag.put("spell3", new Spell(WandItem.names.get(random.nextInt(names.size() - 1))).toTag());
            System.out.println("Empty wand.. adding spells");
        }

        Spell spell1 = Spell.fromTag(tag.getCompound("spell1"));
        Spell spell2 = Spell.fromTag(tag.getCompound("spell2"));
        Spell spell3 = Spell.fromTag(tag.getCompound("spell3"));

        System.out.println("Casting: " + spell1.getName() + " - " + spell2.getName() + " - " + spell3.getName());

        return TypedActionResult.pass(itemStack);

    }
}
