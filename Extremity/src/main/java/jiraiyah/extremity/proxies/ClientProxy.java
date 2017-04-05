package jiraiyah.extremity.proxies;

import jiraiyah.extremity.inits.KeyBindings;
import jiraiyah.extremity.inits.blocks.BlockRendererRegisters;
import jiraiyah.extremity.inits.events.ClientEventRegisters;
import jiraiyah.extremity.inits.items.ItemRendererRegisters;
import jiraiyah.extremity.inits.tileEntities.TileEntityRendererRegisters;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.input.Keyboard;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        ClientEventRegisters.register();
        BlockRendererRegisters.register();
        ItemRendererRegisters.register();
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
        KeyBindings.register();
        TileEntityRendererRegisters.register();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
    }

    public static boolean isCtrlDown()
    {
        return Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) ||
                Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
    }

    public static boolean isShiftDown()
    {
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ||
                Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }

    public static boolean isAltDown()
    {
        return Keyboard.isKeyDown(Keyboard.KEY_LMENU) ||
                Keyboard.isKeyDown(Keyboard.KEY_RMENU);
    }
}
