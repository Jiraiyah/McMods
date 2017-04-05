package jiraiyah.extremity.inits;

import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class NetworkMessages
{
    public static SimpleNetworkWrapper NETWORK;

    private static int ID = 0;

    public static void register()
    {

    }

    private static int nextId()
    {
        return ID++;
    }
}
