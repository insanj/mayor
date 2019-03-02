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
            byte[] data = nbtdata.getByteArray("Data");
 
            NBTTagList entities = nbtdata.getList("Entities", 0);
            NBTTagList tileentities = nbtdata.getList("TileEntities", 0);

            fis.close();

            /*
            NBTNamedTag namedTag = new NBTDeserializer().fromFile(f);
            // the NBT file format dictates that the root tag MUST be a compound
            NBTCompound root = (NBTCompound) namedTag.getTag();
            NBTCompound data = root.getCompoundTag("Data");
            */

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
}
