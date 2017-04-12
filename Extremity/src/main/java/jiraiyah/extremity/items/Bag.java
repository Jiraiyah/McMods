package jiraiyah.extremity.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import jiraiyah.extremity.Extremity;
import jiraiyah.extremity.gui.GuiHandler;
import jiraiyah.extremity.inventories.BagInventory;
import jiraiyah.extremity.references.Names;
import jiraiyah.extremity.references.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@Optional.Interface(modid = "baubles", iface = "baubles.api.IBauble")
public class Bag extends Item implements IBauble
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
        if (worldIn.isRemote)
            return EnumActionResult.SUCCESS;
        player.openGui(Extremity.INSTANCE, GuiHandler.BAG_ID, worldIn, 0, hand.ordinal(), 0);
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand)
    {
        if (worldIn.isRemote)
            return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
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

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        handleUpdate(stack, world, entity);
    }

    private void handleUpdate(ItemStack stack, World world, Entity entity)
    {
        //Read Values From Itemstack NBT
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null)
            compound = new NBTTagCompound();
        NonNullList<ItemStack> furnaceItemStacks = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);
        furnaceItemStacks = getFurnaceItems(compound, furnaceItemStacks);
        int furnaceBurnTime = compound.getInteger("BurnTime");
        int cookTime = compound.getInteger("CookTime");
        int cookingTime = compound.getInteger("CookingTime");
        int totalCookTime = compound.getInteger("CookTimeTotal");
        int currentItemBurnTime = getItemBurnTime(furnaceItemStacks.get(1));

        //Smelt

        boolean isBurning = furnaceBurnTime > 0;
        boolean flag1 = false;

        if (isBurning)
        {
            --furnaceBurnTime;
        }
        /*if (!world.isRemote)
        {*/
            ItemStack itemstack = furnaceItemStacks.get(1);
            if (isBurning || !itemstack.isEmpty() && !furnaceItemStacks.get(0).isEmpty())
            {
                if (!isBurning && canSmelt(furnaceItemStacks))
                {
                    furnaceBurnTime = getItemBurnTime(itemstack);
                    currentItemBurnTime = furnaceBurnTime;

                    if (isBurning)
                    {
                        flag1 = true;

                        if (!itemstack.isEmpty())
                        {
                            Item item = itemstack.getItem();
                            itemstack.shrink(1);

                            if (itemstack.isEmpty())
                            {
                                ItemStack item1 = item.getContainerItem(itemstack);
                                furnaceItemStacks.set(1, item1);
                            }
                        }
                    }
                }

                if (isBurning && this.canSmelt(furnaceItemStacks))
                {
                    ++cookTime;

                    if (cookTime == totalCookTime)
                    {
                        cookTime = 0;
                        totalCookTime = cookingTime;
                        smeltItem(furnaceItemStacks);
                        flag1 = true;
                    }
                }
                else
                {
                    cookTime = 0;
                }
            }
            else if (!isBurning && cookTime > 0)
            {
                cookTime = MathHelper.clamp(cookTime - 2, 0, totalCookTime);
            }
            if (flag1)
            {
                world.getChunkFromBlockCoords(entity.getPosition()).setChunkModified();
            }
        //}
        //TODO : Fix this !!!!
        //setFurnaceItems(compound, furnaceItemStacks);
        compound.setInteger("BurnTime", (short)furnaceBurnTime);
        compound.setInteger("CookTime", (short)cookTime);
        compound.setInteger("CookingTime", (short)cookingTime);
        compound.setInteger("CookTimeTotal", (short)totalCookTime);
        stack.setTagCompound(compound);
    }

    private NonNullList<ItemStack> getFurnaceItems(NBTTagCompound tag, NonNullList<ItemStack> list)
    {
        NBTTagList nbttaglist = tag.getTagList(Names.NBT.BAG_FURNACE_ITEM, 10);

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte(Names.NBT.BAG_FURNACE_SLOT) & 255;

            if (j >= 0 && j < list.size())
            {
                list.set(j, new ItemStack(nbttagcompound));
            }
        }
        return list;
    }

    private NBTTagCompound setFurnaceItems(NBTTagCompound tag, NonNullList<ItemStack> list)
    {
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < list.size(); ++i)
        {
            ItemStack itemstack = (ItemStack)list.get(i);

            if (!itemstack.isEmpty())
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte(Names.NBT.BAG_FURNACE_SLOT, (byte)i);
                itemstack.writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }

        tag.setTag(Names.NBT.BAG_FURNACE_ITEM, nbttaglist);

        return tag;
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack)
    {
        return BaubleType.BODY;
    }

    @Override
    public boolean willAutoSync(ItemStack itemstack, EntityLivingBase player)
    {
        return true;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player)
    {
        handleUpdate(itemstack, player.world, player);
    }

    private boolean canSmelt(List<ItemStack> furnaceItemStacks)
    {
        if ((furnaceItemStacks.get(0)).isEmpty())
        {
            return false;
        }
        else
        {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(furnaceItemStacks.get(0));

            if (itemstack.isEmpty())
            {
                return false;
            }
            else
            {
                ItemStack itemstack1 = furnaceItemStacks.get(2);
                if (itemstack1.isEmpty()) return true;
                if (!itemstack1.isItemEqual(itemstack)) return false;
                int result = itemstack1.getCount() + itemstack.getCount();
                return result <= 64 && result <= itemstack1.getMaxStackSize();
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    private static int getItemBurnTime(ItemStack stack)
    {
        if (stack.isEmpty())
        {
            return 0;
        }
        else
        {
            Item item = stack.getItem();
            if (!item.getRegistryName().getResourceDomain().equals("minecraft"))
            {
                int burnTime = net.minecraftforge.fml.common.registry.GameRegistry.getFuelValue(stack);
                if (burnTime != 0) return burnTime;
            }
            return item == Item.getItemFromBlock(Blocks.WOODEN_SLAB) ? 150 : (item == Item.getItemFromBlock(Blocks.WOOL) ? 100 : (item == Item.getItemFromBlock(Blocks.CARPET) ? 67 : (item == Item.getItemFromBlock(Blocks.LADDER) ? 300 : (item == Item.getItemFromBlock(Blocks.WOODEN_BUTTON) ? 100 : (Block.getBlockFromItem(item).getDefaultState().getMaterial() == Material.WOOD ? 300 : (item == Item.getItemFromBlock(Blocks.COAL_BLOCK) ? 16000 : (item instanceof ItemTool && "WOOD".equals(((ItemTool)item).getToolMaterialName()) ? 200 : (item instanceof ItemSword && "WOOD".equals(((ItemSword)item).getToolMaterialName()) ? 200 : (item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).getMaterialName()) ? 200 : (item == Items.STICK ? 100 : (item != Items.BOW && item != Items.FISHING_ROD ? (item == Items.SIGN ? 200 : (item == Items.COAL ? 1600 : (item == Items.LAVA_BUCKET ? 20000 : (item != Item.getItemFromBlock(Blocks.SAPLING) && item != Items.BOWL ? (item == Items.BLAZE_ROD ? 2400 : (item instanceof ItemDoor && item != Items.IRON_DOOR ? 200 : (item instanceof ItemBoat ? 400 : 0))) : 100)))) : 300)))))))))));
        }
    }

    private void smeltItem(List<ItemStack> furnaceItemStacks)
    {
        if (this.canSmelt(furnaceItemStacks))
        {
            ItemStack itemstack = furnaceItemStacks.get(0);
            ItemStack itemstack1 = FurnaceRecipes.instance().getSmeltingResult(itemstack);
            ItemStack itemstack2 = furnaceItemStacks.get(2);

            if (itemstack2.isEmpty())
            {
                furnaceItemStacks.set(2, itemstack1.copy());
            }
            else if (itemstack2.getItem() == itemstack1.getItem())
            {
                itemstack2.grow(itemstack1.getCount());
            }

            if (itemstack.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && itemstack.getMetadata() == 1 && !(furnaceItemStacks.get(1)).isEmpty() && furnaceItemStacks.get(1).getItem() == Items.BUCKET)
            {
                furnaceItemStacks.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.shrink(1);
        }
    }

    public static boolean isItemFuel(ItemStack stack)
    {
        return getItemBurnTime(stack) > 0;
    }
}
