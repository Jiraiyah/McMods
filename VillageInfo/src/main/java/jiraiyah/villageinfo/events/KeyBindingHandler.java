package jiraiyah.villageinfo.events;

import jiraiyah.villageinfo.VillageInfo;
import jiraiyah.villageinfo.inits.KeyBindings;
import jiraiyah.villageinfo.network.SpawnPlayerMessage;
import jiraiyah.villageinfo.network.VillagePlayerMessage;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class KeyBindingHandler
{
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if (KeyBindings.VILLAGE_DATA.isPressed())
        {
            VillageInfo.SHOW_VILLAGE = !VillageInfo.SHOW_VILLAGE;
            VillagePlayerMessage.sendMessage(Minecraft.getMinecraft().player.getUniqueID(), VillageInfo.SHOW_VILLAGE);
        }

        if (KeyBindings.VILLAGE_DATA_BORDER.isPressed())
            VillageInfo.VILLAGE_BORDER = !VillageInfo.VILLAGE_BORDER;
        if (KeyBindings.VILLAGE_DATA_DOORS.isPressed())
            VillageInfo.VILLAGE_DOORS = !VillageInfo.VILLAGE_DOORS;
        if (KeyBindings.VILLAGE_DATA_SPHERE.isPressed())
            VillageInfo.VILLAGE_SPHERE = !VillageInfo.VILLAGE_SPHERE;
        if (KeyBindings.VILLAGE_DATA_GOLEM.isPressed())
            VillageInfo.VILLAGE_GOLEM = !VillageInfo.VILLAGE_GOLEM;
        if (KeyBindings.VILLAGE_DATA_INFO.isPressed())
            VillageInfo.VILLAGE_INFO_TEXT = !VillageInfo.VILLAGE_INFO_TEXT;
        if (KeyBindings.VILLAGE_DATA_CENTER.isPressed())
            VillageInfo.VILLAGE_CENTER = !VillageInfo.VILLAGE_CENTER;
        if (KeyBindings.SOLID_DRAW.isPressed())
            VillageInfo.SOLID_DRAW = !VillageInfo.SOLID_DRAW;
        if (KeyBindings.DISABLE_DEPTH.isPressed())
            VillageInfo.DISABLE_DEPTH = !VillageInfo.DISABLE_DEPTH;
        if (KeyBindings.VILLAGE_PER_COLOR.isPressed())
            VillageInfo.PER_VILLAGE_COLOR = !VillageInfo.PER_VILLAGE_COLOR;
        if (KeyBindings.CHUNK_BORDER.isPressed())
            VillageInfo.CHUNK_BORDER = !VillageInfo.CHUNK_BORDER;
        if (KeyBindings.SPAWN_CHUNK.isPressed())
        {
            WorldSpawnHandler.showSpawnChunks = !WorldSpawnHandler.showSpawnChunks;
            SpawnPlayerMessage.sendMessage(Minecraft.getMinecraft().player.getUniqueID());
        }
    }
}