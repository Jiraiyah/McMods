package jiraiyah.extremity.items.ingredients;

import jiraiyah.extremity.Extremity;
import jiraiyah.extremity.references.Names;
import jiraiyah.extremity.references.Reference;
import net.minecraft.item.Item;

public class ConductiveEmerald extends Item
{
    public ConductiveEmerald()
    {
        setUnlocalizedName(Reference.MOD_ID.toLowerCase() + "." + Names.CONDUCTIVE_EMERALD_NAME.toLowerCase());
        setRegistryName(Names.CONDUCTIVE_EMERALD_NAME);
        setMaxStackSize(8);
        setCreativeTab(Extremity.CREATIVE_TAB);
    }
}
