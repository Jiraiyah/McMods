package jiraiyah.extremity.proxies;

import jiraiyah.extremity.inits.NetworkMessages;
import jiraiyah.extremity.inits.blocks.BlockInits;
import jiraiyah.extremity.inits.blocks.BlockRegisters;
import jiraiyah.extremity.inits.events.CommonEventRegisters;
import jiraiyah.extremity.inits.items.ItemInits;
import jiraiyah.extremity.inits.items.ItemRegisters;
import jiraiyah.extremity.inits.recipes.RecipeRegisters;
import jiraiyah.extremity.inits.tileEntities.TileEntityRegisters;
import jiraiyah.jlib.interfaces.IProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy implements IProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        BlockInits.initialize();
        BlockRegisters.register();
        ItemInits.initialize();
        ItemRegisters.register();
        CommonEventRegisters.register();
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        RecipeRegisters.register();
        TileEntityRegisters.register();
        NetworkMessages.register();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {

    }
}
