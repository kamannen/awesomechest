package kamannen.awesomechest.item;

import kamannen.awesomechest.lib.Names;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Map;

public class ItemHelper {

    public static boolean checkItemIsValidKeyForLock(final ItemStack currentEquippedItem, final Map<Integer, ItemStack> upgrades) {
        if (currentEquippedItem != null && currentEquippedItem.getItem() instanceof ItemKey) {
            final NBTTagCompound stackTagCompoundKey = currentEquippedItem.stackTagCompound;
            for (ItemStack itemStack : upgrades.values()) {
                if (itemStack != null) {
                    final Item item = itemStack.getItem();
                    if (item instanceof ACChestUpgrade) {
                        if (item instanceof ItemLock) {
                            final NBTTagCompound stackTagCompoundLock = itemStack.stackTagCompound;
                            if (stackTagCompoundLock != null && stackTagCompoundLock.hasKey(Names.NBT.key)
                                    && stackTagCompoundKey != null && stackTagCompoundKey.hasKey(Names.NBT.key)) {
                                final int codeLock = stackTagCompoundLock.getInteger(Names.NBT.key);
                                final int codeKey = stackTagCompoundKey.getInteger(Names.NBT.key);
                                return codeKey == codeLock;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

}
