package kamannen.awesomechest.container;

import cpw.mods.fml.common.FMLCommonHandler;
import kamannen.awesomechest.inventory.InventoryLock;
import kamannen.awesomechest.item.ItemLock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLock extends Container {

    private final InventoryLock inventory;
    private final EntityPlayer player;

    public ContainerLock(final EntityPlayer player, final InventoryLock inventory) {
        this.inventory = inventory;
        this.player = player;
        layoutContainer(player, inventory, 184, 184);
    }

    @Override
    public boolean canInteractWith(final EntityPlayer player) {
        return true;
    }

    @Override
    public void onContainerClosed(final EntityPlayer player) {
        super.onContainerClosed(player);
        if (!player.worldObj.isRemote) {

            saveInventory(player);
        }
    }

    @Override
    public ItemStack transferStackInSlot(final EntityPlayer player, final int i) {
        ItemStack itemstack = null;
        final Slot slot = (Slot) inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (i < 1) {
                if (!mergeItemStack(itemstack1, 1, inventorySlots.size(), false)) {
                    return null;
                }
            } else if (itemstack.getItem() instanceof ItemLock) {
                return null;
            } else if (!mergeItemStack(itemstack1, 0, 1, false)) {
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
        addSlotToContainer(new SlotLock(this, chestInventory, player, 0, 12 + 4 * 18, 8 + 2 * 18));

        final int leftCol = (xSize - 162) / 2 + 1;
        for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++) {
            for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++) {
                addSlotToContainer(new Slot(player.inventory, playerInvCol + playerInvRow * 9 + 9,
                        leftCol + playerInvCol * 18, ySize - (4 - playerInvRow) * 18 - 10));
            }
        }

        for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
            addSlotToContainer(new Slot(player.inventory, hotbarSlot, leftCol + hotbarSlot * 18, ySize - 24));
        }
    }

    public void saveInventory(final EntityPlayer player) {
        inventory.onGuiSaved(player);
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


    private class SlotLock extends Slot {
        private final EntityPlayer entityPlayer;
        private final ContainerLock containerLock;

        public SlotLock(final ContainerLock containerLock, final IInventory inventory, final EntityPlayer entityPlayer, final int slotIndex, final int x, final int y) {
            super(inventory, slotIndex, x, y);
            this.entityPlayer = entityPlayer;
            this.containerLock = containerLock;
        }

        @Override
        public int getSlotStackLimit() {
            return 1;
        }

        @Override
        public void onSlotChanged() {
            super.onSlotChanged();

            if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
                containerLock.saveInventory(entityPlayer);
            }
        }

        @Override
        public boolean isItemValid(final ItemStack itemStack) {
            return true; //TODO: Check that item is a key
        }
    }
}
