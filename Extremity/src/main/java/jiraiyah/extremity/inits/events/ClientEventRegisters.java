package jiraiyah.extremity.inits.events;

import jiraiyah.extremity.events.KeyBindingHandler;
import net.minecraftforge.common.MinecraftForge;

public class ClientEventRegisters
{
    public static void register()
    {
        MinecraftForge.EVENT_BUS.register(new KeyBindingHandler());
    }
}
