package kamannen.awesomechest.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import kamannen.awesomechest.container.ContainerAWChest;
import kamannen.awesomechest.inventory.InventoryAWChest;
import kamannen.awesomechest.lib.References;
import kamannen.awesomechest.lib.Values;
import kamannen.awesomechest.tileentity.ChestTileEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GUIAWChest extends GuiContainer {

    private final InventoryAWChest inventoryAWChest;
    private final EntityPlayer player;
    private final ChestTileEntity chestTileEntity;

    public GUIAWChest(final EntityPlayer player, final InventoryAWChest inventory, final ChestTileEntity tileEntity) {
        super(new ContainerAWChest(player, inventory));
        this.inventoryAWChest = inventory;
        this.chestTileEntity = tileEntity;
        this.player = player;
        this.xSize = 176;
        this.ySize = 168;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(final int a, final int b) {
        this.fontRendererObj.drawString(this.chestTileEntity.hasCustomInventoryName() ? this.chestTileEntity.getInventoryName() : I18n.format(this.chestTileEntity.getInventoryName()), 8, 6, 4210752);
        this.fontRendererObj.drawString(this.player.inventory.hasCustomInventoryName() ? this.player.inventory.getInventoryName() : I18n.format(this.player.inventory.getInventoryName()), 8, this.ySize - 96 + 2, 4210752);

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(final float a, final int b, final int c) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int chestSize = Values.Entities.CHEST_INVENTORY_SIZE_SMALL;
        if (super.inventorySlots instanceof ContainerAWChest) {
            chestSize = ((ContainerAWChest) super.inventorySlots).getInventoryAWChest().getSizeInventory();
        }
        this.mc.getTextureManager().bindTexture(new ResourceLocation(References.MOD_ID,
                "textures/gui/chest_inventory_" + chestSize + ".png"));
        final int x = (width - xSize) / 2;
        final int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
}
