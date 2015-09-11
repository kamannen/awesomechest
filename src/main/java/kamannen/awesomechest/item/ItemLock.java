package kamannen.awesomechest.item;

import kamannen.awesomechest.AwesomechestMod;
import kamannen.awesomechest.gui.EGUIs;
import kamannen.awesomechest.lib.Names;
import kamannen.awesomechest.tileentity.ChestTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

//TODO: Figure out why shift click crafting doesn't create NBT, and why addInformation doesn't update info on lock immediately
// It's because shift clicking gives us the empty itemstack where the actual itemstack is going to be merged,
// not the actual itemstack with our item
public class ItemLock extends ACItem implements ACChestUpgrade {

    public ItemLock() {
        super();
        setUnlocalizedName(Names.Items.LOCK);
    }

    @Override
    public void onCreated(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (!world.isRemote) {
            setUUID(itemStack);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer player,
                               final List list, final boolean b) {
        final NBTTagCompound tagCompound = itemStack.getTagCompound();
        if (tagCompound != null) {
            final int code = itemStack.stackTagCompound.getInteger(Names.NBT.key);
            list.add(EnumChatFormatting.GREEN + StatCollector.translateToLocalFormatted("key.lock.id.message", code));
        }
    }

    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (!world.isRemote && !entityPlayer.isSneaking()) {
            entityPlayer.openGui(AwesomechestMod.instance, EGUIs.LOCK.ordinal(), world, (int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ);
        }
        return itemStack;
    }

    public static void setUUID(final ItemStack itemStack) {
        initNBTTagCompound(itemStack);

        if (!hasTag(itemStack, Names.NBT.UUID_MOST_SIG) && !hasTag(itemStack, Names.NBT.UUID_LEAST_SIG)) {
            final UUID itemUUID = UUID.randomUUID();
            setLong(itemStack, Names.NBT.UUID_MOST_SIG, itemUUID.getMostSignificantBits());
            setLong(itemStack, Names.NBT.UUID_LEAST_SIG, itemUUID.getLeastSignificantBits());
        }
    }

    private static void initNBTTagCompound(final ItemStack itemStack) {
        if (itemStack.stackTagCompound == null) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
    }

    public static boolean hasTag(final ItemStack itemStack, final String keyName) {
        return itemStack != null && itemStack.stackTagCompound != null && itemStack.stackTagCompound.hasKey(keyName);
    }

    public static void setLong(final ItemStack itemStack, final String keyName, final long keyValue) {
        initNBTTagCompound(itemStack);

        itemStack.stackTagCompound.setLong(keyName, keyValue);
    }

    @Override
    public boolean addUpgrade(final ChestTileEntity chestTileEntity) {
        chestTileEntity.setChestLocked(true);
        return true;
    }

    @Override
    public boolean removeUpgrade(final ChestTileEntity chestTileEntity) {
        chestTileEntity.setChestLocked(false);
        return true;
    }
}
