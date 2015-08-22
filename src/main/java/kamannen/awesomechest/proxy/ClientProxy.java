package kamannen.awesomechest.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import kamannen.awesomechest.gui.EGUIs;
import kamannen.awesomechest.gui.GUIAWChest;
import kamannen.awesomechest.gui.GUILock;
import kamannen.awesomechest.inventory.InventoryAWChest;
import kamannen.awesomechest.inventory.InventoryLock;
import kamannen.awesomechest.renderer.tileentity.AWChestTileEntityRenderer;
import kamannen.awesomechest.tileentity.ChestTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerTileEntitySpecialRenderer() {
        ClientRegistry.bindTileEntitySpecialRenderer(ChestTileEntity.class, new AWChestTileEntityRenderer());
    }

    @Override
    public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        if (ID == EGUIs.LOCK.ordinal()) {
            return new GUILock(player, new InventoryLock(player.getHeldItem()));
        } else if (ID == EGUIs.CHEST.ordinal()) {
            final TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity instanceof ChestTileEntity) {
                return new GUIAWChest(player, new InventoryAWChest(), (ChestTileEntity) tileEntity);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
