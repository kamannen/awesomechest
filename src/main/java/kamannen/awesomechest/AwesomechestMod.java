package kamannen.awesomechest;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import kamannen.awesomechest.block.ModBlocks;
import kamannen.awesomechest.item.ModItems;
import kamannen.awesomechest.lib.Names;
import kamannen.awesomechest.lib.References;
import kamannen.awesomechest.proxy.CommonProxy;
import kamannen.awesomechest.recipes.ModRecipes;
import kamannen.awesomechest.tab.AWCreativeTab;
import kamannen.awesomechest.tileentity.ChestTileEntity;

@Mod(modid = References.MOD_ID, name = References.MOD_NAME, version = References.VERSION)
public class AwesomechestMod {

    @Mod.Instance(References.MOD_ID)
    public static AwesomechestMod instance;

    @SidedProxy(clientSide = References.CLIENTPROXY_PATH, serverSide = References.COMMONPROXY_PATH)
    public static CommonProxy proxy;

    public static AWCreativeTab creativeTab = new AWCreativeTab(References.MOD_NAME);

    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        ModBlocks.init();
        ModItems.init();
    }

    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
        ModRecipes.init();
        GameRegistry.registerTileEntity(ChestTileEntity.class, Names.Blocks.AWESOME_CHEST);
        proxy.registerTileEntitySpecialRenderer();
    }

    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
    }
}
