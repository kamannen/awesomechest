package kamannen.awesomechest.item;

import cpw.mods.fml.common.registry.GameRegistry;
import kamannen.awesomechest.lib.Names;

public class ModItems {

    public static ACItem itemLock = new ItemLock();
    public static ACItem itemKey = new ItemKey();

    public static void init() {
        GameRegistry.registerItem(itemLock, Names.Items.LOCK);
        GameRegistry.registerItem(itemKey, Names.Items.KEY);
    }
}
