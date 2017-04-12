package jiraiyah.extremity.network;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import io.netty.buffer.ByteBuf;
import jiraiyah.extremity.Extremity;
import jiraiyah.extremity.gui.GuiHandler;
import jiraiyah.extremity.inits.NetworkMessages;
import jiraiyah.extremity.items.Bag;
import jiraiyah.jlib.utilities.ServerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class BagInventoryOpen implements IMessageHandler<BagInventoryOpen.Packet, IMessage>
{
    @Override
    public IMessage onMessage(BagInventoryOpen.Packet message, MessageContext ctx)
    {
        World world = ServerHelper.getServerWorld();
        EntityPlayer player = world.getPlayerEntityByUUID(message.playerId);
        IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
        if (baubles == null || baubles.getSlots() == 0)
            return null;
        for (int i = 0; i < baubles.getSlots(); i++)
        {
            ItemStack inSlot = baubles.getStackInSlot(i);
            if (inSlot.getCount() > 0 && inSlot.getItem() instanceof Bag)
            {
                player.openGui(Extremity.INSTANCE, GuiHandler.BAG_ID, world, 1, i, 0);
                break;
            }
        }
        return null;
    }

    public static void sendMessage(UUID playerId)
    {
        Packet packet = new Packet(playerId);
        NetworkMessages.NETWORK.sendToServer(packet);
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
