package kamannen.awesomechest.item;

import kamannen.awesomechest.AwesomechestMod;
import kamannen.awesomechest.gui.EGUIs;
import kamannen.awesomechest.lib.Names;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class ItemLock extends ACItem {

    private boolean CONTAINS_KEY;

    public ItemLock() {
        super();
        CONTAINS_KEY = true;
        // todo: create key item and place in lock inventory


        setUnlocalizedName(Names.Items.LOCK);
    }

    @Override
    public void onCreated(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        //TODO: remove this method?
        itemStack.stackTagCompound = new NBTTagCompound();
        itemStack.stackTagCompound.setString(Names.NBT.key, "TODO: Replace this.");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer player,
                               final List list, final boolean b) {

        if (CONTAINS_KEY) {
            list.add(EnumChatFormatting.GREEN + "Contains key");
        }
    }

    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (!world.isRemote && !entityPlayer.isSneaking()) {
            setUUID(itemStack);
            entityPlayer.openGui(AwesomechestMod.instance, EGUIs.LOCK.ordinal(), world, (int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ);
        }

        return itemStack;
    }

    /*@Override
    public boolean onItemUse(final ItemStack stack, final EntityPlayer player, final World world, final int x, final int y, final int z, final int side, final float hitX, final float hitY, final float hitZ) {
        if (!world.isRemote) {
            if (stack != null && player.isSneaking()) {
                final TileEntity tileEntity = world.getTileEntity(x, y, z);
                if (stack.getItem() instanceof ItemLock) {
                    final NBTTagList nbttaglist = stack.stackTagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
                    if (nbttaglist != null) {
                        final NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(0);
                        final ItemStack itemStack = ItemStack.loadItemStackFromNBT(nbttagcompound1);
                        if (itemStack.getItem() instanceof ItemBlock) {
//                            final HCChestTileEntity hcChestTileEntity = new HCChestTileEntity(tileEntity, ((ItemBlock) itemStack.getItem()).field_150939_a);
                            world.setBlock(x, y, z, ModBlocks.blockChest);
                            final TileEntity tileEntity1 = world.getTileEntity(x, y, z);
                            if (tileEntity1 instanceof ChestTileEntity) {
                                ((ChestTileEntity) tileEntity1).updateHiddenInfo(tileEntity, ((ItemBlock) itemStack.getItem()).field_150939_a);
                            }
//                            world.setTileEntity(x, y, z, hcChestTileEntity);
//                            hcChestTileEntity.updateContainingBlockInfo();
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }*/

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
}
