package me.insanj.contractors;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
 
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.EnumSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import net.minecraft.server.v1_13_R2.*;

public class ContractorsSchematicHandler {
    public final Contractors plugin;

    public ContractorsSchematicHandler(Contractors plugin) {
        this.plugin = plugin;
    }

    public ContractorsSchematic readSchematicFile(String name) {
        if (!name.endsWith(".schematic")) {
            name = name + ".schematic";
        }
        File file = new File(plugin.getDataFolder() + "/schematics/" + name);
        if (!file.exists()) {
            return null;
        }

        try {
            FileInputStream stream = new FileInputStream(file);
            NBTTagCompound nbtdata = NBTCompressedStreamTools.a(stream);

            short width = nbtdata.getShort("Width");
            short height = nbtdata.getShort("Height");
            short length = nbtdata.getShort("Length");

            byte[] blocks = nbtdata.getByteArray("Blocks");
            byte[] data = nbtdata.getByteArray("Data");

            byte[] addId = new byte[0];

            if (nbtdata.hasKey("AddBlocks")) {
                addId = nbtdata.getByteArray("AddBlocks");
            }

            short[] sblocks = new short[blocks.length];
            for (int index = 0; index < blocks.length; index++) {
                if ((index >> 1) >= addId.length) {
                    sblocks[index] = (short) (blocks[index] & 0xFF);
                } else {
                    if ((index & 1) == 0) {
                        sblocks[index] = (short) (((addId[index >> 1] & 0x0F) << 8) + (blocks[index] & 0xFF));
                    } else {
                        sblocks[index] = (short) (((addId[index >> 1] & 0xF0) << 4) + (blocks[index] & 0xFF));
                    }
                }
            }

            stream.close();
            return new ContractorsSchematic(name, sblocks, data, width, length, height);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void pasteSchematic(World world, Location loc, ContractorsSchematic schematic) {
        short[] blocks = schematic.blocks;
        byte[] blockData = schematic.data;
 
        short length = schematic.length;
        short width = schematic.width;
        short height = schematic.height;
 
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                for (int z = 0; z < length; ++z) {
                    int index = y * width * length + z * width + x;
                    Block block = new Location(world, x + loc.getX(), y + loc.getY(), z + loc.getZ()).getBlock();

                    Material material = convertMaterial((int)blocks[index], blockData[index]);
                    block.setType(material);
                    // block.setTypeIdAndData(blocks[index], blockData[index], true);
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    public Material convertMaterial(int ID, byte Data) {
        for(Material i : EnumSet.allOf(Material.class)) {
            if(i.getId() == ID) {
                return Bukkit.getUnsafe().fromLegacy(new MaterialData(i, Data));
            } 
        }
        return null;
    }
}

