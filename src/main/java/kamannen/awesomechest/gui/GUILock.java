package kamannen.awesomechest.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import kamannen.awesomechest.container.ContainerLock;
import kamannen.awesomechest.inventory.InventoryLock;
import kamannen.awesomechest.lib.References;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;


@SideOnly(Side.CLIENT)
public class GUILock extends GuiContainer {

    public GUILock(final EntityPlayer player, final InventoryLock inventory) {
        super(new ContainerLock(player, inventory));
        this.allowUserInput = false;
        xSize = ySize = 184;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(final float a, final int b, final int c) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation(References.MOD_ID, "textures/gui/inventory.png"));
        final int x = (width - xSize) / 2;
        final int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
}
