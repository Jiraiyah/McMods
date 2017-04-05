package jiraiyah.extremity.inits.items;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ItemRegisters
{
    public static void register()
    {
        if (ItemInits.ITEM_LIST != null && ItemInits.ITEM_LIST.size() > 0)
            for (Item item : ItemInits.ITEM_LIST)
                if (item != null && ForgeRegistries.ITEMS.getKey(item) == null)
                    GameRegistry.register(item);

        OreDictionary.registerOre("glue", ItemInits.GLUE);
    }
}
