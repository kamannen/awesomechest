package kamannen.awesomechest.container;

import kamannen.awesomechest.item.ACChestUpgrade;
import kamannen.awesomechest.lib.Values;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
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
        return itemStack != null && itemStack.getItem() instanceof ACChestUpgrade && !upgradeAlreadyApplied(itemStack.getItem());
    }

    private boolean upgradeAlreadyApplied(final Item item) {
        final int lastSlot = this.inventory.getSizeInventory();
        final int firstSlot = lastSlot - Values.Entities.CHEST_INVENTORY_SIZE_UPGRADES;

        for (int slotIndex = firstSlot; slotIndex < lastSlot; slotIndex++) {
            final ItemStack stackInSlot = this.inventory.getStackInSlot(slotIndex);
            if (stackInSlot != null) {
                final Item existingUpgradeItem = stackInSlot.getItem();
                if (existingUpgradeItem.getUnlocalizedName().equals(item.getUnlocalizedName())) {
                    return true;
                }
            }
        }

        return false;
    }
}
