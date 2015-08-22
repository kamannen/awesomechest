package kamannen.awesomechest.nbt;

import kamannen.awesomechest.inventory.AWInventoryBase;
import kamannen.awesomechest.lib.Names;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class NBTHelper {


    /*public static void writeInventoryItemsToNBT(final NBTTagCompound nbttagcompound,
                                                final AWInventoryBase inventory) {
        final NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < inventory.getContent().length; i++) {
            if (inventory.getContent()[i] != null) {
                final NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                inventory.getContent()[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Items", nbttaglist);
    }

    public static void readInventoryItemsFromNBT(final NBTTagCompound nbttagcompound,
                                   final AWInventoryBase inventory) {
        if (nbttagcompound != null) {
            final NBTTagList nbttaglist = nbttagcompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
            inventory.setContent(new ItemStack[inventory.getSizeInventory()]);
            for (int i = 0; i < nbttaglist.tagCount(); i++) {
                final NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
                final int j = nbttagcompound1.getByte("Slot") & 0xff;
                if (j >= 0 && j < inventory.getContent().length) {
                    inventory.getContent()[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
                }
            }
        }
    }*/

    public static boolean hasUUID(final ItemStack itemStack) {
        return hasTag(itemStack, Names.NBT.UUID_MOST_SIG) && hasTag(itemStack, Names.NBT.UUID_LEAST_SIG);
    }

    public static boolean hasTag(final ItemStack itemStack, final String keyName) {
        return itemStack != null && itemStack.stackTagCompound != null && itemStack.stackTagCompound.hasKey(keyName);
    }

}
