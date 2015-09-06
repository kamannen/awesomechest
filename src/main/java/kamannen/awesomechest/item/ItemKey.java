package kamannen.awesomechest.item;

import kamannen.awesomechest.lib.Names;
import kamannen.awesomechest.nbt.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemKey extends ACItem {

    public ItemKey() {
        super();
        setUnlocalizedName(Names.Items.KEY);
    }

    public void setLockCode(final ItemStack itemStack, final int code) {
        NBTHelper.setInteger(itemStack, Names.NBT.key, code);
    }

    @SuppressWarnings("unchecked")
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
