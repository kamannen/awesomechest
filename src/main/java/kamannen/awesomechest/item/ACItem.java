package kamannen.awesomechest.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import kamannen.awesomechest.AwesomechestMod;
import kamannen.awesomechest.lib.Names;
import kamannen.awesomechest.lib.References;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ACItem extends Item {
    public ACItem() {
        super();
        setMaxStackSize(1);
        setCreativeTab(AwesomechestMod.creativeTab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(References.MOD_ID + ":" + Names.getItemName(this));
    }
}
