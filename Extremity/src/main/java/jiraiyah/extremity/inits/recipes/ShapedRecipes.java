package jiraiyah.extremity.inits.recipes;

import jiraiyah.extremity.inits.items.ItemInits;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ShapedRecipes
{
    public static void registerShaped()
    {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemInits.CONDUCTIVE_DIAMOND, 1),
        "aca",
                "cbc",
                "aca",
                'a', "dustRedstone",
                'b', "gemDiamond",
                'c', "gemLapis"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemInits.CONDUCTIVE_EMERALD, 1),
        "aca",
                "cbc",
                "aca",
                'a', "dustRedstone",
                'b', "gemEmerald",
                'c', "gemLapis"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemInits.CONDUCTIVE_DIAMOND, 1),
                "aca",
                "cbc",
                "aca",
                'a', "gemLapis",
                'b', "gemDiamond",
                'c', "dustRedstone"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemInits.CONDUCTIVE_EMERALD, 1),
                "aca",
                "cbc",
                "aca",
                'a', "gemLapis",
                'b', "gemEmerald",
                'c', "dustRedstone"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemInits.BAG_FRAME, 1),
        "aba",
                "bcb",
                "aba",
                'a', "ingotIron",
                'b', "stickWood",
                'c', ItemInits.GLUED_LEATHER));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemInits.BAG, 1),
        "aaa",
                "aba",
                "aaa",
                'a', ItemInits.BAG_FRAME,
                'b', "chest"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemInits.BAG_TIER_ONE_UPGRADE, 1),
        "aaa",
                "aba",
                "aaa",
                'a', "stickWood",
                'b', "plankWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemInits.BAG_TIER_TWO_UPGRADE, 1),
        "aba",
                "bcb",
                "aba",
                'a', "ingotIron",
                'b', "ingotGold",
                'c', "chest"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemInits.BAG_TIER_TWO_UPGRADE, 1),
        "aba",
                "bcb",
                "aba",
                'a', "ingotGold",
                'b', "ingotIron",
                'c', "chest"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemInits.CONDUCTIVE_STAR, 1),
        "aba",
                "bcb",
                "aba",
                'a', "gemDiamond",
                'b', "gemEmerald",
                'c', "netherStar"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemInits.CONDUCTIVE_STAR, 1),
                "aba",
                "bcb",
                "aba",
                'a', "gemEmerald",
                'b', "gemDiamond",
                'c', "netherStar"));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemInits.BAG_TIER_THREE_UPGRADE, 1),
        "aaa",
                "aba",
                "aaa",
                'a', ItemInits.DIAMOND_CORE,
                'b', ItemInits.EMERALD_CORE));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemInits.BAG_TIER_FOUR_UPGRADE, 1),
        "aaa",
                "aba",
                "aaa",
                'a', ItemInits.EMERALD_CORE,
                'b', "netherStar"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemInits.BAG_TIER_FIVE_UPGRADE, 1),
        "aaa",
                "aba",
                "aaa",
                'a', "netherStar",
                'b', ItemInits.STAR_CORE));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemInits.BAG_TIER_SIX_UPGRADE, 1),
        "aaa",
                "aba",
                "aaa",
                'a', ItemInits.STAR_CORE,
                'b', "blockLapis"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemInits.BAG_TIER_SEVEN_UPGRADE, 1),
        "aaa",
                "aba",
                "aaa",
                'a', new ItemStack(Blocks.SEA_LANTERN),
                'b', ItemInits.STAR_CORE));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemInits.BAG_TIER_EIGHT_UPGRADE, 1),
        "aaa",
                "aba",
                "aaa",
                'a', ItemInits.STAR_CORE,
                'b', Item.getItemFromBlock(Blocks.DRAGON_EGG).setContainerItem(Item.getItemFromBlock(Blocks
                .DRAGON_EGG))));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemInits.BAG_TRASH_UPGRADE, 1),
                "aaa",
                "aba",
                "aaa",
                'a', "ingotIron",
                'b', Items.LAVA_BUCKET.setContainerItem(Items.BUCKET)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemInits.BAG_CRAFTING_UPGRADE, 1),
                "aaa",
                "aba",
                "aaa",
                'a', "workbench",
                'b', "chest"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemInits.BAG_SMELTING_UPGRADE, 1),
                "aba",
                "bcb",
                "aba",
                'a', Items.MAGMA_CREAM,
                'b', ItemInits.STAR_CORE,
                'c', Item.getItemFromBlock(Blocks.DRAGON_EGG).setContainerItem(Item.getItemFromBlock(Blocks
                .DRAGON_EGG))));
        //OreDictionary
    }
}
