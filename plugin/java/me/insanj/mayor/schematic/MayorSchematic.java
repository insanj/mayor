package me.insanj.mayor;

public class MayorSchematic {
   public final String name;
   public final short[] blocks;
   public final byte[] data;
   public final short width;
   public final short length;
   public final short height;

    public MayorSchematic(String name, short[] blocks, byte[] data, short width, short length, short height) {
        this.name = name;
        this.blocks = blocks;
        this.data = data;
        this.width = width;
        this.length = length;
        this.height = height;
    }

    @Override
    public String toString() {
        return String.format("MayorSchematic %s, name=%s, blocks=%s, data=%s, width=%s, length=%s, height=%s", super.toString(), this.name, this.blocks, this.data, this.width, this.length, this.height);
    }
}
