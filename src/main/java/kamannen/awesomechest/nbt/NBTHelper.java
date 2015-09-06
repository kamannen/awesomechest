package kamannen.awesomechest.nbt;

import kamannen.awesomechest.lib.Names;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

public class NBTHelper {

    public static int createKeyCode() {
        return new Random().nextInt(Integer.MAX_VALUE - (Integer.MAX_VALUE / 2)) + (Integer.MAX_VALUE / 2);
    }

    public static boolean hasUUID(final ItemStack itemStack) {
        return hasTag(itemStack, Names.NBT.UUID_MOST_SIG) && hasTag(itemStack, Names.NBT.UUID_LEAST_SIG);
    }

    public static boolean hasTag(final ItemStack itemStack, final String keyName) {
        return itemStack != null && itemStack.stackTagCompound != null && itemStack.stackTagCompound.hasKey(keyName);
    }

    public static void initNBTTagCompound(final ItemStack itemStack) {
        if (itemStack.stackTagCompound == null) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
    }

    public static void setInteger(final ItemStack itemStack, final String key, final int value) {
        initNBTTagCompound(itemStack);
        itemStack.stackTagCompound.setInteger(key, value);
    }

    public static void setBoolean(final ItemStack itemStack, final String key, final boolean value) {
        initNBTTagCompound(itemStack);
        itemStack.stackTagCompound.setBoolean(key, value);
    }
}
