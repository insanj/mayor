package me.insanj.contractors;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.Math;

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

public class Contractors extends JavaPlugin {
    public ContractorsConfig config = new ContractorsConfig(this);
    public ContractorsSchematicHandler handler = new ContractorsSchematicHandler(this);

    @Override
    public void onEnable() {
        // (1) load all schematics from disk
        List<String> schematicsPaths = config.getSchematicPaths();
       
        // (2) loop thru and parse each NBT/.schematic file
        HashMap<String, ContractorsSchematic> schematics = new HashMap<String, ContractorsSchematic>();
        for (String path : schematicsPaths) {
            ContractorsSchematic readFile = handler.readSchematicFile(path);
            schematics.put(path, readFile);
        }

        // (3) print out all the files so we can see what we got
        System.out.println(schematics.toString());
    }
}