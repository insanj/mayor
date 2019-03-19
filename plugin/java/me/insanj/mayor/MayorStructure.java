package me.insanj.mayor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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
import net.minecraft.server.v1_13_R2.TileEntity;
import net.minecraft.server.v1_13_R2.BaseBlockPosition;

public class MayorStructure extends DefinedStructure {

  public void mayorBuild(World world, BlockPosition blockposition, DefinedStructureInfo definedstructureinfo, MayorBuildHandler.BuildConfig buildConfig) {
    definedstructureinfo.i();
    if (!this.a.isEmpty() && this.c.getX() >= 1 && this.c.getY() >= 1 && this.c.getZ() >= 1) {
        Block block = definedstructureinfo.f();
        StructureBoundingBox structureboundingbox = definedstructureinfo.g();
        Iterator iterator = this.a.iterator();

        DefinedStructure.BlockInfo definedstructure_blockinfo;

        while (iterator.hasNext()) {
            TimeUnit.SECONDS.sleep(1/buildConfig.blocksPerSecond);

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
                                ((IInventory) tileentity).l();
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
                            tileentity.a(definedstructure_blockinfo.c);
                        }
                    }
                }
            }
        }

        iterator = this.a.iterator();

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

        if (!definedstructureinfo.e()) {
            this.a(world, blockposition, definedstructureinfo.b(), definedstructureinfo.c(), structureboundingbox);
        }
    }
  }
}
