package com.me.funmod.wandstation;

import com.me.funmod.general.ImplementedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class WandStationInventory implements ImplementedInventory {
    private DefaultedList<ItemStack> items;
    public WandStationInventory(int inventorySize) {
        items = DefaultedList.ofSize(inventorySize, ItemStack.EMPTY);
    }

    public DefaultedList<ItemStack> getItems() {
        return items;
    }
    public void setItems(DefaultedList<ItemStack> items) {
        this.items = items;
    }
}
