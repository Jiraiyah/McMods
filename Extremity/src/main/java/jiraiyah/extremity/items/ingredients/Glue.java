package jiraiyah.extremity.items.ingredients;

import jiraiyah.extremity.Extremity;
import jiraiyah.extremity.references.Names;
import jiraiyah.extremity.references.Reference;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class Glue extends Item
{
    public Glue()
    {
        setUnlocalizedName(Reference.MOD_ID.toLowerCase() + "." + Names.GLUE_NAME.toLowerCase());
        setRegistryName(Names.GLUE_NAME);
        setMaxStackSize(64);
        setCreativeTab(Extremity.CREATIVE_TAB);
    }
}
