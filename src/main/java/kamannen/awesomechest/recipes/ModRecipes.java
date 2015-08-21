package kamannen.awesomechest.recipes;

import cpw.mods.fml.common.registry.GameRegistry;
import kamannen.awesomechest.block.ModBlocks;
import kamannen.awesomechest.item.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModRecipes {
    public static void init() {
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemLock), Blocks.stone, new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.AW_BLOCK_CHEST), "xxx", "x x", "xxx", 'x', new ItemStack(Blocks.log, 1, OreDictionary.WILDCARD_VALUE));
    }
}
