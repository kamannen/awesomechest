package kamannen.awesomechest.tab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class AWCreativeTab extends CreativeTabs {

    public AWCreativeTab(final String lable) {
        super(lable);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem() {
        return Items.baked_potato;
    }
}
