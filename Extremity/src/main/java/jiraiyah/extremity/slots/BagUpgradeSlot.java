package jiraiyah.extremity.slots;

import jiraiyah.extremity.infrastructures.BagUpgradeItemBase;
import jiraiyah.extremity.inventories.BagInventory;
import jiraiyah.extremity.items.bagUpgrades.TrashUpgrade;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class BagUpgradeSlot extends SlotItemHandler
{
    IItemHandler owner;

    public BagUpgradeSlot(IItemHandler inventoryIn, int index, int xPosition, int yPosition)
    {
        super(inventoryIn, index, xPosition, yPosition);
        this.owner = inventoryIn;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return stack.getItem() instanceof BagUpgradeItemBase;
    }

    @Override
    public void putStack(@Nonnull ItemStack stack)
    {
        if (owner instanceof BagInventory && stack.getItem() instanceof BagUpgradeItemBase)
        {
            BagInventory bag = (BagInventory) owner;
            int id = ((BagUpgradeItemBase)stack.getItem()).getID();
            if (id == -1)
            {
                super.putStack(stack);
                return;
            }
            if (id != 1 && id < 9 && !bag.hasUpgrade(id - 1))
            {
                super.putStack(stack);
                return;
            }
            if (!bag.hasUpgrade(id))
                    bag.upgrade(id);
            else
            {
                super.putStack(stack);
                return;
            }
        }
        super.putStack(ItemStack.EMPTY);
    }
}
