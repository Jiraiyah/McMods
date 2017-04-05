package jiraiyah.villageinfo.inits;

import jiraiyah.villageinfo.events.ChunkDataHandler;
import jiraiyah.villageinfo.events.KeyBindingHandler;
import jiraiyah.villageinfo.events.VillageDataHandler;
import jiraiyah.villageinfo.events.WorldSpawnHandler;
import net.minecraftforge.common.MinecraftForge;

public class ClientEventRegister
{
    public static void register()
    {
        MinecraftForge.EVENT_BUS.register(new KeyBindingHandler());
        MinecraftForge.EVENT_BUS.register(new VillageDataHandler());
        MinecraftForge.EVENT_BUS.register(new WorldSpawnHandler());
        MinecraftForge.EVENT_BUS.register(new ChunkDataHandler());
    }
}
