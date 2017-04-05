package jiraiyah.extremity.inits.recipes;

import static jiraiyah.extremity.inits.recipes.ShapedRecipes.registerShaped;
import static jiraiyah.extremity.inits.recipes.ShapelessRecipes.registerShapeless;
import static jiraiyah.extremity.inits.recipes.SmeltingRecipes.registerSmelting;

public class RecipeRegisters
{
    public static void register()
    {
        registerShaped();
        registerShapeless();
        registerSmelting();
    }
}
