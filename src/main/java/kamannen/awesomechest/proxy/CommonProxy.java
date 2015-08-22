package kamannen.awesomechest.proxy;

import cpw.mods.fml.common.network.IGuiHandler;
import kamannen.awesomechest.container.ContainerAWChest;
import kamannen.awesomechest.container.ContainerLock;
import kamannen.awesomechest.gui.EGUIs;
import kamannen.awesomechest.inventory.InventoryAWChest;
import kamannen.awesomechest.inventory.InventoryLock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler {

    public void registerTileEntitySpecialRenderer() {
    }

    @Override
    public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        if (ID == EGUIs.LOCK.ordinal()) {
            return new ContainerLock(player, new InventoryLock(player.getHeldItem()));
        } else if (ID == EGUIs.CHEST.ordinal()) {
            return new ContainerAWChest(player, new InventoryAWChest());
        } else {
            return null;
        }
    }

    @Override
    public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        return null;
    }
}
