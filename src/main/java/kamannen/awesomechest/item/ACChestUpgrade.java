package kamannen.awesomechest.item;

import kamannen.awesomechest.tileentity.ChestTileEntity;

public interface ACChestUpgrade {

    boolean addUpgrade(final ChestTileEntity chestTileEntity);

    boolean removeUpgrade(final ChestTileEntity chestTileEntity);

}
