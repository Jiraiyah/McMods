package jiraiyah.extremity.items.bagUpgrades;

import jiraiyah.extremity.Extremity;
import jiraiyah.extremity.infrastructures.BagUpgradeItemBase;
import jiraiyah.extremity.references.Names;
import jiraiyah.extremity.references.Reference;

public class CraftingUpgrade extends BagUpgradeItemBase
{
    public CraftingUpgrade()
    {
        setUnlocalizedName(Reference.MOD_ID.toLowerCase() + "." + Names.CRAFTING_UPGRADE_NAME.toLowerCase());
        setRegistryName(Names.CRAFTING_UPGRADE_NAME);
        setMaxStackSize(64);
        setCreativeTab(Extremity.CREATIVE_TAB);
    }

    @Override
    public int getID()
    {
        return 9;
    }
}
