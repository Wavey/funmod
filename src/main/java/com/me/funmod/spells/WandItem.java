package com.me.funmod.spells;

import com.mojang.datafixers.Typed;
import com.sun.jna.StringArray;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
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


        List<Spell> spells = WandItem.getOrCreateSpells(itemStack, true);
        if(spells.isEmpty()) {
            System.out.println("Tried to fire an empty wand");
            return TypedActionResult.pass(itemStack);
        }


        System.out.println("Casting: " + getSpellDebugNames(spells));
        //spells.get(0).doTheThing(world,user);
        Spell.spawnSpells(world, user, spells);
        return TypedActionResult.pass(itemStack);

    }

    private static String getSpellDebugNames(List<Spell> spells) {
        if(spells.isEmpty()) {
            return "Empty";
        }

        ArrayList<String> spellNames = new ArrayList<String>(spells.size());
        for(Spell s : spells) {
            spellNames.add(s.getName());
        }
        return String.join(" - ", spellNames);
    }

    public static List<Spell> getOrCreateSpells(ItemStack wandStack, boolean createIfNeeded) {
        ArrayList<Spell> spells = new ArrayList<Spell>();
        DefaultedList<ItemStack> items = getOrCreateInventory(wandStack, createIfNeeded);
        for(ItemStack itemStack : items) {
            Item item = itemStack.getItem();
            if(item instanceof SpellItem) {
                SpellItem spellItem = (SpellItem) item;
                Spell spell = SpellFactory.spellFromSpellItem(spellItem);
                spells.add(spell);
            }
        }

        return spells;

    }
    public static DefaultedList<ItemStack> getOrCreateInventory(ItemStack wandStack, boolean createIfNeeded) {
        CompoundTag inventoryTag = getOrCreateInventoryTag((wandStack), createIfNeeded);
        if(inventoryTag.isEmpty()) {
            return DefaultedList.ofSize(0, ItemStack.EMPTY);
        }
        int size = inventoryTag.getInt("size");
        DefaultedList<ItemStack> items = DefaultedList.ofSize(size, ItemStack.EMPTY);
        Inventories.fromTag(inventoryTag.getCompound("items"), items);
        return items;
    }

    public static CompoundTag getOrCreateInventoryTag(ItemStack wandStack, boolean createIfNeeded) {
        CompoundTag tag = wandStack.getOrCreateSubTag("Inventory");
        if(tag.isEmpty() && createIfNeeded) {
            DefaultedList<ItemStack> items = DefaultedList.ofSize(random.nextInt(2) + 3, ItemStack.EMPTY );
            for(int i = 0;i < items.size(); ++i) {
                Item spellItem = SpellFactory.getSpellItem(random.nextInt(5));
                ItemStack spellItemStack = new ItemStack(spellItem);
                items.set(i, spellItemStack);
            }
            CompoundTag inventoryTag = new CompoundTag();
            Inventories.toTag(inventoryTag, items, true);
            tag.put("items", inventoryTag);
            tag.putInt("size", items.size());
        }
        return tag;

    }

    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        List<Spell> spells = WandItem.getOrCreateSpells(stack, false);
        tooltip.add(new LiteralText(getSpellDebugNames(spells)));
    }

}
