package me.insanj.mayor;

import java.util.Map;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.bukkit.World;
import org.bukkit.Location;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class MayorConfig {
    private final MayorPlugin plugin;
    private static final String SCHEMATICS_PATH = "schematics";
    private static final String STRUCTURES_PATH = "structures";

    public MayorConfig(MayorPlugin plugin) {
        this.plugin = plugin;
    }

    public static String getSchematicsFolderPath(MayorPlugin plugin) {
        return String.format("%s/%s/", plugin.getDataFolder(), SCHEMATICS_PATH);
    }

    public ArrayList<File> getSchematicFiles() {
        String pluginDataFolderPath = MayorConfig.getSchematicsFolderPath(plugin);
        File pluginDataFolder = new File(pluginDataFolderPath);
        if (!pluginDataFolder.exists()) {
            pluginDataFolder.mkdir();
        }

        ArrayList<File> schematicFiles = new ArrayList<File>();
        File[] pluginFolderListing = pluginDataFolder.listFiles();
        for (File file : pluginFolderListing) {
            if (file.isFile()) {
                schematicFiles.add(file);
            }
        }
        return schematicFiles;
    }

    public static String getStructuresFolderPath(MayorPlugin plugin) {
        return String.format("%s/%s/", plugin.getDataFolder(), STRUCTURES_PATH);
    }

    public ArrayList<File> getStructureFiles() {
        String pluginDataFolderPath = MayorConfig.getStructuresFolderPath(plugin);
        File pluginDataFolder = new File(pluginDataFolderPath);
        if (!pluginDataFolder.exists()) {
            pluginDataFolder.mkdir();
        }

        ArrayList<File> structureFiles = new ArrayList<File>();
        File[] pluginFolderListing = pluginDataFolder.listFiles();
        for (File file : pluginFolderListing) {
            if (file.isFile()) {
                structureFiles.add(file);
            }
        }
        return structureFiles;
    }

    public File readFile(String folderPath, String name) {
        if (!name.endsWith(".schematic")) {
            name = name + ".schematic";
        }

        String filePath = folderPath + name;
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }

        return file;
    }
}
