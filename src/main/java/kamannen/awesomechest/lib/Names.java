package kamannen.awesomechest.lib;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class Names {
    public static class Blocks {
        public static final String AWESOME_CHEST = "awesomeChest";
    }

    public static class Items {
        public static final String LOCK = "itemLock";
    }

    public static class NBT {
        public static final String UUID_MOST_SIG = "itemLock";
        public static final String UUID_LEAST_SIG = "itemLock";
        public static final String key = "key";
        public static final String ITEMS = "Items";
        public static final String FACING_DIRECTION = "directionFacing";
    }

    public static String getBlockName(final Block block) {
        return block.getUnlocalizedName().substring(block.getUnlocalizedName().indexOf(".") + 1);
    }

    public static String getItemName(final Item item) {
        return item.getUnlocalizedName().substring(item.getUnlocalizedName().indexOf(".") + 1);
    }
}
