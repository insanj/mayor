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

public class MayorStructureBuilder extends MayorBuilder {
  private MayorStructureHandler structureHandler;
  
  public MayorStructureBuilder(MayorPlugin plugin, MayorStructureHandler structureHandler) {
    super(plugin);
    this.structureHandler = structureHandler;
  }

  public void locateAndBuildStructure(DefinedStructure structure, Location location) {
    WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
    DefinedStructureInfo structInfo = new DefinedStructureInfo().a(EnumBlockMirror.NONE).a(EnumBlockRotation.NONE).a(false).a((ChunkCoordIntPair) null).a((Block) null).c(false).a(1.0f).a(new Random());

    BlockPosition blockPos = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    structure.a(world, blockPos, structInfo);

    /*
    WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
    BlockPosition pos = new BlockPosition(location.getX(), location.getY(), location.getZ());
    try {
      structure.mayorBuild(world, pos, buildConfig);
    } catch (Exception e) {
      plugin.logError(e);
    }*/
  }
}
