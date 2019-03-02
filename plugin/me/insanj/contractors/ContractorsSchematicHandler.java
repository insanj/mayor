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

                    // Material material = findMaterial((int)blocks[index]);
                    // Material materialWithData = Bukkit.getUnsafe().fromLegacy(new MaterialData(material, blockData[index]));
                    Material material = convertMaterial((int)blocks[index], blockData[index]);
                    block.setType(material);
                    //block.setBlockData(materialWithData);
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


    public Material findMaterial(int typeId) {
        final Material[] foundMaterial = new Material[1];

        EnumSet.allOf(Material.class).forEach(material -> {
            if (material.getId() == typeId) {
                foundMaterial[0] = material;
            }
        });

        return foundMaterial[0];
    }

 
    /*
    public static ContractorsSchematic loadSchematic(File file) throws IOException
    {
        FileInputStream stream = new FileInputStream(file);
        NBTInputStream nbtStream = new NBTInputStream(new GZIPInputStream(stream));
 
        CompoundTag schematicTag = (CompoundTag) nbtStream.readTag();
        if (!schematicTag.getName().equals("Schematic")) {
            throw new IllegalArgumentException("Tag \"Schematic\" does not exist or is not first");
        }
 
        Map<String, Tag> schematic = schematicTag.getValue();
        if (!schematic.containsKey("Blocks")) {
            throw new IllegalArgumentException("Schematic file is missing a \"Blocks\" tag");
        }
 
        short width = getChildTag(schematic, "Width", ShortTag.class).getValue();
        short length = getChildTag(schematic, "Length", ShortTag.class).getValue();
        short height = getChildTag(schematic, "Height", ShortTag.class).getValue();
 
        String materials = getChildTag(schematic, "Materials", StringTag.class).getValue();
        if (!materials.equals("Alpha")) {
            throw new IllegalArgumentException("Schematic file is not an Alpha schematic");
        }
 
        byte[] blocks = getChildTag(schematic, "Blocks", ByteArrayTag.class).getValue();
        byte[] blockData = getChildTag(schematic, "Data", ByteArrayTag.class).getValue();
        return new ContractorsSchematic(file.name, blocks, blockData, width, length, height);
    }
 */

 /*
    private static <T extends Tag> T getChildTag(Map<String, Tag> items, String key, Class<T> expected) throws IllegalArgumentException
    {
        if (!items.containsKey(key)) {
            throw new IllegalArgumentException("Schematic file is missing a \"" + key + "\" tag");
        }
        Tag tag = items.get(key);
        if (!expected.isInstance(tag)) {
            throw new IllegalArgumentException(key + " tag is not of tag type " + expected.getName());
        }
        return expected.cast(tag);
    }
*/
}


