package jiraiyah.extremity.items.bagUpgrades;

import jiraiyah.extremity.Extremity;
import jiraiyah.extremity.infrastructures.BagUpgradeItemBase;
import jiraiyah.extremity.references.Names;
import jiraiyah.extremity.references.Reference;

public class TrashUpgrade extends BagUpgradeItemBase
{
    public TrashUpgrade()
    {
        setUnlocalizedName(Reference.MOD_ID.toLowerCase() + "." + Names.TRASH_UPGRADE_NAME.toLowerCase());
        setRegistryName(Names.TRASH_UPGRADE_NAME);
        setMaxStackSize(64);
        setCreativeTab(Extremity.CREATIVE_TAB);
    }

    @Override
    public int getID()
    {
        return 9;
    }
}
