package me.insanj.contractors;

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

public class Contractors extends JavaPlugin implements CommandExecutor {
    public ContractorsConfig config = new ContractorsConfig(this);
    public ContractorsSchematicHandler handler = new ContractorsSchematicHandler(this);
    public HashMap<String, ContractorsSchematic> schematics;
    
    @Override
    public void onEnable() {
        // (1) load all schematics from disk
        ArrayList<File> schematicFiles = config.getSchematicFiles();
       
        // (2) loop thru and parse each NBT/.schematic file
        HashMap<String, ContractorsSchematic> readSchematics = new HashMap<String, ContractorsSchematic>();
        for (File file : schematicFiles) {
            String fileName = file.getName();
            ContractorsSchematic readFile = handler.readSchematicFile(fileName, file);
            readSchematics.put(fileName, readFile);
        }

        // (3) print out all the files so we can see what we got
        System.out.println(readSchematics.toString());
        schematics = readSchematics;

        // (4) testing commands to generate schematics/structures
        getCommand("contractors").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "No schematic name provided");
            return false;
        }
        
        else if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command");
            return false;
        }

        else if (schematics == null || schematics.size() <= 0) {
            sender.sendMessage(ChatColor.RED + "No schematics found in /plugins/contractors/");
            return false;
        }

        Player player = (Player) sender;
        ContractorsSchematic schematic = schematics.get(args[0]);
        if (schematic == null) {
            sender.sendMessage(ChatColor.RED + "Could not find schematic with name: " + args[0]);
            return false;
        }

        handler.pasteSchematic(player.getWorld(), player.getLocation(), schematic);
        return true;
    }
}