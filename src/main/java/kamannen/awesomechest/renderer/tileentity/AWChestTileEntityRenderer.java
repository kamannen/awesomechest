package kamannen.awesomechest.renderer.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import kamannen.awesomechest.lib.Textures;
import kamannen.awesomechest.tileentity.ChestTileEntity;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class AWChestTileEntityRenderer extends TileEntitySpecialRenderer {

    private RenderBlocks renderBlocks;
    private final ModelChest modelChest = new ModelChest();

    public AWChestTileEntityRenderer() {
    }

    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double x, final double y, final double z, final float tick) {
        if (tileEntity instanceof ChestTileEntity) {
            ChestTileEntity chestTileEntity = (ChestTileEntity) tileEntity;
            ForgeDirection direction = ForgeDirection.SOUTH;

            if (chestTileEntity.getWorldObj() != null) {
                direction = chestTileEntity.getFacingDirection();
            }

            this.bindTexture(Textures.TileEntities.AWESOME_CHEST);
            GL11.glPushMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
            GL11.glScalef(1.0F, -1.0F, -1.0F);
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            short angle = 0;

            if (direction != null) {
                if (direction == ForgeDirection.NORTH) {
                    angle = 180;
                } else if (direction == ForgeDirection.SOUTH) {
                    angle = 0;
                } else if (direction == ForgeDirection.WEST) {
                    angle = 90;
                } else if (direction == ForgeDirection.EAST) {
                    angle = -90;
                }
            }

            GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            float adjustedLidAngle = chestTileEntity.prevLidAngle + (chestTileEntity.lidAngle - chestTileEntity.prevLidAngle) * tick;
            adjustedLidAngle = 1.0F - adjustedLidAngle;
            adjustedLidAngle = 1.0F - adjustedLidAngle * adjustedLidAngle * adjustedLidAngle;
            modelChest.chestLid.rotateAngleX = -(adjustedLidAngle * (float) Math.PI / 2.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            modelChest.renderAll();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    /*@Override
    public void renderTileEntityAt(final TileEntity tile, final double x, final double y, final double z, final float partialTick) {
//        render((ChestTileEntity) tile, x, y, z, partialTick);
        super.renderTileEntityAt(tile, x, y, z, partialTick);
    }*/

    /*public void render(final ChestTileEntity tile, final double x, final double y, final double z, final float partialTick) {
        final Block block = tile.getStoredBlock();

        if (block.getMaterial() != Material.air && block.isOpaqueCube()) {
            final Tessellator tessellator = Tessellator.instance;
            this.bindTexture(TextureMap.locationBlocksTexture);
            RenderHelper.disableStandardItemLighting();
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_CULL_FACE);

            if (Minecraft.isAmbientOcclusionEnabled()) {
                GL11.glShadeModel(GL11.GL_SMOOTH);
            } else {
                GL11.glShadeModel(GL11.GL_FLAT);
            }

            tessellator.startDrawingQuads();
            tessellator.setTranslation((double) ((float) x - (float) tile.xCoord), (double) ((float) y - (float) tile.yCoord), (double) ((float) z - (float) tile.zCoord));
            tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);

            this.renderBlocks.renderBlockAllFaces(block, tile.xCoord, tile.yCoord, tile.zCoord);

            tessellator.setTranslation(0.0D, 0.0D, 0.0D);
            tessellator.draw();
            RenderHelper.enableStandardItemLighting();
        }
    }*/

    @Override
    public void func_147496_a(final World world) {
        this.renderBlocks = new RenderBlocks(world);
    }
}
