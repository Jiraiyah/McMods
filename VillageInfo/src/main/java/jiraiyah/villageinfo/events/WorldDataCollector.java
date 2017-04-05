package jiraiyah.villageinfo.events;

import jiraiyah.jlib.utilities.ServerHelper;
import jiraiyah.villageinfo.infrastructure.Config;
import jiraiyah.villageinfo.infrastructure.VillageData;
import jiraiyah.villageinfo.network.SpawnServerMessage;
import jiraiyah.villageinfo.network.VillageServerMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.village.VillageCollection;
import net.minecraft.village.VillageDoorInfo;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.*;
import java.util.stream.Collectors;

public class WorldDataCollector
{
    private int resetCoolDown;
    private static List<UUID> VILLAGE_INFO_PLAYERS = new ArrayList<>();
    private static BlockPos spawnPoint = null;
    private static List<Integer> xCoords = new ArrayList<>();
    private static List<Integer> zCoords = new ArrayList<>();
    private static Map<UUID, List<VillageData>> VillageDataList = new HashMap<>();

    @SubscribeEvent
    public void serverTickEvent(TickEvent.ServerTickEvent event)
    {
        resetCoolDown --;
        if (resetCoolDown > 0)
            return;
        resetCoolDown = 20 * Config.updateRatio;
        if (VILLAGE_INFO_PLAYERS == null || VILLAGE_INFO_PLAYERS.size() == 0)
            return;
        resetVillageDataList();
    }

    @SubscribeEvent
    public void worldLoadEvent(WorldEvent.Load event)
    {
        if (spawnPoint == null)
            getSpawnPoint();
        if (xCoords == null || zCoords == null || xCoords.size() == 0 || zCoords.size() == 0)
            getSpawnChunks();
    }

    @SubscribeEvent
    public void playerLogoutEvent(PlayerEvent.PlayerLoggedOutEvent event)
    {
        removePlayerFromVillageList(event.player.getUniqueID());
    }

    private static void getSpawnChunks()
    {
        boolean foundFirstChunk = false;
        int cnt = 1;
        IChunkProvider chunkProvider = ServerHelper.getChunkProvider();
        for (int x = spawnPoint.getX() - 256; x < spawnPoint.getX() + 256; x += cnt)
        {
            for (int z = spawnPoint.getX() - 256; z < spawnPoint.getX() + 256; z += cnt)
            {
                BlockPos tempPos = new BlockPos(x, 0, z);
                Chunk chunk = ServerHelper.getChunkFromBlockPos(tempPos);
                if (ServerHelper.isSpawnChunk(chunk.xPosition, chunk.zPosition))
                {
                    if (!foundFirstChunk)
                    {
                        foundFirstChunk = true;
                        cnt = 16;
                    }
                    xCoords.add(chunk.xPosition);
                    zCoords.add(chunk.zPosition);
                }
            }
        }
    }

    private static void getSpawnPoint()
    {
        spawnPoint = ServerHelper.getSpawnPoint();
    }

    private static void resetVillageDataList()
    {
        World world = ServerHelper.getServerWorld();
        VillageCollection villageCollection = world.getVillageCollection();
        List<Village> allVillages = villageCollection.getVillageList();
        VillageDataList.clear();
        for (UUID player : VILLAGE_INFO_PLAYERS)
        {
            List<VillageData> tempList = new ArrayList<>();
            if (allVillages == null || allVillages.size() == 0)
            {
                VillageDataList.clear();
                VillageServerMessage.sendMessage(player, tempList);
                return;
            }
            EntityPlayer entityPlayer = world.getPlayerEntityByUUID(player);
            float psx = entityPlayer.getPosition().getX();
            float psz = entityPlayer.getPosition().getZ();
            allVillages.stream()
                    .filter(v -> psx < v.getCenter().getX() + v.getVillageRadius() + Config.villageDetectDistance &&
                                psz < v.getCenter().getZ() + v.getVillageRadius() + Config.villageDetectDistance &&
                                psx > v.getCenter().getX() - v.getVillageRadius() - Config.villageDetectDistance &&
                                psz > v.getCenter().getZ() - v.getVillageRadius() - Config.villageDetectDistance)
                    .forEach(v -> {
                        int radius = v.getVillageRadius();
                        int villagerCount = v.getNumVillagers();
                        int reputation = v.getPlayerReputation(entityPlayer.getName());
                        BlockPos center = v.getCenter();
                        List<VillageDoorInfo> doorInfos = v.getVillageDoorInfoList();
                        List<BlockPos> doorPositions = doorInfos.stream().map(VillageDoorInfo::getDoorBlockPos).collect(Collectors.toList());
                        tempList.add(new VillageData(radius, center, doorPositions, reputation, villagerCount));
                    });
            if (!VillageDataList.containsKey(player))
                VillageDataList.put(player, tempList);
            else
                VillageDataList.replace(player, tempList);
            VillageServerMessage.sendMessage(player, tempList);
        }
    }

    public static void addPlayerToVillageList(UUID playerId)
    {
        if (!VILLAGE_INFO_PLAYERS.contains(playerId))
            VILLAGE_INFO_PLAYERS.add(playerId);
    }

    public static void removePlayerFromVillageList(UUID playerId)
    {
        if (VILLAGE_INFO_PLAYERS.contains(playerId))
        {
            VILLAGE_INFO_PLAYERS.remove(playerId);
            if (VillageDataList.containsKey(playerId))
                VillageDataList.remove(playerId);
        }
    }

    public static void getSpawnInformation(UUID playerId)
    {
        SpawnServerMessage.sendMessage(playerId, spawnPoint, xCoords, zCoords);
    }
}
