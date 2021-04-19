package com.github.hazork.adventurepass.menu;

import org.bukkit.inventory.ItemStack;

public class ItemMenu {

  private int slot;
  private int page;
  private ItemStack item;

  public ItemMenu(int slot, int page, ItemStack item) {
    this.slot = slot;
    this.page = page;
    this.item = item;
  }

  public int getSlot() {
    return slot;
  }

  public int getPage() {
    return page;
  }

  public ItemStack getItem() {
    return item;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((item == null) ? 0 : item.hashCode());
    result = prime * result + page;
    result = prime * result + slot;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    ItemMenu other = (ItemMenu) obj;
    if (item == null) {
      if (other.item != null) return false;
    } else if (!item.equals(other.item)) return false;
    if (page != other.page) return false;
    if (slot != other.slot) return false;
    return true;
  }
}
