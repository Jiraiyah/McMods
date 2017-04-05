package jiraiyah.villageinfo.network;

import io.netty.buffer.ByteBuf;
import jiraiyah.villageinfo.events.WorldDataCollector;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class SpawnPlayerMessage implements IMessageHandler<SpawnPlayerMessage.Packet, IMessage>
{
    @Override
    public IMessage onMessage(SpawnPlayerMessage.Packet message, MessageContext ctx)
    {
        WorldDataCollector.getSpawnInformation(message.playerId);
        return null;
    }

    public static void sendMessage(UUID playerId)
    {
        Packet packet = new Packet(playerId);
        NetworkMessages.network.sendToServer(packet);
    }

    public static class Packet implements IMessage
    {
        public UUID playerId;

        public Packet() {}

        public Packet(UUID playerId)
        {
            this.playerId = playerId;
        }

        @Override
        public void fromBytes(ByteBuf buf)
        {
            int size = buf.readInt();
            byte[] bytes = buf.readBytes(size).array();
            String id = new String(bytes);
            playerId = UUID.fromString(id);
        }

        @Override
        public void toBytes(ByteBuf buf)
        {
            String id = playerId.toString();
            byte[] bytes = id.getBytes();
            buf.writeInt(bytes.length);
            buf.writeBytes(bytes);
        }
    }
}
