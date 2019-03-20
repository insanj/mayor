package me.insanj.mayor;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.Math;
import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.FluidCollisionMode;

import net.minecraft.server.v1_13_R2.EnumBlockRotation;
import net.minecraft.server.v1_13_R2.DefinedStructure;

class MayorCommandExecutor implements CommandExecutor {
  public final MayorSchematicHandler handler;
  public final HashMap<String, MayorSchematic> schematics;
  public final HashMap<String, DefinedStructure> structures;
  private final MayorVillagerHandler villagerHandler;

  public MayorCommandExecutor(MayorSchematicHandler handler, HashMap<String, MayorSchematic> schematics, HashMap<String, DefinedStructure> structures, MayorVillagerHandler villagerHandler) {
    this.handler = handler;
    this.schematics = schematics;
    this.structures = structures;
    this.villagerHandler = villagerHandler;
  }

  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player) || !sender.isOp()) {
        sender.sendMessage(ChatColor.RED + "Only operator players can execute this command :(");
        return false;
    }

    Player player = (Player) sender;
    Location target = player.getLocation();

    if (args.length <= 0) {
      villagerHandler.spawnVillager(target);
      sender.sendMessage(ChatColor.GREEN + "Spawned Mayor villager to your location!");
      return true;
    }

    else if (args.length != 1) {
      sender.sendMessage(ChatColor.RED + "No schematic or structure name provided!");
      return false;
    }

    String argumentString = args[0];
     // getTargetBlockExact(100, FluidCollisionMode.NEVER).getLocation();

    if (target == null) {
      sender.sendMessage(ChatColor.RED + "No target block found, make sure you're looking somewhere I can build!");
      return false;
    }

    sender.sendMessage(ChatColor.BLUE + "Generating " + argumentString + "...");

    if (argumentString.indexOf(".nbt") >= 0) {
      if (structures == null || structures.size() <= 0) {
          sender.sendMessage(ChatColor.RED + "No structures found in /plugins/mayor/structures/");
          return false;
      }

      String structureName = argumentString;
      DefinedStructure structure = structures.get(structureName);
      if (structure == null) {
        sender.sendMessage(ChatColor.RED + "No structure found with the name: " + structureName);
        return false;
      }

      MayorStructureHandler.insertSingleStructure((DefinedStructure)structure, target, EnumBlockRotation.NONE);
      sender.sendMessage(ChatColor.GREEN + String.format("Done building structure!"));
      return true;
    }

    else if (argumentString.indexOf(".schematic") >= 0) {
      if (schematics == null || schematics.size() <= 0) {
          sender.sendMessage(ChatColor.RED + "No schematics found in /plugins/mayor/schematics/");
          return false;
      }

      String schematicName = argumentString;
      MayorSchematic schematic = schematics.get(schematicName);
      if (schematic == null) {
          sender.sendMessage(ChatColor.RED + "Could not find schematic with name: " + schematicName);
          return false;
      }

      handler.pasteSchematic(player.getWorld(), target, schematic);
      sender.sendMessage(ChatColor.GREEN + String.format("Done building schematic!"));
      return true;
    }

    sender.sendMessage(ChatColor.RED + "Provided file must end with .schematic or .nbt, these are the only formats current supported.");
    return false;
  }
}
