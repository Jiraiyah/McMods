package jiraiyah.extremity.containers;

import jiraiyah.extremity.inventories.BagInventory;
import jiraiyah.extremity.slots.BagSlot;
import jiraiyah.extremity.slots.BagUpgradeSlot;
import jiraiyah.extremity.slots.LockedSlot;
import jiraiyah.extremity.slots.TrashSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;


public class BagContainer extends Container
{
    public int bagSlots;

    private IItemHandler bagInventory;

    private TrashSlot trashSlot;

    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
    public IInventory craftResult = new InventoryCraftResult();

    private InventoryPlayer playerInventory;
    private World world;

    public BagContainer(IInventory playerInventory, IItemHandler bagInventory, int blockedSlot, World world)
    {
        this.playerInventory = (InventoryPlayer)playerInventory;
        this.world = world;
        this.bagInventory = bagInventory;
        AddCraftingSlot(bagInventory);
        bindPlayerInventory(playerInventory, blockedSlot);
        addSlotToContainer(new BagUpgradeSlot(bagInventory, 499, 228, 210));
        for (int i = 1; i < 9; i++)
            AddRowSlot(bagInventory, i);
        //TODO : Smelting
        AddTrashSlot(bagInventory);
        UpgradeSlotCount(bagInventory);
        ((BagInventory) bagInventory).setContainer(this);
    }

    public void UpgradeSlotCount(IItemHandler bagInventory)
    {
        this.bagSlots = bagInventory.getSlots();
    }

    public void AddTrashSlot(IItemHandler bagInventory)
    {
        BagInventory bag = (BagInventory) bagInventory;
        if(bag.hasUpgrade(11))
        {
            trashSlot = new TrashSlot(bagInventory, 500, 5, 210);
            addSlotToContainer(trashSlot);
        }
    }

    public void AddRowSlot(IItemHandler bagInventory, int row)
    {
        BagInventory bag = (BagInventory) bagInventory;
        if(bag.hasUpgrade(row))
            for (int i = 0; i < 13; i++)
            {
                addSlotToContainer(new BagSlot(bagInventory, (row - 1) * 13 + i, 12 + i * 18, 5 + (row - 1) *
                        18));
            }
    }

    // 1 Slot : SlotCrafting, 0
    // 9 Slots : 0 - 8
    public void AddCraftingSlot(IItemHandler bagInventory)
    {
        BagInventory bag = (BagInventory) bagInventory;
        if(bag.hasUpgrade(9))
        {
            addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult,
                    0, 41, 210));
            for (int i = 0; i < 3; ++i)
            {
                for (int j = 0; j < 3; ++j)
                {
                    this.addSlotToContainer(new Slot(this.craftMatrix, j + i * 3, 5 + j * 18,
                            152 + i * 18));
                }
            }
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);
        for (int i = 0; i < 9; ++i)
        {
            ItemStack itemstack = this.craftMatrix.removeStackFromSlot(i);

            if (!itemstack.isEmpty())
            {
                int index = getFirstEmptySlot(itemstack);
                if (index != -1)
                {
                    bagInventory.insertItem(index, itemstack, false);
                }
                else
                {
                    index = playerIn.inventory.getFirstEmptyStack();
                    if (index != -1)
                    {
                        playerIn.inventoryContainer.putStackInSlot(index, itemstack);
                        playerIn.inventoryContainer.detectAndSendChanges();
                        playerIn.inventory.markDirty();
                    }
                    else
                        if (!world.isRemote)
                            playerIn.dropItem(itemstack, false);
                }
            }
        }
    }

    public void AddSmeltingSlot(IItemHandler bagInventory)
    {

    }

    // 27 Slots : 9 - 35
    // 9 Slots : 0 - 8
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
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < bagSlots)
            {
                if (!mergeItemStack(itemstack1, 0, inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!mergeItemStack(itemstack1, 0, bagSlots, false))
            {
                return ItemStack.EMPTY;
            }
            if (itemstack1.getCount() == 0)
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn)
    {
        return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
    }

    public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this
                .craftMatrix, this.world));
    }

    private int getFirstEmptySlot(ItemStack stack)
    {
        int index = -1;

        for (int i = 0; i < inventorySlots.size(); i ++)
            if (inventorySlots.get(i).getStack().isEmpty() &&
                inventorySlots.get(i).isItemValid(stack) &&
                inventorySlots.get(i) instanceof BagSlot)
            {
                index = inventorySlots.get(i).getSlotIndex();
                break;
            }
        return index;
    }

    @Override
    protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection)
    {
        boolean flag = false;
        int i = startIndex;

        if (reverseDirection)
        {
            i = endIndex - 1;
        }

        if (stack.isStackable())
        {
            while (!stack.isEmpty())
            {
                if (reverseDirection)
                {
                    if (i < startIndex)
                    {
                        break;
                    }
                }
                else if (i >= endIndex)
                {
                    break;
                }

                Slot slot = (Slot)this.inventorySlots.get(i);
                if (slot != trashSlot)
                {
                    ItemStack itemstack = slot.getStack();

                    if (!itemstack.isEmpty() && itemstack.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getMetadata() == itemstack.getMetadata()) && ItemStack.areItemStackTagsEqual(stack, itemstack))
                    {
                        int j = itemstack.getCount() + stack.getCount();
                        int maxSize = Math.min(slot.getSlotStackLimit(), stack.getMaxStackSize());

                        if (j <= maxSize)
                        {
                            stack.setCount(0);
                            itemstack.setCount(j);
                            slot.onSlotChanged();
                            flag = true;
                        }
                        else if (itemstack.getCount() < maxSize)
                        {
                            stack.shrink(maxSize - itemstack.getCount());
                            itemstack.setCount(maxSize);
                            slot.onSlotChanged();
                            flag = true;
                        }
                    }
                }

                if (reverseDirection)
                {
                    --i;
                }
                else
                {
                    ++i;
                }
            }
        }

        if (!stack.isEmpty())
        {
            if (reverseDirection)
            {
                i = endIndex - 1;
            }
            else
            {
                i = startIndex;
            }

            while (true)
            {
                if (reverseDirection)
                {
                    if (i < startIndex)
                    {
                        break;
                    }
                }
                else if (i >= endIndex)
                {
                    break;
                }

                Slot slot1 = (Slot)this.inventorySlots.get(i);
                if (slot1 != trashSlot)
                {
                    ItemStack itemstack1 = slot1.getStack();

                    if (itemstack1.isEmpty() && slot1.isItemValid(stack))
                    {
                        if (stack.getCount() > slot1.getSlotStackLimit())
                        {
                            slot1.putStack(stack.splitStack(slot1.getSlotStackLimit()));
                        }
                        else
                        {
                            slot1.putStack(stack.splitStack(stack.getCount()));
                        }

                        slot1.onSlotChanged();
                        flag = true;
                        break;
                    }
                }

                if (reverseDirection)
                {
                    --i;
                }
                else
                {
                    ++i;
                }
            }
        }

        return flag;
    }
}
