package kamannen.awesomechest.container;

import kamannen.awesomechest.item.ItemLock;
import kamannen.awesomechest.tileentity.ChestTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAWChest extends Container {

    private final ChestTileEntity inventory;

    public ContainerAWChest(final EntityPlayer player, final ChestTileEntity inventory) {
        this.inventory = inventory;
        inventory.openInventory();
        final int sizeInventory = inventory.getSizeInventory();

        final int slotPixelSize = 18;
        final int numColumn = 9;
        final int playerInvRows = 4;
        int xSize = ((sizeInventory * slotPixelSize) / (sizeInventory / numColumn)) + 14;
        int ySize = (sizeInventory / numColumn) * slotPixelSize + 42 + (playerInvRows * slotPixelSize);

        layoutContainer(player, inventory, xSize, ySize);
    }

    public ChestTileEntity getMyInventory() {
        return inventory;
    }

    @Override
    public boolean canInteractWith(final EntityPlayer player) {
        return true;
    }

    @Override
    public void onContainerClosed(final EntityPlayer player) {
        super.onContainerClosed(player);
        this.inventory.closeInventory();
    }


    @Override
    public ItemStack transferStackInSlot(final EntityPlayer player, final int i) {
        ItemStack itemstack = null;
        final Slot slot = (Slot) inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            final int sizeInventory = this.inventory.getSizeInventory();
            if (i < sizeInventory) {
                if (!mergeItemStack(itemstack1, sizeInventory, inventorySlots.size(), false)) {
                    return null;
                }
            } else if (itemstack.getItem() instanceof ItemLock) {
                return null;
            } else if (!mergeItemStack(itemstack1, 0, sizeInventory, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }

    protected void layoutContainer(final EntityPlayer player, final IInventory chestInventory, final int xSize, final int ySize) {

        for (int chestRow = 0; chestRow < 3; chestRow++) {
            for (int chestCol = 0; chestCol < 9; chestCol++) {
                addSlotToContainer(new Slot(chestInventory, chestCol + chestRow * 9, 8 + chestCol * 18, 18 + chestRow * 18));
            }
        }

        final int leftCol = 8;
        for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++) {
            for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++) {
                addSlotToContainer(new Slot(player.inventory, playerInvCol + playerInvRow * 9 + 9,
                        leftCol + playerInvCol * 18, ySize - 82 + (playerInvRow * 18)));
            }
        }

        for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
            addSlotToContainer(new Slot(player.inventory, hotbarSlot, leftCol + hotbarSlot * 18, ySize - 24));
        }
    }

    @Override
    protected boolean mergeItemStack(final ItemStack itemStack, final int slotMin, final int slotMax, final boolean ascending) {
        boolean slotFound = false;
        int currentSlotIndex = ascending ? slotMax - 1 : slotMin;

        Slot slot;
        ItemStack stackInSlot;

        if (itemStack.isStackable()) {
            while (itemStack.stackSize > 0 && (!ascending && currentSlotIndex < slotMax || ascending && currentSlotIndex >= slotMin)) {
                slot = (Slot) this.inventorySlots.get(currentSlotIndex);
                stackInSlot = slot.getStack();

                if (slot.isItemValid(itemStack) &&
                        stackInSlot != null && stackInSlot.getItem() == itemStack.getItem() &&
                        (!itemStack.getHasSubtypes() || itemStack.getItemDamage() == stackInSlot.getItemDamage())
                        && ItemStack.areItemStackTagsEqual(itemStack, stackInSlot)) {
                    final int combinedStackSize = stackInSlot.stackSize + itemStack.stackSize;
                    final int slotStackSizeLimit = Math.min(stackInSlot.getMaxStackSize(), slot.getSlotStackLimit());

                    if (combinedStackSize <= slotStackSizeLimit) {
                        itemStack.stackSize = 0;
                        stackInSlot.stackSize = combinedStackSize;
                        slot.onSlotChanged();
                        slotFound = true;
                    } else if (stackInSlot.stackSize < slotStackSizeLimit) {
                        itemStack.stackSize -= slotStackSizeLimit - stackInSlot.stackSize;
                        stackInSlot.stackSize = slotStackSizeLimit;
                        slot.onSlotChanged();
                        slotFound = true;
                    }
                }

                currentSlotIndex += ascending ? -1 : 1;
            }
        }

        if (itemStack.stackSize > 0) {
            currentSlotIndex = ascending ? slotMax - 1 : slotMin;

            while (!ascending && currentSlotIndex < slotMax || ascending && currentSlotIndex >= slotMin) {
                slot = (Slot) this.inventorySlots.get(currentSlotIndex);
                stackInSlot = slot.getStack();

                if (slot.isItemValid(itemStack) && stackInSlot == null) {
                    final ItemStack copy = itemStack.copy();
                    copy.stackSize = Math.min(itemStack.stackSize, slot.getSlotStackLimit());
                    slot.putStack(copy);
                    slot.onSlotChanged();

                    if (slot.getStack() != null) {
                        itemStack.stackSize -= slot.getStack().stackSize;
                        slotFound = true;
                    }

                    break;
                }

                currentSlotIndex += ascending ? -1 : 1;
            }
        }

        return slotFound;
    }
}
