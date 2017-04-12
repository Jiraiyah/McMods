package jiraiyah.extremity.events;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import jiraiyah.extremity.Extremity;
import jiraiyah.extremity.gui.GuiHandler;
import jiraiyah.extremity.inits.KeyBindings;
import jiraiyah.extremity.items.Bag;
import jiraiyah.extremity.network.BagInventoryOpen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class KeyBindingHandler
{
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if (KeyBindings.BAG_INVENTORY.isPressed())
        {
            World world = Minecraft.getMinecraft().world;
            EntityPlayer player = Minecraft.getMinecraft().player;
            IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
            if (baubles == null || baubles.getSlots() == 0)
                return;
            for (int i = 0; i < baubles.getSlots(); i++)
            {
                ItemStack inSlot = baubles.getStackInSlot(i);
                if (inSlot.getCount() > 0 && inSlot.getItem() instanceof Bag)
                {
                    BagInventoryOpen.sendMessage(player.getUniqueID());
                    break;
                }
            }
        }
    }
}
