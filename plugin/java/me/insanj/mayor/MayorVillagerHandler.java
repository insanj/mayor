package me.insanj.mayor;

import java.util.Arrays;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.Location;

// https://bukkit.org/threads/class-custom-vilagers-trade-items.338739/
class MayorVillagerHandler {
  private final String MAYOR_VILLAGER_DISPLAY_NAME = "Mayor";
  private final MayorPlugin plugin;

  public MayorVillagerHandler(MayorPlugin plugin) {
    this.plugin = plugin;
  }

  private ItemStack tradeInputItem() {
    return new ItemStack(Material.COBBLESTONE, 64);
  }

  private ItemStack tradeOutputItem() {
    ItemStack tradeOutputItem = new ItemStack(Material.POPPY, 1);
    ItemMeta itemMeta = tradeOutputItem.getItemMeta();
    itemMeta.setDisplayName(ChatColor.GREEN + "Thank You Note");
    itemMeta.setLore(Arrays.asList(ChatColor.GOLD + "Your building is on its way :)"));
    tradeOutputItem.setItemMeta(itemMeta);
    return tradeOutputItem;
  }

  public boolean spawnVillager(Location location) {
    Villager villager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
    villager.setCustomName(MAYOR_VILLAGER_DISPLAY_NAME);

    MayorVillager baseVillager = new MayorVillager(plugin, villager);
    MayorVillager builtVillager = baseVillager.addRecipe(tradeInputItem(), tradeOutputItem());
    boolean spawnSuccess = builtVillager.finish();
    return spawnSuccess;
  }
}
