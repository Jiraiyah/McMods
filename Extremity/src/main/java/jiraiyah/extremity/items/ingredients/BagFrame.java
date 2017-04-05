package jiraiyah.extremity.items.ingredients;

import jiraiyah.extremity.Extremity;
import jiraiyah.extremity.references.Names;
import jiraiyah.extremity.references.Reference;
import net.minecraft.item.Item;

public class BagFrame extends Item
{
    public BagFrame()
    {
        setUnlocalizedName(Reference.MOD_ID.toLowerCase() + "." + Names.BAG_FRAME_NAME.toLowerCase());
        setRegistryName(Names.BAG_FRAME_NAME);
        setMaxStackSize(8);
        setCreativeTab(Extremity.CREATIVE_TAB);
    }
}
