package jiraiyah.villageinfo.network;

import io.netty.buffer.ByteBuf;
import jiraiyah.jlib.utilities.ServerHelper;
import jiraiyah.villageinfo.events.WorldSpawnHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpawnServerMessage implements IMessageHandler<SpawnServerMessage.Packet, IMessage>
{
    @Override
    public IMessage onMessage(SpawnServerMessage.Packet message, MessageContext ctx)
    {
        WorldSpawnHandler.setSpawnInformation(message.spawnPoint, message.xCoord, message.zCoord);
        return null;
    }

    public static void sendMessage(UUID playerId, BlockPos spawnPoint, List<Integer> xCoords, List<Integer> zCoords)
    {
        Packet packet = new Packet(spawnPoint, xCoords, zCoords);
        EntityPlayerMP playerMP = ServerHelper.getPlayerById(playerId);
        NetworkMessages.network.sendTo(packet, playerMP);
    }

    public static class Packet implements IMessage
    {
        public BlockPos spawnPoint;
        public List<Integer> xCoord = new ArrayList<>();
        public List<Integer> zCoord = new ArrayList<>();

        public Packet() {}

        public Packet(BlockPos spawnPoint, List<Integer> xCoord, List<Integer> zCoord)
        {
            this.spawnPoint = spawnPoint;
            this.xCoord = xCoord;
            this.zCoord = zCoord;
        }

        @Override
        public void fromBytes(ByteBuf buf)
        {
            spawnPoint = BlockPos.fromLong(buf.readLong());
            int dataSize = buf.readInt();
            xCoord = new ArrayList<>();
            zCoord = new ArrayList<>();
            for (int i = 0; i < dataSize; i++)
                xCoord.add(buf.readInt());
            for (int i = 0; i < dataSize; i++)
                zCoord.add(buf.readInt());
        }

        @Override
        public void toBytes(ByteBuf buf)
        {
            buf.writeLong(spawnPoint.toLong());
            buf.writeInt(xCoord.size());
            for (int x : xCoord)
                buf.writeInt(x);
            for (int z : zCoord)
                buf.writeInt(z);
        }
    }
}
