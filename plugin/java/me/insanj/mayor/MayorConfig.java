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
    public MayorConfig(MayorPlugin plugin) {
        this.plugin = plugin;
    }

    public ArrayList<File> getSchematicFiles() {
        File pluginDataFolder = new File(plugin.getDataFolder() + "/");
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
}
