package kamannen.awesomechest.container;

import kamannen.awesomechest.item.ACChestUpgrade;
import kamannen.awesomechest.tileentity.ChestTileEntity;
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

    @Override
    public void onSlotChanged() {
        if (this.inventory instanceof ChestTileEntity) {
            final ChestTileEntity belongsToChestTileEntity = (ChestTileEntity) this.inventory;
            if (this.getHasStack()) {
                final ItemStack stack = this.getStack();
                final Item item = stack.getItem();
                if (item instanceof ACChestUpgrade && !upgradeAlreadyApplied(item)) {
                    belongsToChestTileEntity.addUpgrade(this.getSlotIndex(), stack);
                }
            } else {
                belongsToChestTileEntity.removeUpgrade(this.getSlotIndex());
            }
        }
    }

    private boolean upgradeAlreadyApplied(final Item item) {
        if (this.inventory instanceof ChestTileEntity && item instanceof ACChestUpgrade) {
            final ChestTileEntity belongsToChestTileEntity = (ChestTileEntity) this.inventory;
            return belongsToChestTileEntity.containsUpgrade((ACChestUpgrade) item);
        }
        return false;
    }
}
