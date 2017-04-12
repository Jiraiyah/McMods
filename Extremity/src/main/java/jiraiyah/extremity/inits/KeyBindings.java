package jiraiyah.extremity.inits;

import jiraiyah.extremity.references.Reference;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Loader;
import org.lwjgl.input.Keyboard;

public class KeyBindings
{
    public static KeyBinding BAG_INVENTORY;

    public static void register()
    {
        if (Loader.isModLoaded("baubles"))
        {
            BAG_INVENTORY = new KeyBinding(Reference.MOD_ID.toLowerCase() + ".key.openbaginventory",
                    KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_E, Reference.MOD_ID.toLowerCase() +
                    ".key.categories");

            ClientRegistry.registerKeyBinding(BAG_INVENTORY);
        }

    }
}
