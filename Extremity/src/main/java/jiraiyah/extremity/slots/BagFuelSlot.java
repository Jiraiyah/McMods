package jiraiyah.extremity.slots;

import jiraiyah.extremity.inventories.BagInventory;
import jiraiyah.extremity.items.Bag;
import jiraiyah.jlib.slots.SpecialSlotItemHandler;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.items.IItemHandler;

public class BagFuelSlot extends SpecialSlotItemHandler
{
    public BagFuelSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
    }

    public boolean isItemValid(ItemStack stack)
    {
        return Bag.isItemFuel(stack) || isBucket(stack);
    }

    public int getItemStackLimit(ItemStack stack)
    {
        return isBucket(stack) ? 1 : super.getItemStackLimit(stack);
    }

    public static boolean isBucket(ItemStack stack)
    {
        return stack.getItem() == Items.BUCKET;
    }
}
