package jiraiyah.extremity.items.ingredients;

import jiraiyah.extremity.Extremity;
import jiraiyah.extremity.references.Names;
import jiraiyah.extremity.references.Reference;
import net.minecraft.item.Item;

public class EmeraldCore extends Item
{
    public EmeraldCore()
    {
        setUnlocalizedName(Reference.MOD_ID.toLowerCase() + "." + Names.EMERALD_CORE_NAME.toLowerCase());
        setRegistryName(Names.EMERALD_CORE_NAME);
        setMaxStackSize(64);
        setCreativeTab(Extremity.CREATIVE_TAB);
    }
}
