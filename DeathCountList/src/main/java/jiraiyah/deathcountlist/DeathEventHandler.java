package jiraiyah.deathcountlist;

import net.minecraft.scoreboard.IScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DeathEventHandler
{
    // /scoreboard objectives add deaths deathCount Deaths
    // /scoreboard objectives setdisplay list deaths

    @SubscribeEvent
    public void onWorldGenerateSpawnPoint(WorldEvent.CreateSpawnPosition event)
    {
        World world = event.getWorld();
        ScoreObjective objective = world.getScoreboard().getObjective("deaths");
        if (objective == null)
            generateObjective(world);
    }

    @SubscribeEvent
    public void onWorldGenerateSpawnPoint(WorldEvent.Load event)
    {
        World world = event.getWorld();
        ScoreObjective objective = world.getScoreboard().getObjective("deaths");
        if (objective == null)
            generateObjective(world);
    }

    private void generateObjective(World world)
    {
        ScoreObjective objective = world.getScoreboard().addScoreObjective("deaths", IScoreCriteria.DEATH_COUNT);
        objective.setDisplayName("Deaths");
        world.getScoreboard().setObjectiveInDisplaySlot(0, objective);
    }
}
