package jiraiyah.jlib.utilities;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.server.FMLServerHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class ServerHelper
{
    public static EntityPlayerMP getPlayerById(UUID playerId)
    {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList()
                .getPlayerByUUID(playerId);
    }

    public static World getServerWorld()
    {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();
    }

    public static MinecraftServer mc()
    {
        return FMLServerHandler.instance().getServer();
    }

    public static Chunk getChunkFromBlockPos(BlockPos pos, World world)
    {
        return world.getChunkProvider().getLoadedChunk(pos.getX() >> 4, pos.getZ() >> 4);
    }

    public static boolean isSpawnChunk(int x, int z)
    {
        return getServerWorld().isSpawnChunk(x, z);
    }

    public static boolean isPlayerOP (String playerName)
    {
        GameProfile profile = getGameProfile(playerName);
        return profile != null && mc().getPlayerList().canSendCommands(profile);
    }

    public static GameProfile getGameProfile (String playerName)
    {
        EntityPlayer player = getPlayerByName(playerName);
        if (player != null)
            return player.getGameProfile();
        playerName = playerName.toLowerCase(Locale.ROOT);
        return mc().getPlayerProfileCache().getGameProfileForUsername(playerName);
    }

    public static EntityPlayerMP getPlayerByName(String name)
    {
        return mc().getPlayerList().getPlayerByUsername(name);
    }

    public static ArrayList<EntityPlayer> getPlayersInDimension(int dimension)
    {
        ArrayList<EntityPlayer> players = new ArrayList<>();
        for (EntityPlayer p : getPlayers())
            if (p.dimension == dimension)
                players.add(p);
        return players;
    }

    public static List<EntityPlayerMP> getPlayers()
    {
        return mc().getPlayerList().getPlayers();
    }

    public static int getDimension(World world)
    {
        return world.provider.getDimension();
    }
}
