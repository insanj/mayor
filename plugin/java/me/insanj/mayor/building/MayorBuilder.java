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
import net.minecraft.server.v1_13_R2.DefinedStructure;

public class MayorBuilder {
  class LocateConfig {
    private final int range;
    public LocateConfig(int range) {
      this.range = range;
    }
    public int getRange() {
      return range;
    }
  }

  class BuildConfig {
    private final int blocksPerSecond;
    public BuildConfig(int blocksPerSecond) {
      this.blocksPerSecond = blocksPerSecond;
    }

    public int getBlocksPerSecond() {
      return blocksPerSecond;
    }
  }

  private final MayorPlugin plugin;
  private final int LOCATE_CONFIG_DEFAULT_RANGE = 25;
  private final int BUILD_CONFIG_DEFAULT_BPS = 1;

  public LocateConfig locateConfig = new LocateConfig(LOCATE_CONFIG_DEFAULT_RANGE);
  public BuildConfig buildConfig = new BuildConfig(BUILD_CONFIG_DEFAULT_BPS);

  public MayorBuilder(MayorPlugin plugin) {
    this.plugin = plugin;
  }
}
