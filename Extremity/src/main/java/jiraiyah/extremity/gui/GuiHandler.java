package jiraiyah.extremity.gui;

import jiraiyah.extremity.containers.BagContainer;
import jiraiyah.extremity.inventories.BagInventory;
import jiraiyah.extremity.items.Bag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler
{
    public static final int BAG_ID = 0;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case BAG_ID :
                ItemStack heldItem = player.getHeldItem(EnumHand.values()[x]);
                if (heldItem.getCount() > 0)
                {
                    BagInventory inventory = Bag.getItems(heldItem);
                    int blockedSlot = -1;
                    if (player.getHeldItemMainhand() == heldItem)
                        blockedSlot = player.inventory.currentItem;
                    return new BagContainer(player.inventory, inventory, blockedSlot);
                }
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case BAG_ID :
                ItemStack heldItem = player.getHeldItem(EnumHand.values()[x]);
                if (heldItem.getCount() > 0)
                {
                    BagInventory inventory = Bag.getItems(heldItem);
                    int blockedSlot = -1;
                    if (player.getHeldItemMainhand() == heldItem)
                        blockedSlot = player.inventory.currentItem;
                    return new BagGui(player.inventory, inventory, blockedSlot, heldItem);
                }
                break;
        }
        return null;
    }
}
