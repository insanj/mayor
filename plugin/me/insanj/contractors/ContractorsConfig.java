package me.insanj.contractors;

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

public class ContractorsConfig {
    public final String SCHEMATICS_PATH = "schematics";
    public final String USE_DEFAULT_SCHEMATICS_PATH = "use_default_schematics";
    public final String DEFAULT_SCHEMATIC_PATH = "default.schematic";
    
    private Contractors plugin;

    public ContractorsConfig(Contractors givenPlugin) {
        plugin = givenPlugin;
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
    }

    public List<String> getSchematicPaths() {
        List<String> configSchematics = getConfigSchematicPaths();
        List<String> combinedList = new ArrayList<String>(configSchematics);
        if (plugin.getConfig().getBoolean(USE_DEFAULT_SCHEMATICS_PATH) == true) {
            List<String> defaultSchematics = getDefaultSchematicPaths();
            combinedList.addAll(defaultSchematics);
        }

        return combinedList;
    }

    public List<String> getConfigSchematicPaths() {
        try {
            List<String> readConfigResult = (List<String>) plugin.getConfig().getList(SCHEMATICS_PATH);
            if (readConfigResult == null) {
                return new ArrayList<String>();
            }

            return readConfigResult;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }

    public List<String> getDefaultSchematicPaths() {
        return Arrays.asList(DEFAULT_SCHEMATIC_PATH);
    }
}