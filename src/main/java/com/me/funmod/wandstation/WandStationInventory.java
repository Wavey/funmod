package com.me.funmod.wandstation;

import com.me.funmod.general.ImplementedInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;

public class WandStationInventory implements ImplementedInventory {
    private DefaultedList<ItemStack> items;
    private final NbtCompound inventoryTag;
    public WandStationInventory(NbtCompound inventoryTag) {
        this.inventoryTag = inventoryTag;
        int size = inventoryTag.getInt("size");
        this.items = DefaultedList.ofSize(size, ItemStack.EMPTY);
        Inventories.readNbt(inventoryTag.getCompound("items"), items);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void markDirty() {
        ImplementedInventory.super.markDirty();
        Inventories.writeNbt(inventoryTag.getCompound("items"), this.items);
    }
}
