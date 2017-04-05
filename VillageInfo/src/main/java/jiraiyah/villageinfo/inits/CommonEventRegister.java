package jiraiyah.villageinfo.inits;

import jiraiyah.villageinfo.events.WorldDataCollector;
import net.minecraftforge.common.MinecraftForge;

public class CommonEventRegister
{
    public static void register()
    {
        MinecraftForge.EVENT_BUS.register(new WorldDataCollector());
    }
}
