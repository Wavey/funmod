package com.me.funmod.wandstation;

import com.me.funmod.FunMod;
import com.me.funmod.spells.Spell;
import com.me.funmod.spells.SpellFactory;
import com.me.funmod.spells.SpellItem;
import com.me.funmod.spells.WandItem;
import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.collection.DefaultedList;

import javax.swing.border.CompoundBorder;
import java.util.ArrayList;
import java.util.List;

public class WandStationGuiDescription extends SyncedGuiDescription {
    private static final int INVENTORY_SIZE=1;
    private WandStationInventory wandInventory = new WandStationInventory(3);
    private ItemStack wand;


    public WandStationGuiDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(FunMod.SCREEN_HANDLER_TYPE, syncId, playerInventory, getBlockInventory(context, INVENTORY_SIZE), getBlockPropertyDelegate(context));
        // Extract the wand
        ItemStack itemStack = playerInventory.getMainHandStack();
        if (itemStack.getItem() != FunMod.WAND) {
            return;
        }

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(300, 200);
        wand = itemStack;
        setupSpellInventory(root);

        WItemSlot wandSlot = WItemSlot.of(wandInventory, 0, wandInventory.size(), 1);
        root.add(wandSlot, 0, 1);

        root.add(this.createPlayerInventoryPanel(), 0, 3);

        root.validate(this);
    }
    private void setupSpellInventory(WGridPanel root) {
        List<CompoundTag> spells = WandItem.getSpellsAsTags(wand, true);
        wandInventory = new WandStationInventory(spells.size());
        DefaultedList<ItemStack> items = DefaultedList.ofSize(spells.size(), ItemStack.EMPTY);
        for(int i = 0;i < spells.size(); i++) {
            SpellItem spellItem = SpellFactory.spellItemFromSpell(Spell.fromTag(spells.get(i)));
            items.set(i, new ItemStack(spellItem, 1));
        }


        wandInventory.setItems(items);


    }
}
