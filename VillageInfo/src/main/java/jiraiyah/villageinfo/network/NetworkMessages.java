package jiraiyah.villageinfo.network;

import jiraiyah.villageinfo.references.Reference;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkMessages
{
    public static SimpleNetworkWrapper network;

    private static int ID = 0;

    public static void register()
    {
        network = new SimpleNetworkWrapper(Reference.MOD_ID);
        network.registerMessage(VillagePlayerMessage.class, VillagePlayerMessage.Packet.class, nextId(), Side.SERVER);
        network.registerMessage(SpawnPlayerMessage.class, SpawnPlayerMessage.Packet.class, nextId(), Side.SERVER);

        network.registerMessage(VillageServerMessage.class, VillageServerMessage.Packet.class, nextId(), Side.CLIENT);
        network.registerMessage(SpawnServerMessage.class, SpawnServerMessage.Packet.class, nextId(), Side.CLIENT);
    }

    private static int nextId()
    {
        return ID++;
    }
}
