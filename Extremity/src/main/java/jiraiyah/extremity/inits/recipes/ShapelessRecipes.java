package jiraiyah.extremity.inits.recipes;

import jiraiyah.extremity.inits.items.ItemInits;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ShapelessRecipes
{
    public static void registerShapeless()
    {
        GameRegistry.addShapelessRecipe(new ItemStack(ItemInits.GLUED_LEATHER, 1), OreDictionary.getOres("glue")
                        .get(0), Items.LEATHER);
    }
}
