package jiraiyah.extremity.slots;

import jiraiyah.jlib.slots.SpecialSlotItemHandler;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.IItemHandler;

public class BagFurnaceOutputSlot extends SpecialSlotItemHandler
{
    private final EntityPlayer player;
    private int removeCount;

    public BagFurnaceOutputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
        this.player = null;
    }

    public BagFurnaceOutputSlot(EntityPlayer player, IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
        this.player = player;
    }

    public boolean isItemValid(ItemStack stack)
    {
        return false;
    }

    public ItemStack decrStackSize(int amount)
    {
        if (getHasStack())
        {
            removeCount += Math.min(amount, getStack().getCount());
        }

        return super.decrStackSize(amount);
    }

    public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack)
    {
        onCrafting(stack);
        super.onTake(thePlayer, stack);
        return stack;
    }

    protected void onCrafting(ItemStack stack, int amount)
    {
        removeCount += amount;
        onCrafting(stack);
    }

    protected void onCrafting(ItemStack stack)
    {
        stack.onCrafting(player.world, player, removeCount);

        if (!player.world.isRemote)
        {
            int i = removeCount;
            float f = FurnaceRecipes.instance().getSmeltingExperience(stack);

            if (f == 0.0F)
            {
                i = 0;
            }
            else if (f < 1.0F)
            {
                int j = MathHelper.floor((float)i * f);

                if (j < MathHelper.ceil((float)i * f) && Math.random() < (double)((float)i * f - (float)j))
                {
                    ++j;
                }

                i = j;
            }

            while (i > 0)
            {
                int k = EntityXPOrb.getXPSplit(i);
                i -= k;
                player.world.spawnEntity(new EntityXPOrb(player.world, player.posX, player.posY + 0.5D, player.posZ + 0.5D, k));
            }
        }

        removeCount = 0;

        net.minecraftforge.fml.common.FMLCommonHandler.instance().firePlayerSmeltedEvent(player, stack);

        if (stack.getItem() == Items.IRON_INGOT)
        {
            player.addStat(AchievementList.ACQUIRE_IRON);
        }

        if (stack.getItem() == Items.COOKED_FISH)
        {
            player.addStat(AchievementList.COOK_FISH);
        }
    }
}
