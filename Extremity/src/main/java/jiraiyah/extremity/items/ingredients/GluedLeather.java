package jiraiyah.extremity.items.ingredients;

import jiraiyah.extremity.Extremity;
import jiraiyah.extremity.references.Names;
import jiraiyah.extremity.references.Reference;
import net.minecraft.item.Item;

public class GluedLeather extends Item
{
    public GluedLeather()
    {
        setUnlocalizedName(Reference.MOD_ID.toLowerCase() + "." + Names.GLUED_LEATHER_NAME.toLowerCase());
        setRegistryName(Names.GLUED_LEATHER_NAME);
        setMaxStackSize(1);
        setCreativeTab(Extremity.CREATIVE_TAB);
    }
}
