package kamannen.awesomechest.inventory;

import kamannen.awesomechest.nbt.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class AWInventoryBase implements IInventory {

    protected ItemStack[] content;

    public AWInventoryBase(final ItemStack[] content) {
        this.content = content;
    }

    public void setContent(final ItemStack[] content) {
        this.content = content;
    }

    public ItemStack[] getContent() {
        return this.content;
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(final int i) {
        return i >= 0 && i < this.content.length ? content[i] : null;
    }

    @Override
    public ItemStack decrStackSize(final int i, final int decreaseBy) {
        ItemStack itemStack = getStackInSlot(i);
        if (itemStack != null) {
            if (itemStack.stackSize <= decreaseBy) {
                setInventorySlotContents(i, null);
            } else {
                itemStack = itemStack.splitStack(decreaseBy);
                if (itemStack.stackSize == 0) {
                    setInventorySlotContents(i, null);
                }
            }
        }

        return itemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(final int i) {
        if (content[i] != null) {
            final ItemStack itemStack = content[i];
            content[i] = null;
            return itemStack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(final int i, final ItemStack itemStack) {
        content[i] = itemStack;
    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {}

    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        return true;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(final int i, final ItemStack itemStack) {
        return true;
    }

    /*public void save(NBTTagCompound nbtTagCompound) {

        if (nbtTagCompound == null) {
            nbtTagCompound = new NBTTagCompound();

            final UUID uuid = UUID.randomUUID();
            nbtTagCompound.setLong(Names.NBT.UUID_MOST_SIG, uuid.getMostSignificantBits());
            nbtTagCompound.setLong(Names.NBT.UUID_LEAST_SIG, uuid.getLeastSignificantBits());
        }

        writeToNBT(nbtTagCompound);
    }*/

    /*public void readFromNBT(final NBTTagCompound nbttagcompound) {
        NBTHelper.readInventoryItemsFromNBT(nbttagcompound, this);
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbttagcompound) {
        NBTHelper.writeInventoryItemsToNBT(nbttagcompound, this);
    }*/

    public boolean hasUUID(final ItemStack itemStack) {
        return NBTHelper.hasUUID(itemStack);
    }

    public boolean hasTag(final ItemStack itemStack, final String keyName) {
        return NBTHelper.hasTag(itemStack, keyName);
    }
}
