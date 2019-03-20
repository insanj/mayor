package me.insanj.mayor;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.VillagerReplenishTradeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.MerchantRecipe;

import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftInventoryMerchant;

import net.minecraft.server.v1_13_R2.DefinedStructure;
import net.minecraft.server.v1_13_R2.InventoryMerchant;

class MayorVillagerTradeListener implements Listener {
  private final MayorPlugin plugin;
  private final HashMap<String, MayorSchematic> schematics;
  private final HashMap<String, DefinedStructure> structures;
  private final MayorBuildCoordinator buildCoordinator;

  public MayorVillagerTradeListener(MayorPlugin plugin, HashMap<String, MayorSchematic> schematics, HashMap<String, DefinedStructure> structures, MayorBuildCoordinator buildCoordinator) {
    this.plugin = plugin;
    this.schematics = schematics;
    this.structures = structures;
    this.buildCoordinator = buildCoordinator;
  }

  @EventHandler
  public void onVillagerReplenishTrade(VillagerReplenishTradeEvent event) {
    if (event.getEntity().getName().equals("Mayor")) {
      plugin.getServer().broadcastMessage(ChatColor.GREEN + "The Mayor has agreed to build a new structure!");
      event.getEntity().setCustomName(null);
      buildCoordinator.coordinate(event.getEntity().getLocation());
    }
  }

/*
  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    plugin.getLogger().info("InventoryClickEvent");

    try {
      Inventory inventory = event.getInventory();
      if (inventory != null && inventory.getType().equals(InventoryType.MERCHANT) && inventory instanceof CraftInventoryMerchant) {
        int rawSlot = event.getRawSlot();
        if (rawSlot >= 0 && rawSlot <= 2) { // Top villager slots
          MerchantInventory merchantInventory = (MerchantInventory)inventory;
          MerchantRecipe recipe = merchantInventory.getSelectedRecipe​();
          if (recipe != null) {
            int uses = recipe.getUses​();
            plugin.getLogger().info("uses " + Integer.toString(uses));
          }
        }

      }
    } catch (Exception e) {
      plugin.logError(e);
    }
  }*/

/*
  @EventHandler
  public void onVillagerTrade(InventoryOpenEvent event) {
    if ((event.getInventory() != null) && (event.getInventory().getType() == InventoryType.MERCHANT)) {
      CraftInventoryMerchant merchantInventory = (CraftInventoryMerchant) event.getInventory();
      InventoryMerchant inventory = merchantInventory.getInventory();
      MerchantRecipe recipe = inventory.getRecipe().asBukkit();
      if (recipe.getResult().getType() == Material.POPPY) {
         plugin.getServer().broadcastMessage(ChatColor.GREEN + event.getPlayer().getName() + " & the Mayor have agreed to build a new structure!");
      }
    }
 }*/

/*
  @EventHandler
  public void onInventoryInteract(InventoryInteractEvent event) {
    plugin.getLogger().info("InventoryInteractEvent");

  */
}
