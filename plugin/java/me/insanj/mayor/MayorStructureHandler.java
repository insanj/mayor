package me.insanj.mayor;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.Random;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Location;
import org.bukkit.World;

import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;

import net.minecraft.server.v1_13_R2.Block;
import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.ChunkCoordIntPair;
import net.minecraft.server.v1_13_R2.DefinedStructure;
import net.minecraft.server.v1_13_R2.DefinedStructureManager;
import net.minecraft.server.v1_13_R2.DefinedStructureInfo;
import net.minecraft.server.v1_13_R2.EnumBlockMirror;
import net.minecraft.server.v1_13_R2.EnumBlockRotation;
import net.minecraft.server.v1_13_R2.WorldServer;
import net.minecraft.server.v1_13_R2.DefinedStructure;
import net.minecraft.server.v1_13_R2.NBTCompressedStreamTools;
import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.Blocks;
import net.minecraft.server.v1_13_R2.DefinedStructure;
import net.minecraft.server.v1_13_R2.WorldServer;
import net.minecraft.server.v1_13_R2.DefinedStructure;
import net.minecraft.server.v1_13_R2.NBTCompressedStreamTools;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.EnumBlockRotation;

// https://www.spigotmc.org/threads/tutorial-example-handle-structures.331243/
class MayorStructureHandler {
  /**
  * Loads a single structure NBT file and creates a new structure object instance. Works only for structures created with 1.13 or later!
  * @param source - The structure file
  * @return DefinedStructure - The new instance
  */
  public static DefinedStructure loadSingleStructure(File source) throws FileNotFoundException, IOException {
    if (source == null) {
      throw new FileNotFoundException("Mayor cannot load structure when source file providing as parameter is null itself :(");
    }

    DefinedStructure structure = new DefinedStructure();
    structure.b(NBTCompressedStreamTools.a(new FileInputStream(source)));
    return structure;
  }

  /**
    * Loads a single structure NBT file and creates a new structure object instance. Also converts pre1.13 versions.
    * @param source - The structure file
    * @param world - The world (actually ANY world) instance to receive a data fixer (legacy converter)
    * @return DefinedStructure - The new instance
    * @deprecated Only for pre1.13, uses the NMS 1.13 DataFixer to convert stuff
    */
  /*@Deprecated
  public static DefinedStructure loadLegacySingleStructure(File source, World world) throws Exception {

    // FileNotFoundException, IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException

      Method parseAndConvert = DefinedStructureManager.class.getDeclaredMethod("a", InputStream.class);
      parseAndConvert.setAccessible(true);
      return (DefinedStructure) parseAndConvert.invoke(((CraftWorld) world).getHandle().C(), new FileInputStream(source));
  }*/

  /**
 * Creates a single structure of maximum 32x32x32 blocks.
  * @param corners - The edges of the are (order doesn't matter)
  * @param author - The listed author of the structure
  * @return DefinedStructure - The new structure instance
  */
  public static DefinedStructure createSingleStructure(Location[] corners, String author) {
      if (corners.length != 2) throw new IllegalArgumentException("An area needs to be set up by exactly 2 opposite edges!");
      Location[] normalized = MayorStructureHandler.normalizeEdges(corners[0], corners[1]); // find this method at the end of the tutorial
      // ^^ This is juggling the coordinates, so the first is the Corner with lowest x, y, z and the second has the highest x, y, z.
      WorldServer world = ((CraftWorld) normalized[0].getWorld()).getHandle();
      int[] dimensions = MayorStructureHandler.getDimensions(normalized); // find this method at the end of the tutorial
      if (dimensions[0] > 32 || dimensions[1] > 32 || dimensions[2] > 32) throw new IllegalArgumentException("A single structure can only be 32x32x32!");
      DefinedStructure structure = new DefinedStructure();
      structure.a(world, new BlockPosition(normalized[0].getBlockX(), normalized[0].getBlockY(), normalized[0].getBlockZ()), new BlockPosition(dimensions[0], dimensions[1], dimensions[2]), true, Blocks.STRUCTURE_VOID);
      structure.a(author); // may not be saved to file anymore since 1.13
      return structure;
  }

  /**
  * Saves a structure NBT file to a given destination file
  * @param structure - The structure to be saved
  * @param destination - The NBT file to be created
  */
  public static void saveSingleStructure(DefinedStructure structure, File destination) throws FileNotFoundException, IOException {
      NBTTagCompound fileTag = new NBTTagCompound();
      fileTag = structure.a(fileTag);
      NBTCompressedStreamTools.a(fileTag, new FileOutputStream(new File(destination + ".nbt")));
  }


  /**
    * Pastes a single structure into the world
    * @param structure - The structure to be pasted
    * @param startEdge - The starting corner with the lowest x, y, z coordinates
    * @param rotation - You may rotate the structure by 90 degrees steps
    */
  public static void insertSingleStructure(DefinedStructure structure, Location startEdge, EnumBlockRotation rotation) {
      WorldServer world = ((CraftWorld) startEdge.getWorld()).getHandle();
      DefinedStructureInfo structInfo = new DefinedStructureInfo().a(EnumBlockMirror.NONE).a(rotation).a(false).a((ChunkCoordIntPair) null).a((Block) null).c(false).a(1.0f).a(new Random());
      // false sets ignore entities to false (so it does NOT ignore them)
      // 1.0f sets the amout of to be pasted blocks to 100%
      // mirror & rortation are self explaining
      // the block does the thing like the block does, which can be set in the ingame structure block GUI
      // no idea at the moment, what the coord pair, the random and the second false does
      // If you want to find out more about it, see net.minecraft.server.v1_13_R2.TileEntityStructure.c(boolean), there mojang calls the method and compare it with the ingame GUI
      structure.a(world, new BlockPosition(startEdge.getBlockX(), startEdge.getBlockY(), startEdge.getBlockZ()), structInfo);
  }

  /**
    * Get the amount of blocks along x axis (width), y axis (height), z axis (length)
    * @param corners - The 2 opposite edges, in best case the first has the lowest coordinates in x, y, z
    * @return int[3] - Width, height, length
    */
  public static int[] getDimensions(Location[] corners) {
      if (corners.length != 2) throw new IllegalArgumentException("An area needs to be set up by exactly 2 opposite edges!");
      return new int[] { corners[1].getBlockX() - corners[0].getBlockX() + 1, corners[1].getBlockY() - corners[0].getBlockY() + 1, corners[1].getBlockZ() - corners[0].getBlockZ() + 1 };
  }

  /**
    * Swaps the edge corners if necessary, so the first edge will be at the lowest coordinates and the highest will be at the edge with the highest coordinates
    * @param startBlock - Any corner
    * @param endBlock - The other corner
    * @return Location[2] array - [0] = lowest edge, [1] = highest edge
    */
  public static Location[] normalizeEdges(Location startBlock, Location endBlock) {
      int xMin, xMax, yMin, yMax, zMin, zMax;
      if (startBlock.getBlockX() <= endBlock.getBlockX()) {
          xMin = startBlock.getBlockX();
          xMax = endBlock.getBlockX();
      } else {
          xMin = endBlock.getBlockX();
          xMax = startBlock.getBlockX();
      }
      if (startBlock.getBlockY() <= endBlock.getBlockY()) {
          yMin = startBlock.getBlockY();
          yMax = endBlock.getBlockY();
      } else {
          yMin = endBlock.getBlockY();
          yMax = startBlock.getBlockY();
      }
      if (startBlock.getBlockZ() <= endBlock.getBlockZ()) {
          zMin = startBlock.getBlockZ();
          zMax = endBlock.getBlockZ();
      } else {
          zMin = endBlock.getBlockZ();
          zMax = startBlock.getBlockZ();
      }
      return new Location[] { new Location(startBlock.getWorld(), xMin, yMin, zMin), new Location(startBlock.getWorld(), xMax, yMax, zMax) };
  }

}
