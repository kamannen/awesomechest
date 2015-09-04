package kamannen.awesomechest.lib;

import net.minecraft.util.ResourceLocation;

public class Textures {

    public static class GUI {
        private static final String GUI_TEXTURE_LOCATION = "textures/gui/";

        public static ResourceLocation getAwesomeChestGUI(final int chestSize) {
            return new ResourceLocation(References.MOD_ID,
                    GUI_TEXTURE_LOCATION + "chest_inventory_" +
                            (chestSize - Values.Entities.CHEST_INVENTORY_SIZE_UPGRADES) + ".png");
        }
        public static final ResourceLocation AWESOME_CHEST_GUI_SMALL = new ResourceLocation(References.MOD_ID, GUI_TEXTURE_LOCATION + "chest_inventory_27.png");
        public static final ResourceLocation AWESOME_CHEST_GUI_LARGE = new ResourceLocation(References.MOD_ID, GUI_TEXTURE_LOCATION + "chest_inventory_54.png");
        public static final ResourceLocation KEY_GUI = new ResourceLocation(References.MOD_ID, GUI_TEXTURE_LOCATION + "inventory.png");

    }

    public static class TileEntities {
        private static final String ENTITY_TEXTURE_LOCATION = "textures/entity/";
        public static final ResourceLocation AWESOME_CHEST = new ResourceLocation(References.MOD_ID, ENTITY_TEXTURE_LOCATION + "awesomeChest.png");
    }

}
