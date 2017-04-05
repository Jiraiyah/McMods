package jiraiyah.extremity;

import jiraiyah.extremity.gui.GuiHandler;
import jiraiyah.extremity.inits.items.ItemInits;
import jiraiyah.extremity.proxies.CommonProxy;
import jiraiyah.extremity.references.Reference;
import jiraiyah.jlib.infrastructure.ConfigFile;
import jiraiyah.jlib.infrastructure.ConfigTag;
import jiraiyah.jlib.infrastructure.ConfigTagParent;
import jiraiyah.jlib.utilities.GenericCreativeTab;
import net.minecraft.init.Items;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.File;

@SuppressWarnings("ALL")
@Mod(modid = Reference.MOD_ID, version = Reference.VERSION, name = Reference.MOD_NAME, dependencies = Reference.DEPENDENCIES)
public class Extremity
{
    public static ConfigFile CONFIG;
    public static GuiHandler GUI_HANDLER;

    @Instance(Reference.MOD_ID)
    public static Extremity INSTANCE;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
    public static CommonProxy PROXY;

    public static final GenericCreativeTab CREATIVE_TAB = new GenericCreativeTab(Reference.MOD_NAME);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        /*CONFIG = new ConfigFile(new File(event.getModConfigurationDirectory(), "Extremity.cfg"));
        CONFIG.setComment("This is a temporary comment");
        ConfigTag tag = new ConfigTag(CONFIG, "GlobalElement");
        tag.brace = true;
        ConfigTag tag2 = new ConfigTag(tag, "FirstElement");
        tag2.brace = true;
        tag2.setComment("This is the first element");
        tag2.setBooleanValue(false);
        CONFIG.addChild(tag);*/
        PROXY.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        PROXY.init(event);
        CREATIVE_TAB.setIcon(ItemInits.BAG);
        NetworkRegistry.INSTANCE.registerGuiHandler(this, GUI_HANDLER = new GuiHandler());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        PROXY.postInit(event);
    }
}
