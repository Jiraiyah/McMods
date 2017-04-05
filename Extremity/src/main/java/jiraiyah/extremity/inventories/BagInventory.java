package jiraiyah.extremity.inventories;

import jiraiyah.extremity.containers.BagContainer;
import jiraiyah.jlib.utilities.Log;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;

public class BagInventory implements IItemHandlerModifiable
{
    public final ItemStack bagStack;

    private BagContainer container;

    public BagInventory(ItemStack itemStack)
    {
        this.bagStack = itemStack;
    }

    private NBTTagCompound getTag()
    {
        NBTTagCompound tag;
        tag = bagStack.getTagCompound();
        if (tag == null)
            bagStack.setTagCompound(tag = new NBTTagCompound());
        return tag;
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack)
    {
        //validateSlotIndex(slot);

        NBTTagCompound itemTag = null;
        boolean hasStack = stack.getCount() > 0;
        if (hasStack)
        {
            itemTag = new NBTTagCompound();
            itemTag.setInteger("Slot", slot);
            stack.writeToNBT(itemTag);
        }
        NBTTagList tagList = getTag().getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound existing = tagList.getCompoundTagAt(i);
            if (existing.getInteger("Slot") != slot)
                continue;
            if (hasStack)
                tagList.set(i, itemTag);
            else
                tagList.removeTag(i);
            return;
        }
        if (hasStack)
            tagList.appendTag(itemTag);
        getTag().setTag("Items", tagList);
    }

    @Override
    public int getSlots()
    {
        return 119;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        //validateSlotIndex(slot);
        NBTTagList tagList = getTag().getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound itemTag = tagList.getCompoundTagAt(i);
            if (itemTag.getInteger("Slot") != slot)
                continue;
            return new ItemStack(itemTag);
        }
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
    {
        if (stack.getCount() <= 0)
            return ItemStack.EMPTY;
        //validateSlotIndex(slot);
        ItemStack existing = getStackInSlot(slot);
        int limit = stack.getMaxStackSize();
        if (existing.getCount() > 0)
        {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
                return stack;
            limit -= existing.getCount();
        }
        if (limit <= 0)
            return stack;
        boolean reachedLimit = stack.getCount() > limit;
        if (!simulate)
        {
            if (existing.getCount() <= 0)
            {
                existing = reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack;
            }
            else
            {
                existing.grow(reachedLimit ? limit : stack.getCount());
            }
            setStackInSlot(slot, existing);
        }
        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        if (amount == 0)
            return ItemStack.EMPTY;
        //validateSlotIndex(slot);
        ItemStack existing = getStackInSlot(slot);
        if (existing.getCount() <= 0)
            return ItemStack.EMPTY;
        int toExtract = Math.min(amount, existing.getMaxStackSize());
        if (existing.getCount() <= toExtract)
        {
            if (!simulate)
            {
                setStackInSlot(slot, ItemStack.EMPTY);
            }
            return existing;
        }
        else
        {
            if (!simulate)
            {
                setStackInSlot(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
            }
            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
        }
    }

    @Override
    public int getSlotLimit(int slot)
    {
        return 64;
    }

    /*private void validateSlotIndex(int slot)
    {
        if (slot < 0 || slot >= getSlots())
            throw new RuntimeException("Slot " + slot + " not is valid range - [0,"+ getSlots() +"]");
    }*/

    public void setContainer(BagContainer container)
    {
        this.container = container;
    }

    public void upgrade(int id)
    {
        NBTTagCompound nbt;
        if (bagStack.hasTagCompound())
            nbt = bagStack.getTagCompound();
        else
            nbt = new NBTTagCompound();

        switch (id)
        {
            case 1://first row
                nbt.setBoolean("R1", true);
                container.UpgradeSlotCount(this);
                container.AddRowSlot(this, 1);
                break;
            case 2://second row
                nbt.setBoolean("R2", true);
                container.UpgradeSlotCount(this);
                container.AddRowSlot(this, 2);
                break;
            case 3://third row
                nbt.setBoolean("R3", true);
                container.UpgradeSlotCount(this);
                container.AddRowSlot(this, 3);
                break;
            case 4://fourth row
                nbt.setBoolean("R4", true);
                container.UpgradeSlotCount(this);
                container.AddRowSlot(this, 4);
                break;
            case 5://fifth row
                nbt.setBoolean("R5", true);
                container.UpgradeSlotCount(this);
                container.AddRowSlot(this, 5);
                break;
            case 6://sixth row
                nbt.setBoolean("R6", true);
                container.UpgradeSlotCount(this);
                container.AddRowSlot(this, 6);
                break;
            case 7://seventh row
                nbt.setBoolean("R7", true);
                container.UpgradeSlotCount(this);
                container.AddRowSlot(this, 7);
                break;
            case 8://eighth row
                nbt.setBoolean("R8", true);
                container.UpgradeSlotCount(this);
                container.AddRowSlot(this, 8);
                break;
            case 9://trash slot
                nbt.setBoolean("Trash", true);
                container.UpgradeSlotCount(this);
                container.AddTrashSlot(this);
                break;
            case 10://crafting
                nbt.setBoolean("Craft", true);
                container.UpgradeSlotCount(this);
                container.AddCraftingSlot(this);
                break;
            case 11://smelting
                nbt.setBoolean("Smelt", true);
                container.UpgradeSlotCount(this);
                container.AddSmeltingSlot(this);
                break;
        }
    }

    public boolean hasUpgrade(int id)
    {
        NBTTagCompound nbt;
        if (bagStack.hasTagCompound())
            nbt = bagStack.getTagCompound();
        else
            nbt = new NBTTagCompound();
        switch (id)
        {
            case 1://first row
                if (nbt.hasKey("R1"))
                    return nbt.getBoolean("R1");
                else return false;
            case 2://second row
                if (nbt.hasKey("R2"))
                    return nbt.getBoolean("R2");
                else return false;
            case 3://third row
                if (nbt.hasKey("R3"))
                    return nbt.getBoolean("R3");
                else return false;
            case 4://fourth row
                if (nbt.hasKey("R4"))
                    return nbt.getBoolean("R4");
                else return false;
            case 5://fifth row
                if (nbt.hasKey("R5"))
                    return nbt.getBoolean("R5");
                else return false;
            case 6://sixth row
                if (nbt.hasKey("R6"))
                    return nbt.getBoolean("R6");
                else return false;
            case 7://seventh row
                if (nbt.hasKey("R7"))
                    return nbt.getBoolean("R7");
                else return false;
            case 8://eighth row
                if (nbt.hasKey("R8"))
                    return nbt.getBoolean("R8");
                else return false;
            case 9://trash slot
                if (nbt.hasKey("Trash"))
                    return nbt.getBoolean("Trash");
                else return false;
            case 10://crafting
                if (nbt.hasKey("Craft"))
                    return nbt.getBoolean("Craft");
                else return false;
            case 11://smelting
                if (nbt.hasKey("Smelt"))
                    return nbt.getBoolean("Smelt");
                else return false;
        }
        return false;
    }


}
