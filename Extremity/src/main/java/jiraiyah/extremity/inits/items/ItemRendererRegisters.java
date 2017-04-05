package jiraiyah.extremity.inits.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ItemRendererRegisters
{
    public static void register()
    {
        if (ItemInits.ITEM_LIST != null && ItemInits.ITEM_LIST.size() > 0)
            for (Item item : ItemInits.ITEM_LIST)
                if (item != null)
                    registerItem(item);
    }

    private static void registerItem(Item item)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(),
                "inventory"));
    }
}
