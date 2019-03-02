package me.insanj.contractors;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
 
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import net.minecraft.server.v1_13_R2.*;

public class ContractorsSchematicHandler {
    public final Contractors plugin;

    public ContractorsSchematicHandler(Contractors plugin) {
        this.plugin = plugin;
    }
/*
    public HashMap<String, Object> readSchematicFile(String filename) {
        try {
            File f = new File(filename);
            if (!f.exists() || f.isDirectory()) { 
                System.out.println("Unable to find schematic file at path: " + filename);
                return new HashMap<String, Object>();
            } else {
                System.out.println("Reading schematic file at path: " + filename);
            }

            InputStream fis = new FileInputStream(f);
            NBTTagCompound nbtdata = NBTCompressedStreamTools.a(fis);
 
            short width = nbtdata.getShort("Width");
            short height = nbtdata.getShort("Height");
            short length = nbtdata.getShort("Length");
 
            String materials = nbtdata.getString("Materials");

            byte[] blocks = nbtdata.getByteArray("Blocks");

            System.out.println("data");
            byte[] data = nbtdata.getByteArray("Data");
            for (byte b : data) {
                System.out.println(b);
            }

            NBTTagList entities = nbtdata.getList("Entities", 0);
            NBTTagList tileentities = nbtdata.getList("TileEntities", 0);

            fis.close();

            HashMap<String, Object> response = new HashMap<String, Object>();
            response.put("Width", width);
            response.put("Height", height);
            response.put("Length", length);
            response.put("Materials", materials);
            response.put("Blocks", blocks.toString());
            response.put("Data", data.toString());
            response.put("Entities", entities.toString());
            response.put("TileEntities", tileentities.toString());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<String, Object>();
        }
    }
*/
    // made with help from 
    // https://www.spigotmc.org/threads/load-schematic-file-and-paste-at-custom-location.167277/
    public ContractorsSchematic readSchematicFile(String name) {
        if (!name.endsWith(".schematic"))
            name = name + ".schematic";
        File file = new File(plugin.getDataFolder() + "/schematics/" + name);
        if (!file.exists())
            return null;
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
}
