package kamannen.awesomechest.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;

public class ChestTileEntity extends TileEntityChest {

    private String playerName;
    private Block storedBlock;

    public ChestTileEntity() {
        super();
        setPlayer(null);
    }

    public void setPlayer(final String playerName) {
        this.playerName = playerName;
    }

    @Override
    public void closeInventory() {
        super.closeInventory();
//        writeToNBT();
    }

    @Override
    public ItemStack getStackInSlot(final int i) {
        return super.getStackInSlot(i);
    }

    @Override
    public ItemStack decrStackSize(final int i, final int j) {
        return super.decrStackSize(i, j);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(final int i) {
        return super.getStackInSlotOnClosing(i);
    }

    @Override
    public void setInventorySlotContents(final int i, final ItemStack itemStack) {
        super.setInventorySlotContents(i, itemStack);
    }

    @Override
    public String getInventoryName() {
        //TODO: Use lang
        return "Awesome chest, belonging to: " + this.playerName;
    }

    public Block getStoredBlock() {
        return storedBlock;
    }

    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        //TODO: LOCK!
        return super.isUseableByPlayer(entityPlayer);
    }

    @Override
    public boolean isItemValidForSlot(final int i, final ItemStack itemStack) {
        return super.isItemValidForSlot(i, itemStack);
    }

    @Override
    public void checkForAdjacentChests() {
        //TODO: Prevent double chest!
        adjacentChestChecked = true;
        adjacentChestZNeg = null;
        adjacentChestXPos = null;
        adjacentChestXNeg = null;
        adjacentChestZPos = null;
    }

    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.storedBlock = Block.getBlockById(nbtTagCompound.getInteger("blockId"));
        this.playerName = nbtTagCompound.getString("playerName");
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("blockId", Block.getIdFromBlock(this.storedBlock));
        nbtTagCompound.setString("playerName", this.playerName);
    }
}
