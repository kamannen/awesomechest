package kamannen.awesomechest.inventory;

import kamannen.awesomechest.lib.Names;
import kamannen.awesomechest.lib.Values;
import net.minecraft.item.ItemStack;

public class InventoryAWChest extends AWInventoryBase {

    public InventoryAWChest() {
        super(new ItemStack[Values.Entities.CHEST_INVENTORY_SIZE_SMALL + Values.Entities.CHEST_INVENTORY_SIZE_UPGRADES]);
    }

    @Override
    public int getSizeInventory() {
        return Values.Entities.CHEST_INVENTORY_SIZE_SMALL + Values.Entities.CHEST_INVENTORY_SIZE_UPGRADES;
    }

    @Override
    public String getInventoryName() {
        return Names.Blocks.AWESOME_CHEST;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
}
