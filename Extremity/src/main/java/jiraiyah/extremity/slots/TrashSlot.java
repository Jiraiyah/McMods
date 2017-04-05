package jiraiyah.extremity.slots;

import jiraiyah.extremity.items.Bag;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class TrashSlot extends SlotItemHandler
{
    public TrashSlot(IItemHandler inventoryIn, int index, int xPosition, int yPosition)
    {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return true;
    }

    @Override
    public void putStack(@Nonnull ItemStack stack)
    {
        super.putStack(ItemStack.EMPTY);
    }
}
