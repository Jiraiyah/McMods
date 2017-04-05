package jiraiyah.villageinfo.proxies;

import jiraiyah.jlib.interfaces.IProxy;
import jiraiyah.villageinfo.inits.CommonEventRegister;
import jiraiyah.villageinfo.network.NetworkMessages;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy implements IProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        CommonEventRegister.register();
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        NetworkMessages.register();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {}
}
