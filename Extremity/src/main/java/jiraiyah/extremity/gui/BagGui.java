package jiraiyah.extremity.gui;

import jiraiyah.extremity.containers.BagContainer;
import jiraiyah.extremity.inventories.BagInventory;
import jiraiyah.extremity.items.Bag;
import jiraiyah.extremity.references.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.oredict.OreDictionary;

public class BagGui extends GuiContainer
{
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Reference.MOD_ID,
            "textures/gui/guielements.png");
    private final IInventory playerInventory;
    private final ItemStack bag;
    private final BagInventory bagInventory;

    public BagGui(IInventory playerInventory, IItemHandler bagInventory, int blockedSlot, ItemStack bag, World world)
    {
        super(new BagContainer(playerInventory, bagInventory, blockedSlot, world));
        this.playerInventory = playerInventory;
        this.bag = bag;
        this.bagInventory = (BagInventory) bagInventory;
        this.allowUserInput = false;
        this.xSize = 256;
        this.ySize = 231;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {}

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1f,1f,1f,1f);
        mc.getTextureManager().bindTexture(GUI_TEXTURE);
        int i = (width - xSize) / 2;
        int j = (height - ySize) / 2;
        drawTexturedModalRect(i, j, 0, 0, 4, 4);
        drawTexturedModalRect(i + xSize - 4, j, 10, 0, 4, 4);
        drawTexturedModalRect(i, j + ySize - 4, 0, 12, 4, 4);
        drawTexturedModalRect(i + xSize - 4, j + ySize - 4, 11, 12, 4, 4);

        drawScaledCustomSizeModalRect(i + 4, j, 4, 0, 8, 4, xSize - 8, 4, 256,256);
        drawScaledCustomSizeModalRect(i + 4, j + ySize - 4, 4, 12, 8, 4, xSize - 8, 4, 256,256);
        drawScaledCustomSizeModalRect(i, j + 4, 0, 4, 4, 8, 4, ySize - 8, 256,256);
        drawScaledCustomSizeModalRect(i + xSize - 4, j + 4, 10, 4, 4, 8, 4, ySize - 8, 256,256);

        drawScaledCustomSizeModalRect(i + 4, j + 4, 4, 4, 8, 8, xSize - 8, ySize - 8, 256,256);

        DrawSlot(i , j);

//        DrawEnergy(i, j);
    }

/*    private void DrawEnergy(int x, int y)
    {
        int fullEnergyHeight = ySize - 13;
        int rfEnergy = 0;
        int fullRF = 100000;
        int percentEnrgy = (int)(rfEnergy * fullEnergyHeight / fullRF);
        int startheight = fullEnergyHeight - percentEnrgy + 6;
        //Background
        drawTexturedModalRect(x + 246, y + 4, 21, 190, 6, 2);
        drawScaledCustomSizeModalRect(x + 246, y + 6, 21, 192, 6, 16, 6, fullEnergyHeight, 256,256);
        drawTexturedModalRect(x + 246, y + 224, 21, 208, 6, 2);
        //Energy Bar
        drawTexturedModalRect(x + 248, y + 224, 30, 209, 2, 2);
        drawScaledCustomSizeModalRect(x + 247, y + startheight, 28, 193, 4, 16, 4, percentEnrgy, 256,256);
        drawTexturedModalRect(x + 248, y + startheight - 1, 30, 192, 2, 2);
    }*/

    private void DrawSlot(int x, int y)
    {
        drawTexturedModalRect(x + 227, y + 209, 0, 93, 18, 18);

        for (int i = 1; i < 9; i++)
            if (bagInventory.hasUpgrade(i))
                for (int j = 0; j < 13; j++)
                    drawTexturedModalRect(x + 11 + j * 18, y + 4 + (i - 1) * 18, 0, 55, 18, 18);
            else
                for (int j = 0; j < 13; j++)
                    drawTexturedModalRect(x + 11 + j * 18, y + 4 + (i - 1) * 18, 19, 131, 18, 18);
        //Draw Trash Slot
        if (bagInventory.hasUpgrade(11))
            drawTexturedModalRect(x + 4, y + 209, 0, 17, 18, 18);
        else
            drawTexturedModalRect(x + 4, y + 209, 19, 55, 18, 18);
        //Draw Crafting Slot
        if (bagInventory.hasUpgrade(9))
        {
            drawTexturedModalRect(x + 40, y + 209, 0, 36, 18, 18);
            for (int x1 = 0; x1 < 3; x1++)
            {
                for (int y1 = 0; y1 < 3; y1 ++ )
                {
                    drawTexturedModalRect(x + 4 + x1 * 18, y + 151 + y1 * 18, 0, 55, 18, 18);
                }
            }
        }
        else
        {
            drawTexturedModalRect(x + 40, y + 209, 19, 93, 18, 18);
            for (int x1 = 0; x1 < 3; x1++)
            {
                for (int y1 = 0; y1 < 3; y1 ++ )
                {
                    drawTexturedModalRect(x + 4 + x1 * 18, y + 151 + y1 * 18, 19, 131, 18, 18);
                }
            }
        }
        //Draw Smelting Slot
        if (bagInventory.hasUpgrade(10))
        {
            drawTexturedModalRect(x + 227, y + 188, 0, 36, 18, 18);
            drawTexturedModalRect(x + 227, y + 167, 0, 74, 18, 18);
            drawTexturedModalRect(x + 227, y + 149, 0, 55, 18, 18);
        }
        else
        {
            drawTexturedModalRect(x + 227, y + 188, 19, 93, 18, 18);
            drawTexturedModalRect(x + 227, y + 167, 19, 150, 18, 18);
            drawTexturedModalRect(x + 227, y + 149, 19, 131, 18, 18);
        }
        //Player Inventory
        for (int l = 0; l < 3; l++)
        {
            for(int j = 0; j < 9; j++)
            {
                drawTexturedModalRect(x + 61 + j * 18, y + 151 + l * 18, 0, 55, 18, 18);
            }
        }
        for(int j = 0; j < 9; j++)
        {
            drawTexturedModalRect(x + 61 + j * 18, y + 209, 0, 55, 18, 18);
        }

    }
}
