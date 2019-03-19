package me.insanj.mayor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_13_R2.MerchantRecipeList;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftVillager;

// https://bukkit.org/threads/class-custom-vilagers-trade-items.338739/
public class MayorVillager {
  private final MayorPlugin plugin;

  private Object ev;
  private MerchantRecipeList list;

  private static final String bukkitversion = Bukkit.getServer().getClass().getPackage().getName().substring(23);

  public MayorVillager(MayorPlugin plugin, Villager villager) {
    this.plugin = plugin;

    list = new MerchantRecipeList();
    ev = ((CraftVillager) villager).getHandle();
  }

  public MayorVillager addRecipe(ItemStack slotOne, ItemStack slotTwo, ItemStack output) {
      try {
          Class<?> mechantRecipe = Class.forName("net.minecraft.server."+bukkitversion+".MerchantRecipe");
          Method add = list.getClass().getDeclaredMethod("a", mechantRecipe);
          Class<?> nmsItemStack = Class.forName("net.minecraft.server."
                  + bukkitversion + ".ItemStack");
          Constructor<?> merchantRecipeConstructor = mechantRecipe.getDeclaredConstructor(nmsItemStack,nmsItemStack,nmsItemStack);
          Object merchantRecipeObj = merchantRecipeConstructor.newInstance(toNMSItemStack(slotOne), toNMSItemStack(slotTwo),toNMSItemStack(output));
          add.invoke(list,merchantRecipeObj);
      } catch (Exception e) {
        plugin.logError(e);
      }

      return this;
  }

  public MayorVillager addRecipe(ItemStack slotOne, ItemStack output) {
      try {
          Class<?> mechantRecipe = Class.forName("net.minecraft.server."+bukkitversion+".MerchantRecipe");
          Method add = list.getClass().getDeclaredMethod("a", mechantRecipe);

          Class<?> nmsItemStack = Class.forName("net.minecraft.server." + bukkitversion + ".ItemStack");

          Constructor<?> merchantRecipeConstructor = mechantRecipe.getDeclaredConstructor(nmsItemStack,nmsItemStack);
          Object merchantRecipeObj = merchantRecipeConstructor.newInstance(toNMSItemStack(slotOne),toNMSItemStack(output));

          add.invoke(list,merchantRecipeObj);
      } catch (Exception e) {
          plugin.logError(e);
      }

      return this;
  }

  private Object toNMSItemStack(ItemStack i) {
      try {
          Class<?> craftItemstack = Class.forName("org.bukkit.craftbukkit."+ bukkitversion + ".inventory.CraftItemStack");
          Method nmsCopy = craftItemstack.getDeclaredMethod("asNMSCopy", ItemStack.class);
          return nmsCopy.invoke(craftItemstack, i);
      } catch (Exception e) {
        plugin.logError(e);
      }

      return null;
  }

  public boolean finish() {
      try {
          Field f = ev.getClass().getDeclaredField("bu");
          f.setAccessible(true);
          f.set(ev, list);
          return true;
      } catch (Exception e) {
          plugin.logError(e);
          return false;
      }
  }
}
