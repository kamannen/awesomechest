package kamannen.awesomechest.renderer.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import kamannen.awesomechest.tileentity.ChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class AWChestTileEntityRenderer extends TileEntitySpecialRenderer {

    private RenderBlocks renderBlocks;

    public AWChestTileEntityRenderer() {
    }

    @Override
    public void renderTileEntityAt(final TileEntity p_147500_1_, final double p_147500_2_, final double p_147500_4_, final double p_147500_6_, final float p_147500_8_) {

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
