package kamannen.awesomechest.block;

import cpw.mods.fml.common.registry.GameRegistry;
import kamannen.awesomechest.lib.Names;

public class ModBlocks {

    public static final AWBlockChest AW_BLOCK_CHEST = new AWBlockChest();

    public static void init() {
        GameRegistry.registerBlock(AW_BLOCK_CHEST, Names.Blocks.AWESOME_CHEST);
    }
}
