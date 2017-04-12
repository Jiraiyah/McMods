package jiraiyah.extremity.inits;

import jiraiyah.extremity.network.BagInventoryOpen;
import jiraiyah.extremity.references.Reference;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkMessages
{
    public static SimpleNetworkWrapper NETWORK;

    private static int ID = 0;

    public static void register()
    {
        NETWORK = new SimpleNetworkWrapper(Reference.MOD_ID);
        if (Loader.isModLoaded("baubles"))
            NETWORK.registerMessage(BagInventoryOpen.class, BagInventoryOpen.Packet.class, nextId(), Side.SERVER);
    }

    private static int nextId()
    {
        return ID++;
    }
}
