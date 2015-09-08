package kamannen.awesomechest.container;

import kamannen.awesomechest.item.ACChestUpgrade;
import kamannen.awesomechest.lib.Values;
import kamannen.awesomechest.tileentity.ChestTileEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ACUpgradeSlot extends Slot {

    private final ChestTileEntity belongsToChestTileEntity;

    public ACUpgradeSlot(final ChestTileEntity belongsToChestTileEntity, final IInventory inventory, final int slotIndex, final int x, final int y) {
        super(inventory, slotIndex, x, y);
        this.belongsToChestTileEntity = belongsToChestTileEntity;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    @Override
    public boolean isItemValid(final ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() instanceof ACChestUpgrade && !upgradeAlreadyApplied(itemStack.getItem());
    }

    @Override
    public void onSlotChanged() {
        if (this.getHasStack()) {
            final ItemStack stack = this.getStack();
            final Item item = stack.getItem();
            if (item instanceof ACChestUpgrade) {
                final ACChestUpgrade upgradeItem = (ACChestUpgrade) item;
                upgradeItem.addUpgrade(this.belongsToChestTileEntity);
            }
        }
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
