package jiraiyah.extremity.inits.recipes;

import jiraiyah.extremity.inits.items.ItemInits;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class SmeltingRecipes
{
    public static void registerSmelting()
    {
        ItemStack slimeBall = OreDictionary.getOres("slimeball").get(0);
        GameRegistry.addSmelting(slimeBall, new ItemStack(ItemInits.GLUE, 1), 2f);
        GameRegistry.addSmelting(new ItemStack(ItemInits.CONDUCTIVE_DIAMOND), new ItemStack(ItemInits.DIAMOND_CORE,
                1), 5f);
        GameRegistry.addSmelting(new ItemStack(ItemInits.CONDUCTIVE_EMERALD), new ItemStack(ItemInits.EMERALD_CORE,
                1), 5f);
        GameRegistry.addSmelting(new ItemStack(ItemInits.CONDUCTIVE_STAR), new ItemStack(ItemInits.STAR_CORE,
                1), 5f);
    }
}
