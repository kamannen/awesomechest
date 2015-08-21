package kamannen.awesomechest.block;

import kamannen.awesomechest.AwesomechestMod;
import kamannen.awesomechest.lib.Names;
import kamannen.awesomechest.tileentity.ChestTileEntity;
import net.minecraft.block.BlockChest;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
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

    /*@Override
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer player, final int i1, final float f1, final float f2, final float f3) {
        final TileEntity te = world.getTileEntity(i, j, k);

        if (world.isRemote) {
            return true;
        }

        if (te != null && te instanceof IInventory) {
            player.displayGUIChest((IInventory) te);
        }

        return true;
    }*/
}
