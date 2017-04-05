package jiraiyah.extremity.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.IItemHandler;

public class LockedSlot extends Slot
{
    public LockedSlot(IInventory inventoryIn, int index, int xPosition, int yPosition)
    {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn)
    {
        return false;
    }
}
