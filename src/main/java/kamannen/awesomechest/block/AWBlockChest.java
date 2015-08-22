package kamannen.awesomechest.block;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import kamannen.awesomechest.AwesomechestMod;
import kamannen.awesomechest.gui.EGUIs;
import kamannen.awesomechest.lib.Names;
import kamannen.awesomechest.tileentity.ChestTileEntity;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class AWBlockChest extends BlockChest {
    protected AWBlockChest() {
        super(0);
        this.setCreativeTab(AwesomechestMod.creativeTab);
        this.setBlockName(Names.Blocks.AWESOME_CHEST);
        this.setHardness(1.0F);
        this.setResistance(5.0F);
        //this.setBlockTextureName(References.MOD_ID + ":" + Names.getBlockName(this));
    }

    @Override
    public boolean isOpaqueCube() {
        return true;
    }

    @Override
    public void onBlockPlacedBy(final World world, final int x, final int y, final int z, final EntityLivingBase livingBase, final ItemStack itemStack) {
        super.onBlockPlacedBy(world, x, y, z, livingBase, itemStack);
        if (livingBase instanceof EntityPlayer) {
            ((ChestTileEntity) world.getTileEntity(x, y, z)).setPlayer(((EntityPlayer) livingBase).getDisplayName());
        }
    }

    @Override
    public TileEntity createNewTileEntity(final World world, final int metadata) {
        return new ChestTileEntity();
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean onBlockActivated(final World world, final int x, final int y, final int z,
                                    final EntityPlayer entityPlayer, final int i1,
                                    final float f1, final float f2, final float f3) {
        if (!world.isRemote) {
            FMLNetworkHandler.openGui(entityPlayer, AwesomechestMod.instance, EGUIs.CHEST.ordinal(), world, x, y, z);
        }

        return true;
    }
}
