package jiraiyah.extremity.containers;

import jiraiyah.extremity.inventories.BagInventory;
import jiraiyah.extremity.slots.BagSlot;
import jiraiyah.extremity.slots.BagUpgradeSlot;
import jiraiyah.extremity.slots.LockedSlot;
import jiraiyah.extremity.slots.TrashSlot;
import jiraiyah.jlib.utilities.Log;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;


public class BagContainer extends Container
{
    public int bagSlots;

    public BagContainer(IInventory playerInventory, IItemHandler bagInventory, int blockedSlot)
    {
        UpgradeSlotCount(bagInventory);
        bindPlayerInventory(playerInventory, blockedSlot);
        addSlotToContainer(new BagUpgradeSlot(bagInventory, 0, 228, 210));
        ((BagInventory) bagInventory).setContainer(this);
        for (int i = 1; i < 9; i++)
            AddRowSlot(bagInventory, i);
        AddTrashSlot(bagInventory);
    }

    public void UpgradeSlotCount(IItemHandler bagInventory)
    {
        this.bagSlots = bagInventory.getSlots();
    }

    public void AddTrashSlot(IItemHandler bagInventory)
    {
        BagInventory bag = (BagInventory) bagInventory;
        if(bag.hasUpgrade(9))
            addSlotToContainer(new TrashSlot(bagInventory, 105, 5, 210));
    }

    public void AddRowSlot(IItemHandler bagInventory, int row)
    {
        BagInventory bag = (BagInventory) bagInventory;
        if(bag.hasUpgrade(row))
            for (int i = 0; i < 13; i++)
            {
                int index = 1 + i + (row - 1) * 13;
                addSlotToContainer(new BagSlot(bagInventory, index, 12 + i * 18, 5 + (row - 1) * 18));
            }
    }

    public void AddCraftingSlot(IItemHandler bagInventory)
    {

    }

    public void AddSmeltingSlot(IItemHandler bagInventory)
    {

    }

    private void bindPlayerInventory (IInventory playerInventory, int blockedSlot)
    {
        for (int l = 0; l < 3; l++)
        {
            for (int j = 0; j < 9; j++)
            {
                int index = j + l * 9 + 9;
                addSlotToContainer(blockedSlot == index
                ? new LockedSlot(playerInventory, index, 62 + j * 18, 152 + l * 18)
                : new Slot(playerInventory, index, 62 + j * 18, 152 + l * 18));
            }
        }

        for (int j = 0; j < 9; j++)
        {
            addSlotToContainer(blockedSlot == j
                    ? new LockedSlot(playerInventory, j, 62 + j * 18, 210)
                    : new Slot(playerInventory, j, 62 + j * 18, 210));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if (slot != null && slot.getHasStack())
        {
            ItemStack slotStack = slot.getStack();
            stack = slotStack.copy();
            if (index < bagSlots)
            {
                if (!mergeItemStack(slotStack, bagSlots, inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!mergeItemStack(slotStack, 0, bagSlots, false))
                return ItemStack.EMPTY;
            if (slotStack.getCount() == 0)
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();
        }
        return stack;
    }



    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }
}
