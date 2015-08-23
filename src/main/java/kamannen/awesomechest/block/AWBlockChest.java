package kamannen.awesomechest.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import kamannen.awesomechest.AwesomechestMod;
import kamannen.awesomechest.gui.EGUIs;
import kamannen.awesomechest.lib.Names;
import kamannen.awesomechest.lib.References;
import kamannen.awesomechest.lib.RenderIDs;
import kamannen.awesomechest.tileentity.ChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class AWBlockChest extends BlockContainer {
    protected AWBlockChest() {
        super(Material.wood);
        this.setCreativeTab(AwesomechestMod.creativeTab);
        this.setBlockName(Names.Blocks.AWESOME_CHEST);
        this.setHardness(1.0F);
        this.setResistance(5.0F);
        //this.setBlockTextureName(References.MOD_ID + ":" + Names.getBlockName(this));
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void onBlockPlacedBy(final World world, final int x, final int y, final int z, final EntityLivingBase livingBase, final ItemStack itemStack) {
        super.onBlockPlacedBy(world, x, y, z, livingBase, itemStack);
        if (livingBase instanceof EntityPlayer) {
            int facing = MathHelper.floor_double(livingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
            ForgeDirection direction = ForgeDirection.SOUTH;

            if (facing == 0) {
                direction = ForgeDirection.NORTH;
            } else if (facing == 1) {
                direction = ForgeDirection.EAST;
            } else if (facing == 2) {
                direction = ForgeDirection.SOUTH;
            } else if (facing == 3) {
                direction = ForgeDirection.WEST;
            }
            ((ChestTileEntity) world.getTileEntity(x, y, z))
                    .setPlayer(((EntityPlayer) livingBase).getDisplayName())
                    .setFacingDirection(direction);
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, block, meta);
    }

    protected void dropInventory(World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (!(tileEntity instanceof IInventory)) {
            return;
        }

        IInventory inventory = (IInventory) tileEntity;

        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack itemStack = inventory.getStackInSlot(i);

            if (itemStack != null && itemStack.stackSize > 0) {
                Random rand = new Random();

                float dX = rand.nextFloat() * 0.8F + 0.1F;
                float dY = rand.nextFloat() * 0.8F + 0.1F;
                float dZ = rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, itemStack.copy());

                if (itemStack.hasTagCompound()) {
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
                }

                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                itemStack.stackSize = 0;
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(final World world, final int metadata) {
        return new ChestTileEntity();
    }

    @Override
    public int getRenderType() {
        return RenderIDs.awesomeChest;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World world, final int x, final int y, final int z) {
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int x, final int y, final int z) {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public boolean onBlockActivated(final World world, final int x, final int y, final int z,
                                    final EntityPlayer entityPlayer, final int i1,
                                    final float f1, final float f2, final float f3) {
        if (!world.isRemote) {
            entityPlayer.openGui(AwesomechestMod.instance, EGUIs.CHEST.ordinal(), world, x, y, z);
        }

        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        blockIcon = iconRegister.registerIcon(References.MOD_ID + ":" + Names.getBlockName(this));
    }
}
