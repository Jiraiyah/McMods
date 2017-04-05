package jiraiyah.extremity.items.bagUpgrades;

import jiraiyah.extremity.Extremity;
import jiraiyah.extremity.infrastructures.BagUpgradeItemBase;
import jiraiyah.extremity.references.Names;
import jiraiyah.extremity.references.Reference;

public class TierFiveUpgrade extends BagUpgradeItemBase
{
    public TierFiveUpgrade()
    {
        setUnlocalizedName(Reference.MOD_ID.toLowerCase() + "." + Names.BAG_TIER_5_NAME.toLowerCase());
        setRegistryName(Names.BAG_TIER_5_NAME);
        setMaxStackSize(64);
        setCreativeTab(Extremity.CREATIVE_TAB);
    }

    @Override
    public int getID()
    {
        return 5;
    }
}
