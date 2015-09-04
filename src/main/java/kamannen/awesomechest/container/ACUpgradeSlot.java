package kamannen.awesomechest.container;

import kamannen.awesomechest.item.ACChestUpgrade;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ACUpgradeSlot extends Slot {
    public ACUpgradeSlot(final IInventory inventory, final int slotIndex, final int x, final int y) {
        super(inventory, slotIndex, x, y);
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    @Override
    public boolean isItemValid(final ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() instanceof ACChestUpgrade;
    }
}
