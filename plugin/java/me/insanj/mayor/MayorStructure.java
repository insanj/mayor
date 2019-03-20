package me.insanj.mayor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.Random;
import java.lang.reflect.Field;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.bukkit.World;

// import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;

import net.minecraft.server.v1_13_R2.Block;
import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.ChunkCoordIntPair;
import net.minecraft.server.v1_13_R2.DefinedStructure;
import net.minecraft.server.v1_13_R2.DefinedStructureManager;
import net.minecraft.server.v1_13_R2.DefinedStructureInfo;
import net.minecraft.server.v1_13_R2.EnumBlockMirror;
import net.minecraft.server.v1_13_R2.EnumBlockRotation;
import net.minecraft.server.v1_13_R2.WorldServer;
import net.minecraft.server.v1_13_R2.NBTCompressedStreamTools;
import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.Blocks;
import net.minecraft.server.v1_13_R2.WorldServer;
import net.minecraft.server.v1_13_R2.NBTCompressedStreamTools;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.EnumBlockRotation;
import net.minecraft.server.v1_13_R2.TileEntity;
import net.minecraft.server.v1_13_R2.BaseBlockPosition;
import net.minecraft.server.v1_13_R2.StructureBoundingBox;
import net.minecraft.server.v1_13_R2.IBlockData;
import net.minecraft.server.v1_13_R2.IInventory;

public class MayorStructure extends DefinedStructure {
  private Object readPrivateSuperClassField(String fieldName) throws Exception {
      return readPrivateSuperClassField(this.getClass(), fieldName);
  }

  private Object readPrivateSuperClassField(Class classInstance, String fieldName) throws Exception {
      Class<?> superclass = classInstance.getSuperclass();
      Field field = superclass.getDeclaredField(fieldName);
      field.setAccessible(true);
      return field.get(this);
  }

  public void mayorBuild(WorldServer world, BlockPosition blockposition, MayorBuildHandler.BuildConfig buildConfig) throws Exception {
    EnumBlockRotation rotation = EnumBlockRotation.NONE;

    DefinedStructureInfo definedstructureinfo = new DefinedStructureInfo().a(EnumBlockMirror.NONE).a(rotation).a(false).a((ChunkCoordIntPair) null).a((Block) null).c(false).a(1.0f).a(new Random());
    definedstructureinfo.i();

    List<DefinedStructure.BlockInfo> a = (List<DefinedStructure.BlockInfo>) readPrivateSuperClassField("b");
    BlockPosition c = (BlockPosition) readPrivateSuperClassField("c");

    if (!a.isEmpty() && c.getX() >= 1 && c.getY() >= 1 && c.getZ() >= 1) {
        Block block =definedstructureinfo.i(); // prev: f
        StructureBoundingBox structureboundingbox = definedstructureinfo.j(); // prev: g
        Iterator iterator = a.iterator();

        DefinedStructure.BlockInfo definedstructure_blockinfo;

        while (iterator.hasNext()) {
            TimeUnit.SECONDS.sleep(1/buildConfig.getBlocksPerSecond());

            definedstructure_blockinfo = (DefinedStructure.BlockInfo) iterator.next();
            Block block1 = definedstructure_blockinfo.b.getBlock();

            if ((block == null || block != block1) && (!definedstructureinfo.h() || block1 != Blocks.STRUCTURE_BLOCK)) {
                BlockPosition blockposition1 = a(definedstructureinfo, definedstructure_blockinfo.a).a(blockposition);

                if (structureboundingbox == null || structureboundingbox.b((BaseBlockPosition) blockposition1)) {
                    IBlockData iblockdata = definedstructure_blockinfo.b.a(definedstructureinfo.b());
                    IBlockData iblockdata1 = iblockdata.a(definedstructureinfo.c());
                    TileEntity tileentity;

                    if (definedstructure_blockinfo.c != null) {
                        tileentity = world.getTileEntity(blockposition1);
                        if (tileentity != null) {
                            if (tileentity instanceof IInventory) {
                                ((IInventory) tileentity).clear(); // prev: l()
                            }

                            world.setTypeAndData(blockposition1, Blocks.BARRIER.getBlockData(), 4);
                        }
                    }

                    if (world.setTypeAndData(blockposition1, iblockdata1, 2) && definedstructure_blockinfo.c != null) {
                        tileentity = world.getTileEntity(blockposition1);
                        if (tileentity != null) {
                            definedstructure_blockinfo.c.setInt("x", blockposition1.getX());
                            definedstructure_blockinfo.c.setInt("y", blockposition1.getY());
                            definedstructure_blockinfo.c.setInt("z", blockposition1.getZ());
                            tileentity.save(definedstructure_blockinfo.c); // param: NBTTagCompound; originally a()
                        }
                    }
                }
            }
        }

        iterator = a.iterator();

        while (iterator.hasNext()) {
            definedstructure_blockinfo = (DefinedStructure.BlockInfo) iterator.next();
            if (block == null || block != definedstructure_blockinfo.b.getBlock()) {
                BlockPosition blockposition2 = a(definedstructureinfo, definedstructure_blockinfo.a).a(blockposition);

                if (structureboundingbox == null || structureboundingbox.b((BaseBlockPosition) blockposition2)) {
                    world.update(blockposition2, definedstructure_blockinfo.b.getBlock());
                    if (definedstructure_blockinfo.c != null) {
                        TileEntity tileentity1 = world.getTileEntity(blockposition2);

                        if (tileentity1 != null) {
                            tileentity1.update();
                        }
                    }
                }
            }
        }

/*
        if (!definedstructureinfo.h()) { // prev: e()
            this.a(world, blockposition, definedstructureinfo.b(), definedstructureinfo.c(), structureboundingbox);
        }

*/
    }
  }
}
