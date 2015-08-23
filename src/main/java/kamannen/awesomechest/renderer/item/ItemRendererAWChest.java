package kamannen.awesomechest.renderer.item;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import kamannen.awesomechest.lib.Textures;
import net.minecraft.client.model.ModelChest;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ItemRendererAWChest implements IItemRenderer {

    private final ModelChest modelChest;

    public ItemRendererAWChest()
    {
        modelChest = new ModelChest();
    }

    @Override
    public boolean handleRenderType(final ItemStack item, final ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(final ItemRenderType type, final ItemStack item, final ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(final ItemRenderType type, final ItemStack itemStack, final Object... data) {
        switch (type) {
            case ENTITY: {
                renderChest(0.5F, 0.5F, 0.5F);
                break;
            }
            case EQUIPPED: {
                renderChest(1.0F, 1.0F, 1.0F);
                break;
            }
            case EQUIPPED_FIRST_PERSON: {
                renderChest(1.0F, 1.0F, 1.0F);
                break;
            }
            case INVENTORY: {
                renderChest(0.0F, 0.075F, 0.0F);
                break;
            }
            default:
                break;
        }
    }

    private void renderChest(float x, float y, float z) {

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.TileEntities.AWESOME_CHEST);

        GL11.glPushMatrix(); //start
        GL11.glTranslatef(x, y, z); //size
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glRotatef(-90, 0, 1, 0);
        modelChest.renderAll();
        GL11.glPopMatrix(); //end
    }
}
