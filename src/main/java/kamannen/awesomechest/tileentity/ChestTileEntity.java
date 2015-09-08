package kamannen.awesomechest.tileentity;

import kamannen.awesomechest.item.ItemHelper;
import kamannen.awesomechest.lib.Names;
import kamannen.awesomechest.lib.Values;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class ChestTileEntity extends TileEntity implements IInventory {

    private String playerName;
    private Block storedBlock;
    public float lidAngle;
    public float prevLidAngle;
    public int numUsingPlayers;
    private int ticksSinceSync;
    private ForgeDirection facingDirection;
    private ItemStack[] content;
    private List<EntityPlayer> usingPlayers;

    private boolean chestLocked;

    public ChestTileEntity() {
        super();
        facingDirection = ForgeDirection.SOUTH;
        content = new ItemStack[Values.Entities.CHEST_INVENTORY_SIZE_SMALL
                + Values.Entities.CHEST_INVENTORY_SIZE_UPGRADES];
        chestLocked = false;
        usingPlayers = new ArrayList<>();
    }

    public ChestTileEntity setPlayer(final String playerName) {
        this.playerName = playerName;
        return this;
    }

    public ChestTileEntity setFacingDirection(final ForgeDirection direction) {
        this.facingDirection = direction;
        return this;
    }

    public ForgeDirection getFacingDirection() {
        return this.facingDirection;
    }

    @Override
    public int getSizeInventory() {
        return content.length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return i >= 0 && i < this.content.length ? content[i] : null;
    }

    @Override
    public ItemStack decrStackSize(int i, int decreaseBy) {
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
    public ItemStack getStackInSlotOnClosing(int i) {
        if (content[i] != null) {
            final ItemStack itemStack = content[i];
            content[i] = null;
            return itemStack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack itemStack) {
        content[slotIndex] = itemStack;

        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    @Override
    public String getInventoryName() {
        return StatCollector.translateToLocalFormatted("chest.header.text", this.playerName);
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
    public boolean isUseableByPlayer(final EntityPlayer entityplayer) {
        return (this.worldObj.getTileEntity(xCoord, yCoord, zCoord) == this
                && entityplayer.getDistanceSq((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D)
                && (!isChestLocked() || ItemHelper.checkItemIsValidKeyForLock(entityplayer.getCurrentEquippedItem(), content)
                || usingPlayers.contains(entityplayer));
    }

    @Override
    public void openInventory() {
        usingPlayers.add(Minecraft.getMinecraft().thePlayer);
        ++numUsingPlayers;
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, this.getBlockType(), 1, numUsingPlayers);
    }

    @Override
    public void closeInventory() {
        usingPlayers.remove(Minecraft.getMinecraft().thePlayer);
        --numUsingPlayers;
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, this.getBlockType(), 1, numUsingPlayers);
    }

    @Override
    public boolean isItemValidForSlot(int slotIndex, ItemStack itemStack) {
        return true;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (++ticksSinceSync % 20 * 4 == 0) {
            worldObj.addBlockEvent(xCoord, yCoord, zCoord, this.getBlockType(), 1, numUsingPlayers);
        }

        prevLidAngle = lidAngle;
        float angleIncrement = 0.1F;
        double adjustedXCoord, adjustedZCoord;

        if (numUsingPlayers > 0 && lidAngle == 0.0F) {
            adjustedXCoord = xCoord + 0.5D;
            adjustedZCoord = zCoord + 0.5D;
            worldObj.playSoundEffect(adjustedXCoord, yCoord + 0.5D, adjustedZCoord, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (numUsingPlayers == 0 && lidAngle > 0.0F || numUsingPlayers > 0 && lidAngle < 1.0F) {
            float var8 = lidAngle;

            if (numUsingPlayers > 0) {
                lidAngle += angleIncrement;
            } else {
                lidAngle -= angleIncrement;
            }

            if (lidAngle > 1.0F) {
                lidAngle = 1.0F;
            }

            if (lidAngle < 0.5F && var8 >= 0.5F) {
                adjustedXCoord = xCoord + 0.5D;
                adjustedZCoord = zCoord + 0.5D;
                worldObj.playSoundEffect(adjustedXCoord, yCoord + 0.5D, adjustedZCoord, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (lidAngle < 0.0F) {
                lidAngle = 0.0F;
            }
        }
    }

    @Override
    public boolean receiveClientEvent(int eventID, int numUsingPlayers) {
        if (eventID == 1) {
            this.numUsingPlayers = numUsingPlayers;
            return true;
        } else {
            return super.receiveClientEvent(eventID, numUsingPlayers);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);

        this.storedBlock = Block.getBlockById(nbtTagCompound.getInteger("blockId"));
        this.playerName = nbtTagCompound.getString("playerName");
        this.chestLocked = nbtTagCompound.getBoolean(Names.NBT.CHEST_LOCKED);
        this.facingDirection = ForgeDirection.getOrientation(nbtTagCompound.getByte(Names.NBT.FACING_DIRECTION));

        NBTTagList tagList = nbtTagCompound.getTagList(Names.NBT.ITEMS, 10);
        content = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < tagList.tagCount(); ++i) {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            byte slotIndex = tagCompound.getByte("Slot");
            if (slotIndex >= 0 && slotIndex < content.length) {
                content[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setInteger("blockId", Block.getIdFromBlock(this.storedBlock));
        nbtTagCompound.setString("playerName", this.playerName);
        nbtTagCompound.setBoolean(Names.NBT.CHEST_LOCKED, this.chestLocked);
        nbtTagCompound.setByte(Names.NBT.FACING_DIRECTION, (byte) this.facingDirection.ordinal());

        NBTTagList tagList = new NBTTagList();
        for (int currentIndex = 0; currentIndex < content.length; ++currentIndex) {
            if (content[currentIndex] != null) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) currentIndex);
                content[currentIndex].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        nbtTagCompound.setTag(Names.NBT.ITEMS, tagList);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        readFromNBT(packet.func_148857_g());
    }

    public void setChestLocked(final boolean isLocked) {
        chestLocked = isLocked;
    }

    public boolean isChestLocked() {
        return chestLocked;
    }
}
