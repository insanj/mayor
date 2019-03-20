package me.insanj.mayor;

import java.util.HashMap;

import org.bukkit.Location;

import net.minecraft.server.v1_13_R2.DefinedStructure;

public class MayorBuildCoordinator {
  private final MayorPlugin plugin;

  private final MayorSchematicBuilder schematicBuilder;
  private final MayorStructureBuilder structureBuilder;

  private final HashMap<String, MayorSchematic> schematics;
  private final HashMap<String, DefinedStructure> structures;

  public MayorBuildCoordinator(MayorPlugin plugin, MayorSchematicBuilder schematicBuilder, MayorStructureBuilder structureBuilder, HashMap<String, MayorSchematic> schematics, HashMap<String, DefinedStructure> structures) {
    this.plugin = plugin;
    this.schematicBuilder = schematicBuilder;
    this.structureBuilder = structureBuilder;
    this.schematics = schematics;
    this.structures = structures;
  }

  public void coordinate(Location location) {
    coordinate(location, false);
  }

  public void coordinate(Location location, boolean useSchematic) {
    if (useSchematic == true) {
      MayorSchematic schematic = schematics.get("tower.schematic");
      schematicBuilder.locateAndBuildSchematic(schematic, location);
    }

    else {
      DefinedStructure structure = structures.get("tree.nbt");
      structureBuilder.locateAndBuildStructure(structure, location);
    }
  }
}
