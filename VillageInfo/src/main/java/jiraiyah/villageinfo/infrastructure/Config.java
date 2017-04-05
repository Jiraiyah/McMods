package jiraiyah.villageinfo.infrastructure;

import jiraiyah.villageinfo.references.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class Config
{
    public static int updateRatio = 1;
    public static int villageDetectDistance = 140;
    public static int spawnChunkShowDistance = 140;

    private static Configuration config;
    private static final String CATEGORY_GENERIC = "Generic";

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (Reference.MOD_ID.equals(event.getModID()))
            loadConfigs(config);
    }

    public static void loadConfigsFromFile(File configFile)
    {
        config = new Configuration(configFile, "1.0", true);
        config.load();
        loadConfigs(config);
    }

    private static void loadConfigs(Configuration conf)
    {
        Property prop = conf.get(CATEGORY_GENERIC, "updateRatio", 1).setRequiresMcRestart(false);
        prop.setComment("Second intervals that server will update village information for each player, should be a " +
                "positive integer (obviously).");
        updateRatio = prop.getInt();

        prop = conf.get(CATEGORY_GENERIC, "villageDetectDistance", 140).setRequiresMcRestart(false);
        prop.setComment("Distance from border of the village (not the center) that server accepts for each player to " +
                "show the village info for, should be positive integer.");
        villageDetectDistance = prop.getInt();

        prop = conf.get(CATEGORY_GENERIC, "spawnChunkShowDistance", 140).setRequiresMcRestart(false);
        prop.setComment("Distance from border of the spawn chunk that graphics starts showing up, should be a " +
                "positive integer.");
        spawnChunkShowDistance = prop.getInt();
        if (conf.hasChanged())
            conf.save();
    }
}
