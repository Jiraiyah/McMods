package jiraiyah.deathcountlist.proxies;

import jiraiyah.deathcountlist.CommonEventRegister;
import jiraiyah.jlib.interfaces.IProxy;
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
    {}

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {}
}
