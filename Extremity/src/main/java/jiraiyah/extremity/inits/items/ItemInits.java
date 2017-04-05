package jiraiyah.extremity.inits.items;

import jiraiyah.extremity.items.Bag;
import jiraiyah.extremity.items.bagUpgrades.*;
import jiraiyah.extremity.items.ingredients.*;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class ItemInits
{
    public static List<Item> ITEM_LIST = new ArrayList<>();

    public static Item GLUE;
    public static Item GLUED_LEATHER;
    public static Item BAG_FRAME;
    public static Item BAG;
    public static Item BAG_TIER_ONE_UPGRADE;
    public static Item BAG_TIER_TWO_UPGRADE;
    public static Item BAG_TIER_THREE_UPGRADE;
    public static Item BAG_TIER_FOUR_UPGRADE;
    public static Item BAG_TIER_FIVE_UPGRADE;
    public static Item BAG_TIER_SIX_UPGRADE;
    public static Item BAG_TIER_SEVEN_UPGRADE;
    public static Item BAG_TIER_EIGHT_UPGRADE;
    public static Item BAG_TRASH_UPGRADE;
    public static Item BAG_CRAFTING_UPGRADE;
    public static Item BAG_SMELTING_UPGRADE;
    public static Item CONDUCTIVE_DIAMOND;
    public static Item CONDUCTIVE_EMERALD;
    public static Item CONDUCTIVE_STAR;
    public static Item DIAMOND_CORE;
    public static Item EMERALD_CORE;
    public static Item STAR_CORE;

    public static void initialize()
    {
        GLUE = new Glue();
        CONDUCTIVE_DIAMOND = new ConductiveDiamond();
        CONDUCTIVE_EMERALD = new ConductiveEmerald();
        CONDUCTIVE_STAR = new ConductiveStar();
        DIAMOND_CORE = new DiamondCore();
        EMERALD_CORE = new EmeraldCore();
        STAR_CORE = new StarCore();
        GLUED_LEATHER = new GluedLeather();
        BAG_FRAME = new BagFrame();
        BAG = new Bag();
        BAG_TIER_ONE_UPGRADE = new TierOneUpgrade();
        BAG_TIER_TWO_UPGRADE = new TierTwoUpgrade();
        BAG_TIER_THREE_UPGRADE = new TierThreeUpgrade();
        BAG_TIER_FOUR_UPGRADE = new TierFourUpgrade();
        BAG_TIER_FIVE_UPGRADE = new TierFiveUpgrade();
        BAG_TIER_SIX_UPGRADE = new TierSixUpgrade();
        BAG_TIER_SEVEN_UPGRADE = new TierSevenUpgrade();
        BAG_TIER_EIGHT_UPGRADE = new TierEightUpgrade();
        BAG_TRASH_UPGRADE = new TrashUpgrade();
        BAG_CRAFTING_UPGRADE = new CraftingUpgrade();
        BAG_SMELTING_UPGRADE = new SmeltingUpgrade();



        ITEM_LIST.add(GLUE);
        ITEM_LIST.add(CONDUCTIVE_DIAMOND);
        ITEM_LIST.add(CONDUCTIVE_EMERALD);
        ITEM_LIST.add(CONDUCTIVE_STAR);
        ITEM_LIST.add(DIAMOND_CORE);
        ITEM_LIST.add(EMERALD_CORE);
        ITEM_LIST.add(STAR_CORE);
        ITEM_LIST.add(GLUED_LEATHER);
        ITEM_LIST.add(BAG_FRAME);
        ITEM_LIST.add(BAG);
        ITEM_LIST.add(BAG_TIER_ONE_UPGRADE);
        ITEM_LIST.add(BAG_TIER_TWO_UPGRADE);
        ITEM_LIST.add(BAG_TIER_THREE_UPGRADE);
        ITEM_LIST.add(BAG_TIER_FOUR_UPGRADE);
        ITEM_LIST.add(BAG_TIER_FIVE_UPGRADE);
        ITEM_LIST.add(BAG_TIER_SIX_UPGRADE);
        ITEM_LIST.add(BAG_TIER_SEVEN_UPGRADE);
        ITEM_LIST.add(BAG_TIER_EIGHT_UPGRADE);
        ITEM_LIST.add(BAG_TRASH_UPGRADE);
        ITEM_LIST.add(BAG_CRAFTING_UPGRADE);
        ITEM_LIST.add(BAG_SMELTING_UPGRADE);
    }
}
