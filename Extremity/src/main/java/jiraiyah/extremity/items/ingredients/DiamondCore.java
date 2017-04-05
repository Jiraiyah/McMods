package jiraiyah.extremity.items.ingredients;

import jiraiyah.extremity.Extremity;
import jiraiyah.extremity.references.Names;
import jiraiyah.extremity.references.Reference;
import net.minecraft.item.Item;

public class DiamondCore extends Item
{
    public DiamondCore()
    {
        setUnlocalizedName(Reference.MOD_ID.toLowerCase() + "." + Names.DIAMOND_CORE_NAME.toLowerCase());
        setRegistryName(Names.DIAMOND_CORE_NAME);
        setMaxStackSize(64);
        setCreativeTab(Extremity.CREATIVE_TAB);
    }
}
