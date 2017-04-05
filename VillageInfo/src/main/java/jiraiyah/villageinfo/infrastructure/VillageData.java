package jiraiyah.villageinfo.infrastructure;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class VillageData
{
    public int radius;
    public BlockPos center;
    public List<BlockPos> doorPositions = new ArrayList<>();
    public int reputation;
    public int villagerCount;

    public VillageData(int radius, BlockPos center, List<BlockPos> doorPositions, int reputation, int villagerCount)
    {
        this.radius = radius;
        this.center = center;
        this.doorPositions = doorPositions;
        this.reputation = reputation;
        this.villagerCount = villagerCount;
    }
}
