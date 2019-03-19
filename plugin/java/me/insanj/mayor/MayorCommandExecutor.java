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

    public MayorCommandExecutor(MayorSchematicHandler handler, HashMap<String, MayorSchematic> schematics, HashMap<String, DefinedStructure> structures) {
        this.handler = handler;
        this.schematics = schematics;
        this.structures = structures;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "No schematic name provided");
            return false;
        }

        else if (!(sender instanceof Player) || !sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "Only operator players can execute this command");
            return false;
        }

        Player player = (Player) sender;
        if (args[0].equals("structure")) {
          String structureName = args[1];
          DefinedStructure structure = structures.get(structureName);
          if (structure == null) {
            sender.sendMessage(ChatColor.RED + "No structure found with the name: " + structureName);
            return false;
          }

          Location location = player.getLocation();
          MayorStructureHandler.insertSingleStructure(structure, location, EnumBlockRotation.NONE);
          return true;
        }

        else if (schematics == null || schematics.size() <= 0) {
            sender.sendMessage(ChatColor.RED + "No schematics found in /plugins/mayor/schematics/");
            return false;
        }

        String schematicName = args[1];
        MayorSchematic schematic = schematics.get(schematicName);
        if (schematic == null) {
            sender.sendMessage(ChatColor.RED + "Could not find schematic with name: " + schematicName);
            return false;
        }

        Location lookingLocation = player.getTargetBlockExact(100, FluidCollisionMode.NEVER).getLocation(); // player.getLocation();
        if (lookingLocation == null) {
          sender.sendMessage(ChatColor.RED + "No target block found, make sure you're looking somewhere I can build!");
          return false;
        }

        handler.pasteSchematic(player.getWorld(), lookingLocation, schematic);
        return true;
    }
}
