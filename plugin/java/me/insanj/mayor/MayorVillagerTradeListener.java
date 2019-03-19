package me.insanj.mayor;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.VillagerReplenishTradeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.ChatColor;

class MayorVillagerTradeListener implements Listener {
  private final MayorPlugin plugin;

  public MayorVillagerTradeListener(MayorPlugin plugin) {
    this.plugin = plugin;
  }


  @EventHandler
  public void onVillagerReplenishTrade(VillagerReplenishTradeEvent event) {
    plugin.getLogger().info("VillagerReplenishTradeEvent");
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

  @EventHandler
  public void onVillagerTrade(InventoryOpenEvent event) {
    if ((event.getInventory() != null) && (event.getInventory().getType() == InventoryType.MERCHANT)) {
      MerchantRecipe villagerTradeMeta = (MerchantRecipe) event.getInventory();
      if (villagerTradeMeta.getResult().getType() == Material.POPPY) {
         plugin.getServer().broadcastMessage(ChatColor.GREEN + event.getPlayer().getName() + " & the Mayor have agreed to build a new structure!");
      }
    }
 }

/*
  @EventHandler
  public void onInventoryInteract(InventoryInteractEvent event) {
    plugin.getLogger().info("InventoryInteractEvent");

  */
}
