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

import net.minecraft.server.v1_13_R2.DefinedStructure;

import org.apache.commons.lang.exception.ExceptionUtils;

public class MayorPlugin extends JavaPlugin {
    public MayorConfig config = new MayorConfig(this);
    public MayorSchematicHandler schematicHandler = new MayorSchematicHandler(this);
    public MayorCommandExecutor executor;
    public HashMap<String, MayorSchematic> schematics;
    public HashMap<String, DefinedStructure> structures;

    @Override
    public void onEnable() {
      // (1) load all schematics from disk
      schematics = readSchematics();
      getLogger().info("-> finished reading .schematics! " + schematics.toString());

      // (2) load all structures from
      structures = readStructures();
      getLogger().info("-> finished reading structure .nbts! " + structures.toString());

      // (3) testing commands to generate schematics/structures
      executor = new MayorCommandExecutor(schematicHandler, schematics, structures);
      getCommand("mayor").setExecutor(executor);
    }

    private HashMap<String, MayorSchematic> readSchematics() {
      ArrayList<File> schematicFiles = config.getSchematicFiles();

      // loop thru and parse each NBT/.schematic file
      final String schematicFolderPath = MayorConfig.getSchematicsFolderPath(this);
      HashMap<String, MayorSchematic> readSchematics = new HashMap<String, MayorSchematic>();
      for (File file : schematicFiles) {
          String fileName = file.getName();
          File schematicFile = config.readFile(schematicFolderPath, fileName);
          MayorSchematic readFile = schematicHandler.readSchematicFile(fileName, file);
          readSchematics.put(fileName, readFile);
      }

      return readSchematics;
    }

    private HashMap<String, DefinedStructure> readStructures() {
      ArrayList<File> structureFiles = config.getStructureFiles();

      // loop thru and parse each NBT/structure file
      final String structuresFolderPath = MayorConfig.getStructuresFolderPath(this);
      HashMap<String, DefinedStructure> readStructures = new HashMap<String, DefinedStructure>();
      for (File file : structureFiles) {
          String fileName = file.getName();
          File structureFile = config.readFile(structuresFolderPath, fileName);
          try {
            DefinedStructure readStructure = MayorStructureHandler.loadSingleStructure(structureFile);
            readStructures.put(fileName, readStructure);
          } catch (Exception e) {
            String errorMessage = ExceptionUtils.getStackTrace(e);
            getLogger().info(String.format(":( error reading structure file %s: %s", fileName, errorMessage));
          }
      }

      return readStructures;
    }
}
