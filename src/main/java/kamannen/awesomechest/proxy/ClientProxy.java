package kamannen.awesomechest.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import kamannen.awesomechest.block.ModBlocks;
import kamannen.awesomechest.gui.EGUIs;
import kamannen.awesomechest.gui.GUIAWChest;
import kamannen.awesomechest.gui.GUILock;
import kamannen.awesomechest.inventory.InventoryLock;
import kamannen.awesomechest.lib.RenderIDs;
import kamannen.awesomechest.renderer.item.ItemRendererAWChest;
import kamannen.awesomechest.renderer.tileentity.AWChestTileEntityRenderer;
import kamannen.awesomechest.tileentity.ChestTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerTileEntitySpecialRenderer() {
        RenderIDs.awesomeChest = RenderingRegistry.getNextAvailableRenderId();

        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.AW_BLOCK_CHEST), new ItemRendererAWChest());

        ClientRegistry.bindTileEntitySpecialRenderer(ChestTileEntity.class, new AWChestTileEntityRenderer());
    }

    @Override
    public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        if (ID == EGUIs.LOCK.ordinal()) {
            return new GUILock(player, new InventoryLock(player.getHeldItem()));
        } else if (ID == EGUIs.CHEST.ordinal()) {
            final TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity instanceof ChestTileEntity) {
                return new GUIAWChest(player, (ChestTileEntity) tileEntity);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
