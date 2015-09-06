package kamannen.awesomechest.item.crafting;

import kamannen.awesomechest.lib.Names;
import kamannen.awesomechest.nbt.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;

import java.util.HashMap;
import java.util.Map;

public class LockRecipe extends ShapedRecipes {
    public LockRecipe(final int recipeWidth, final int recipeHeight, final ItemStack[] recipeItems, final ItemStack recipeOutput) {
        super(recipeWidth, recipeHeight, recipeItems, recipeOutput);
    }

    @Override
    public ItemStack getCraftingResult(final InventoryCrafting inventoryCrafting) {
        ItemStack resultingItemStack = this.getRecipeOutput().copy();

        NBTHelper.setInteger(resultingItemStack, Names.NBT.key, NBTHelper.createKeyCode());
        NBTHelper.setBoolean(resultingItemStack, Names.NBT.NEW, true);
        return resultingItemStack.copy();
    }


    /* Copied from CraftingManager.addRecipe (with slight modifications) */
    public static LockRecipe addRecipe(ItemStack recipeOutput, Object... parameters) {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;

        if (parameters[i] instanceof String[]) {
            String[] astring = (String[]) parameters[i++];

            for (String s1 : astring) {
                ++k;
                j = s1.length();
                s = s + s1;
            }
        } else {
            while (parameters[i] instanceof String) {
                String s2 = (String) parameters[i++];
                ++k;
                j = s2.length();
                s = s + s2;
            }
        }

        Map<Character, ItemStack> hashmap;

        for (hashmap = new HashMap<>(); i < parameters.length; i += 2) {
            Character character = (Character) parameters[i];
            ItemStack itemstack1 = null;

            if (parameters[i + 1] instanceof Item) {
                itemstack1 = new ItemStack((Item) parameters[i + 1]);
            } else if (parameters[i + 1] instanceof Block) {
                itemstack1 = new ItemStack((Block) parameters[i + 1], 1, 32767);
            } else if (parameters[i + 1] instanceof ItemStack) {
                itemstack1 = (ItemStack) parameters[i + 1];
            }

            hashmap.put(character, itemstack1);
        }

        ItemStack[] aitemstack = new ItemStack[j * k];

        for (int i1 = 0; i1 < j * k; ++i1) {
            char c0 = s.charAt(i1);

            if (hashmap.containsKey(c0)) {
                aitemstack[i1] = hashmap.get(c0).copy();
            } else {
                aitemstack[i1] = null;
            }
        }

        return new LockRecipe(j, k, aitemstack, recipeOutput);
    }

}
