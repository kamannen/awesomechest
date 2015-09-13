package kamannen.awesomechest.item;

import kamannen.awesomechest.lib.Names;
import kamannen.awesomechest.nbt.NBTHelper;
import net.minecraft.item.ItemStack;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ItemTest {

    int key;
    ItemStack itemStackLock;
    ItemStack itemStackKey;
    Map<Integer, ItemStack> upgradeMap;

    @Before
    public void setUp() {
        key = NBTHelper.createKeyCode();
        assertTrue(key > 0);
        itemStackLock = new ItemStack(ModItems.itemLock);
        NBTHelper.setInteger(itemStackLock, Names.NBT.key, key);
        assertTrue(itemStackLock.stackTagCompound.hasKey(Names.NBT.key));
        upgradeMap = new HashMap<>();
        upgradeMap.put(1, itemStackLock);
        itemStackKey = new ItemStack(ModItems.itemKey);
        NBTHelper.setInteger(itemStackKey, Names.NBT.key, key);
        assertTrue(itemStackKey.stackTagCompound.hasKey(Names.NBT.key));
    }

    @Test
    public void test_Valid_key_is_valid() {
        assertTrue(ItemHelper.checkItemIsValidKeyForLock(itemStackKey, upgradeMap));
    }

    @Test
    public void test_invalid_key_is_invalid() {
        ItemStack itemStackWrongKey = new ItemStack(ModItems.itemKey);
        NBTHelper.setInteger(itemStackWrongKey, Names.NBT.key, NBTHelper.createKeyCode());
        assertTrue(itemStackWrongKey.stackTagCompound.hasKey(Names.NBT.key));
        assertFalse(ItemHelper.checkItemIsValidKeyForLock(itemStackWrongKey, upgradeMap));
    }

}