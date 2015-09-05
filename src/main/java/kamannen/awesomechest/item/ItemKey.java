package kamannen.awesomechest.item;

import kamannen.awesomechest.lib.Names;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class ItemKey extends ACItem {

    private int lockCode;

    public ItemKey() {
        super();
        this.lockCode = new Random().nextInt(Integer.MAX_VALUE - (Integer.MAX_VALUE / 2)) + (Integer.MAX_VALUE / 2);
        setUnlocalizedName(Names.Items.KEY);
    }

    public ItemKey setLockCode(final int code) {
        lockCode = code;
        return this;
    }

    @Override
    public void onCreated(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        itemStack.stackTagCompound = new NBTTagCompound();
        itemStack.stackTagCompound.setInteger(Names.NBT.key, lockCode);
    }

    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer player,
                               final List list, final boolean b) {
        final NBTTagCompound tagCompound = itemStack.getTagCompound();
        if (tagCompound != null) {
            final int code = tagCompound.getInteger(Names.NBT.key);
            list.add(EnumChatFormatting.AQUA + StatCollector.translateToLocalFormatted("key.lock.id.message", code));
        }
    }
}
