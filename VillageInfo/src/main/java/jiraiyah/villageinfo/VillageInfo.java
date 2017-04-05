package jiraiyah.villageinfo;

import jiraiyah.villageinfo.infrastructure.Config;
import jiraiyah.villageinfo.proxies.CommonProxy;
import jiraiyah.villageinfo.references.Reference;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@SuppressWarnings("ALL")
@Mod(modid = Reference.MOD_ID, version = Reference.VERSION, name = Reference.MOD_NAME, dependencies = Reference.DEPENDENCIES)
public class VillageInfo
{
    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
    public static CommonProxy PROXY;

    public static boolean SOLID_DRAW;
    public static boolean VILLAGE_BORDER;
    public static boolean SHOW_VILLAGE;
    public static boolean VILLAGE_DOORS = true;
    public static boolean VILLAGE_SPHERE;
    public static boolean VILLAGE_GOLEM = true;
    public static boolean VILLAGE_INFO_TEXT;
    public static boolean VILLAGE_CENTER = true;
    public static boolean DISABLE_DEPTH = true;
    public static boolean PER_VILLAGE_COLOR;
    public static boolean CHUNK_BORDER;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        PROXY.preInit(event);
        Config.loadConfigsFromFile(event.getSuggestedConfigurationFile());
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        PROXY.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        PROXY.postInit(event);
    }
}
