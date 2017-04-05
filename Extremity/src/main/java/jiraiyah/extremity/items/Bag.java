package jiraiyah.extremity.items;

import jiraiyah.extremity.Extremity;
import jiraiyah.extremity.gui.GuiHandler;
import jiraiyah.extremity.inventories.BagInventory;
import jiraiyah.extremity.references.Names;
import jiraiyah.extremity.references.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Bag extends Item
{
    @CapabilityInject(IItemHandler.class)
    public static Capability<IItemHandler> ITEM_HANDLER;

    public Bag()
    {
        setUnlocalizedName(Reference.MOD_ID.toLowerCase() + "." + Names.BAG_NAME.toLowerCase());
        setRegistryName(Names.BAG_NAME);
        setMaxStackSize(1);
        setCreativeTab(Extremity.CREATIVE_TAB);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 1;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return !ItemStack.areItemsEqual(oldStack, newStack);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        return new ICapabilityProvider()
        {
            final ItemStack itemStack = stack;

            @Override
            public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
            {
                return capability == ITEM_HANDLER;
            }

            @SuppressWarnings("unchecked")
            @Nullable
            @Override
            public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
            {
                if (capability == ITEM_HANDLER)
                    return (T) getItems(stack);
                return null;
            }
        };
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        player.openGui(Extremity.INSTANCE, GuiHandler.BAG_ID, worldIn, hand.ordinal(), 0, 0);
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand)
    {
        if (!player.isSneaking())
        {
            player.openGui(Extremity.INSTANCE, GuiHandler.BAG_ID, worldIn, hand.ordinal(), 0, 0);
        }
        else
        {
            //TODO : Check the block in front of the player if it has inventory, empty the bag into it
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    public static BagInventory getItems(ItemStack stack)
    {
        return new BagInventory(stack);
    }
}
