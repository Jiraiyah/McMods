package jiraiyah.extremity.items.ingredients;

import jiraiyah.extremity.Extremity;
import jiraiyah.extremity.references.Names;
import jiraiyah.extremity.references.Reference;
import net.minecraft.item.Item;

public class StarCore extends Item
{
    public StarCore()
    {
        setUnlocalizedName(Reference.MOD_ID.toLowerCase() + "." + Names.STAR_CORE_NAME.toLowerCase());
        setRegistryName(Names.STAR_CORE_NAME);
        setMaxStackSize(64);
        setCreativeTab(Extremity.CREATIVE_TAB);
    }
}
