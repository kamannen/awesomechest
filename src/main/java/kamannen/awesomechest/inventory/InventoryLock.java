package kamannen.awesomechest.inventory;

import kamannen.awesomechest.item.ItemKey;
import kamannen.awesomechest.item.ModItems;
import kamannen.awesomechest.lib.Names;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.UUID;

public class InventoryLock extends AWInventoryBase {

    public ItemStack itemStack;

    public InventoryLock(final ItemStack is) {
        super(new ItemStack[1]);
        itemStack = is;
        readFromNBT(is.getTagCompound());
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public String getInventoryName() {
        return Names.Items.LOCK;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isItemValidForSlot(final int i, final ItemStack itemStack) {
        return true;
    }

    public void readFromNBT(final NBTTagCompound nbttagcompound) {
        if (nbttagcompound != null) {

            final NBTTagList nbttaglist = nbttagcompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
            setContent(new ItemStack[getSizeInventory()]);
            for (int i = 0; i < nbttaglist.tagCount(); i++) {
                final NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
                final int j = nbttagcompound1.getByte("Slot") & 0xff;
                if (j >= 0 && j < getContent().length) {
                    getContent()[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
                }
            }
            if (getContent()[0] == null) {
                makeAndAddKeyToLock(nbttagcompound);
            }
        }
    }

    private void makeAndAddKeyToLock(final NBTTagCompound nbttagcompound) {
        final boolean isNew = nbttagcompound.getBoolean(Names.NBT.NEW);
        if (isNew) {
            final int code = nbttagcompound.getInteger(Names.NBT.key);
            final ItemKey itemKey = (ItemKey) ModItems.itemKey;
            ItemStack key = new ItemStack(itemKey);
            itemKey.setLockCode(key, code);

            final Minecraft minecraft = Minecraft.getMinecraft();
            itemKey.onCreated(key, minecraft.theWorld, minecraft.thePlayer);
            setInventorySlotContents(0, key.copy());
            nbttagcompound.setBoolean(Names.NBT.NEW, false);
        }
    }

    public void writeToNBT(final NBTTagCompound nbttagcompound) {
        final NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < getContent().length; i++) {
            if (getContent()[i] != null) {
                final NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                getContent()[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Items", nbttaglist);
    }

    public void save() {
        NBTTagCompound nbtTagCompound = itemStack.getTagCompound();

        if (nbtTagCompound == null) {
            nbtTagCompound = new NBTTagCompound();

            final UUID uuid = UUID.randomUUID();
            nbtTagCompound.setLong(Names.NBT.UUID_MOST_SIG, uuid.getMostSignificantBits());
            nbtTagCompound.setLong(Names.NBT.UUID_LEAST_SIG, uuid.getLeastSignificantBits());
        }

        writeToNBT(nbtTagCompound);
        itemStack.setTagCompound(nbtTagCompound);
    }

    public void onGuiSaved(final EntityPlayer entityPlayer) {
        itemStack = findParentItemStack(entityPlayer);

        if (itemStack != null) {
            save();
        }
    }

    public ItemStack findParentItemStack(final EntityPlayer entityPlayer) {
        if (hasUUID(itemStack)) {
            final UUID parentItemStackUUID = new UUID(itemStack.getTagCompound().getLong(Names.NBT.UUID_MOST_SIG), itemStack.getTagCompound().getLong(Names.NBT.UUID_LEAST_SIG));
            for (int i = 0; i < entityPlayer.inventory.getSizeInventory(); i++) {
                final ItemStack itemStack = entityPlayer.inventory.getStackInSlot(i);

                if (hasUUID(itemStack)) {
                    if (itemStack.getTagCompound().getLong(Names.NBT.UUID_MOST_SIG) == parentItemStackUUID.getMostSignificantBits() && itemStack.getTagCompound().getLong(Names.NBT.UUID_LEAST_SIG) == parentItemStackUUID.getLeastSignificantBits()) {
                        return itemStack;
                    }
                }
            }
        }

        return null;
    }
}
