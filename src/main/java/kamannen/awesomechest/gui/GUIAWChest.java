package kamannen.awesomechest.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import kamannen.awesomechest.container.ContainerAWChest;
import kamannen.awesomechest.lib.Textures;
import kamannen.awesomechest.lib.Values;
import kamannen.awesomechest.tileentity.ChestTileEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GUIAWChest extends GuiContainer {

    private final EntityPlayer player;
    private final ChestTileEntity chestTileEntity;

    public GUIAWChest(final EntityPlayer player, final ChestTileEntity tileEntity) {
        super(new ContainerAWChest(player, tileEntity));
        this.chestTileEntity = tileEntity;
        this.player = player;
        this.xSize = 176;
        this.ySize = 168;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(final int a, final int b) {

        final String inventoryName = this.chestTileEntity.hasCustomInventoryName() ? this.chestTileEntity.getInventoryName() : I18n.format(this.chestTileEntity.getInventoryName());

        if (this.fontRendererObj.getStringWidth(inventoryName) >= this.ySize) {
            GL11.glPushMatrix();
            GL11.glScalef(0.7F, 0.9F, 0.7F);
            this.fontRendererObj.drawString(inventoryName, 8, 6, 4210752);
            GL11.glPopMatrix();
        } else {
            this.fontRendererObj.drawString(inventoryName, 8, 6, 4210752);
        }

        this.fontRendererObj.drawString(this.player.inventory.hasCustomInventoryName() ? this.player.inventory.getInventoryName() : I18n.format(this.player.inventory.getInventoryName()), 8, this.ySize - 96 + 2, 4210752);

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(final float a, final int b, final int c) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int chestSize = Values.Entities.CHEST_INVENTORY_SIZE_SMALL;
        if (super.inventorySlots instanceof ContainerAWChest) {
            chestSize = ((ContainerAWChest) super.inventorySlots).getMyInventory().getSizeInventory();
        }
        this.mc.getTextureManager().bindTexture(Textures.GUI.getAwesomeChestGUI(chestSize));
        final int x = (width - xSize) / 2;
        final int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
}
