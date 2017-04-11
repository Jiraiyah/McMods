package jiraiyah.extremity.containers;

import jiraiyah.extremity.inventories.BagInventory;
import jiraiyah.extremity.slots.BagSlot;
import jiraiyah.extremity.slots.BagUpgradeSlot;
import jiraiyah.extremity.slots.LockedSlot;
import jiraiyah.extremity.slots.TrashSlot;
import jiraiyah.jlib.slots.SpecialSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;


public class BagContainer extends Container
{
    public int bagSlots;

    private IItemHandler bagInventory;

    private TrashSlot trashSlot;

    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
    public IInventory craftResult = new InventoryCraftResult();

    private InventoryPlayer playerInventory;
    private World world;
    private List<Slot> bagSlotList = new ArrayList<>();
    private List<Slot> playerSlotList = new ArrayList<>();

    public BagContainer(IInventory playerInventory, IItemHandler bagInventory, int blockedSlot, World world)
    {
        bagSlotList.clear();
        playerSlotList.clear();
        this.playerInventory = (InventoryPlayer)playerInventory;
        this.world = world;
        this.bagInventory = bagInventory;
        AddCraftingSlot(bagInventory);
        bindPlayerInventory(playerInventory, blockedSlot);
        Slot upgradeSlot =new BagUpgradeSlot(bagInventory, 499, 228, 210);
        bagSlotList.add(upgradeSlot);
        addSlotToContainer(upgradeSlot);
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
        BagSlot slot;
        BagInventory bag = (BagInventory) bagInventory;
        if(bag.hasUpgrade(row))
            for (int i = 0; i < 13; i++)
            {
                slot = new BagSlot(bagInventory, (row - 1) * 13 + i, 12 + i * 18, 5 + (row - 1) *
                        18);
                bagSlotList.add(slot);
                addSlotToContainer(slot);
            }
    }

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
                    SpecialSlot craftingSlot =new SpecialSlot(this.craftMatrix, j + i * 3, 5 + j * 18,
                            152 + i * 18);
                    this.addSlotToContainer(craftingSlot);
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

    private void bindPlayerInventory (IInventory playerInventory, int blockedSlot)
    {
        Slot slot;
        for (int l = 0; l < 3; l++)
        {
            for (int j = 0; j < 9; j++)
            {
                int index = j + l * 9 + 9;
                slot = blockedSlot == index
                ? new LockedSlot(playerInventory, index, 62 + j * 18, 152 + l * 18)
                : new Slot(playerInventory, index, 62 + j * 18, 152 + l * 18);
                playerSlotList.add(slot);
                addSlotToContainer(slot);
            }
        }

        for (int j = 0; j < 9; j++)
        {
            slot = blockedSlot == j
                    ? new LockedSlot(playerInventory, j, 62 + j * 18, 210)
                    : new Slot(playerInventory, j, 62 + j * 18, 210);
            playerSlotList.add(slot);
            addSlotToContainer(slot);
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        boolean clickedInBag = false;
        boolean clickedInPlayer = false;
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if (slot == null || !slot.getHasStack())
            return itemstack;
        ItemStack itemstack1 = slot.getStack();
        if (index == 0)
        {
            itemstack = itemstack1.copy();
            itemstack1.getItem().onCreated(itemstack1, this.world, playerIn);

            if (!this.mergeItemStack(itemstack1, 10, 46, true))
                return ItemStack.EMPTY;

            slot.onSlotChange(itemstack1, itemstack);
            if (itemstack1.isEmpty())
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();

            if (itemstack1.getCount() == itemstack.getCount())
                return ItemStack.EMPTY;
            ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);

            if (index == 0)
                playerIn.dropItem(itemstack2, false);
            return itemstack;
        }


        if (bagSlotList.contains(slot))
            clickedInBag = true;
        else if (playerSlotList.contains(slot))
            clickedInPlayer = true;

        if (clickedInBag)
            handleItemTransfer(slot, playerSlotList);
        else if(clickedInPlayer)
            handleItemTransfer(slot, bagSlotList);
        if (itemstack1.getCount() == 0)
            slot.putStack(ItemStack.EMPTY);
        else
            slot.onSlotChanged();
        return itemstack;
    }

    private void handleItemTransfer(Slot origin, List<Slot> targetList)
    {
        ItemStack slotStack = origin.getStack();

        for (Slot target : targetList)
        {
            ItemStack targetStack = target.getStack();
            if (targetStack == ItemStack.EMPTY && target.isItemValid(slotStack))
            {
                target.putStack(slotStack);
                origin.putStack(ItemStack.EMPTY);
                target.onSlotChanged();
                origin.onSlotChanged();
                break;
            }
            else if (target.isItemValid(slotStack))
            {
                if (!target.isItemValid(slotStack) || targetStack.getCount() >= target.getSlotStackLimit())
                    continue;
                if (targetStack.getItem() != slotStack.getItem() || targetStack.getItem().getMetadata(targetStack) !=
                        slotStack.getItem().getMetadata(slotStack))
                    continue;
                int transferableCount = target.getSlotStackLimit() - targetStack.getCount();
                if (transferableCount >= slotStack.getCount())
                {
                    targetStack.grow(slotStack.getCount());
                    slotStack.setCount(0);
                }
                else
                {
                    targetStack.grow(transferableCount);
                    slotStack.shrink(transferableCount);
                }
                target.putStack(targetStack);
                origin.putStack(slotStack);
                target.onSlotChanged();
                origin.onSlotChanged();
                break;
            }
        }
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
}
