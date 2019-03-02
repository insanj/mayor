package me.insanj.contractors;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jnbt.ByteArrayTag;
import org.jnbt.CompoundTag;
import org.jnbt.ListTag;
import org.jnbt.NBTInputStream;
import org.jnbt.ShortTag;
import org.jnbt.Tag;

// derived from https://bukkit.org/threads/how-the-heck-do-i-read-a-schematic-file.45065/
public class ContractorsSchematicHandler {
    private Tag getChildTag(Map<String, Tag> items, String key, Class<? extends Tag> expected) {
        Tag tag = items.get(key);
        return tag;
    }

    public HashMap<String, List> readSchematicFile(String filename) {
        try {
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            System.out.println("Current relative path is: " + s);

            File f = new File(filename);
            FileInputStream fis = new FileInputStream(f);
            NBTInputStream nbt = new NBTInputStream(fis);
            CompoundTag backuptag = (CompoundTag) nbt.readTag();
            Map<String, Tag> tagCollection = backuptag.getValue();

            short width = (Short)getChildTag(tagCollection, "Width", ShortTag.class).getValue();
            short height = (Short) getChildTag(tagCollection, "Height", ShortTag.class).getValue();
            short length = (Short) getChildTag(tagCollection, "Length", ShortTag.class).getValue();

            byte[] blocks = (byte[]) getChildTag(tagCollection, "Blocks", ByteArrayTag.class).getValue();
            byte[] data = (byte[]) getChildTag(tagCollection, "Data", ByteArrayTag.class).getValue();

            List entities = (List) getChildTag(tagCollection, "Entities", ListTag.class).getValue();
            List tileentities = (List) getChildTag(tagCollection, "TileEntities", ListTag.class).getValue();
            nbt.close();
            fis.close();
            
            HashMap<String, List> response = new HashMap<String, List>();
            response.put("entities", entities);
            response.put("tileentities", tileentities);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}