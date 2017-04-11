package jiraiyah.extremity.slots;

import jiraiyah.jlib.slots.SpecialSlotItemHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

public class TrashSlot extends SpecialSlotItemHandler
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
