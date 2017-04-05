package jiraiyah.villageinfo.events;

import jiraiyah.villageinfo.VillageInfo;
import jiraiyah.villageinfo.infrastructure.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_QUADS;

public class WorldSpawnHandler
{
    static boolean showSpawnChunks;
    private static BlockPos spawnPoint = null;
    private static boolean chatMessageShown;
    private static List<Integer> xCoords = new ArrayList<>();
    private static List<Integer> zCoords = new ArrayList<>();

    @SubscribeEvent
    public void renderWorldLastEvent(RenderWorldLastEvent event)
    {
        if (!showSpawnChunks || spawnPoint == null || xCoords == null || zCoords == null || xCoords.size() == 0 ||
                zCoords.size() == 0)
            return;
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (player == null)
            return;
        if (!chatMessageShown)
        {

            ITextComponent textComponent = new TextComponentString("Spawn Point : " + TextFormatting.DARK_RED +
                        WorldSpawnHandler.spawnPoint.getX() + ", " + WorldSpawnHandler.spawnPoint.getY() + ", " +
                        WorldSpawnHandler.spawnPoint.getZ());
            player.sendMessage(textComponent);
            chatMessageShown = true;
        }
        VertexBuffer buffer = Tessellator.getInstance().getBuffer();
        float ticks = event.getPartialTicks();
        double plx = player.lastTickPosX + ((player.posX - player.lastTickPosX) * ticks);
        double ply = player.lastTickPosY + ((player.posY - player.lastTickPosY) * ticks);
        double plz = player.lastTickPosZ + ((player.posZ - player.lastTickPosZ) * ticks);
        Vec3d playerPos = new Vec3d(plx, ply, plz);
        double distance = Math.sqrt(playerPos.squareDistanceTo(spawnPoint.getX(), spawnPoint.getY(), spawnPoint.getZ
                ()));
        int radius = 64;
        if (distance > Config.spawnChunkShowDistance)
            return;
        int minX = Integer.MIN_VALUE, minZ = Integer.MIN_VALUE, maxX = Integer.MAX_VALUE, maxZ = Integer.MAX_VALUE;
        for (int i = 0; i < xCoords.size(); i++)
        {
            int finalX = (Math.abs(xCoords.get(i) * 16) + 16) * (int) Math.signum(xCoords.get(i));
            int finalZ = (Math.abs(zCoords.get(i) * 16) + 16) * (int) Math.signum(zCoords.get(i));
            if (minX < finalX)
                minX = finalX;
            if (maxX > finalX)
                maxX = finalX;
            if (minZ < finalZ)
                minZ = finalZ;
            if (maxZ > finalZ)
                maxZ = finalZ;
        }
        if (!VillageInfo.SOLID_DRAW)
        {
            GlStateManager.pushMatrix();
            {
                GlStateManager.translate(minX - plx, 0 - ply, minZ - plz);
                GlStateManager.glLineWidth(1);
                if (VillageInfo.DISABLE_DEPTH)
                    GlStateManager.disableDepth();
                GlStateManager.disableLighting();
                GlStateManager.disableTexture2D();
                {
                    int spacing = 16;
                    for (int y = 0; y <= 256; y+= spacing)
                    {
                        buffer.begin(GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
                        buffer.pos(0f, y, 0f).color(0f,1f,0f,1f).endVertex();
                        buffer.pos(0f, y, (maxZ - minZ)).color(0f,1f,0f,1f).endVertex();
                        buffer.pos((maxX - minX), y, (maxZ - minZ)).color(0f,1f,0f,1f).endVertex();
                        buffer.pos((maxX - minX), y, 0).color(0f,1f,0f,1f).endVertex();
                        buffer.pos(0, y, 0).color(0f,1f,0f,1f).endVertex();
                        Tessellator.getInstance().draw();
                    }

                    buffer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
                    for(int x = (maxX - minX); x <= 0; x+= spacing)
                    {
                        buffer.pos(x, 0, 0).color(1f, 0f,0f,1f).endVertex();
                        buffer.pos(x, 256, 0).color(1f, 0f,0f,1f).endVertex();

                        buffer.pos(x, 0, (maxZ - minZ)).color(1f, 0f,0f,1f).endVertex();
                        buffer.pos(x, 256, (maxZ - minZ)).color(1f, 0f,0f,1f).endVertex();
                    }
                    for(int z = (maxZ - minZ); z <= 0; z+= spacing)
                    {
                        buffer.pos(0, 0, z).color(0f, 0f,1f,1f).endVertex();
                        buffer.pos(0, 256, z).color(0f, 0f,1f,1f).endVertex();

                        buffer.pos((maxX - minX), 0, z).color(0f, 0f,1f,1f).endVertex();
                        buffer.pos((maxX - minX), 256, z).color(0f, 0f,1f,1f).endVertex();
                    }
                    Tessellator.getInstance().draw();
                }
                if (VillageInfo.DISABLE_DEPTH)
                    GlStateManager.enableDepth();
                GlStateManager.enableLighting();
                GlStateManager.enableTexture2D();
            }
            GlStateManager.popMatrix();
        }
        else
        {
            GlStateManager.pushMatrix();
            {
                GlStateManager.translate(minX - plx, 0 - ply, minZ - plz);
                GlStateManager.glLineWidth(1);
                if (VillageInfo.DISABLE_DEPTH)
                    GlStateManager.disableDepth();
                GlStateManager.disableLighting();
                GlStateManager.disableTexture2D();
                GlStateManager.disableCull();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                {
                    buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
                    float opacity = 0.11f;
                    buffer.pos(0f, 0f, 0f).color(0f, 0f, 1f, opacity).endVertex();
                    buffer.pos((maxX - minX), 0f, 0f).color(0f, 0f, 1f, opacity).endVertex();
                    buffer.pos((maxX - minX), 256f, 0f).color(0f, 0f, 1f, opacity).endVertex();
                    buffer.pos(0f, 256f, 0f).color(0f, 0f, 1f, opacity).endVertex();

                    buffer.pos(0f, 0f, (maxZ - minZ)).color(0f, 0f, 1f, opacity).endVertex();
                    buffer.pos((maxX - minX), 0f, (maxZ - minZ)).color(0f, 0f, 1f, opacity).endVertex();
                    buffer.pos((maxX - minX), 256f, (maxZ - minZ)).color(0f, 0f, 1f, opacity).endVertex();
                    buffer.pos(0f, 256f, (maxZ - minZ)).color(0f, 0f, 1f, opacity).endVertex();

                    buffer.pos(0f, 0f, 0f).color(1f, 0f, 0f, opacity).endVertex();
                    buffer.pos(0f, 0f, (maxZ - minZ)).color(1f, 0f, 0f, opacity).endVertex();
                    buffer.pos(0f, 256f, (maxZ - minZ)).color(1f, 0f, 0f, opacity).endVertex();
                    buffer.pos(0f, 256f, 0).color(1f, 0f, 0f, opacity).endVertex();

                    buffer.pos((maxX - minX), 0f, 0f).color(1f, 0f, 0f, opacity).endVertex();
                    buffer.pos((maxX - minX), 0f, (maxZ - minZ)).color(1f, 0f, 0f, opacity).endVertex();
                    buffer.pos((maxX - minX), 256f, (maxZ - minZ)).color(1f, 0f, 0f, opacity).endVertex();
                    buffer.pos((maxX - minX), 256f, 0f).color(1f, 0f, 0f, opacity).endVertex();

                    buffer.pos(0f, 256f, 0f).color(0f, 1f, 0f, opacity).endVertex();
                    buffer.pos(0f, 256f, (maxZ - minZ)).color(0f, 1f, 0f, opacity).endVertex();
                    buffer.pos((maxX - minX), 256f, (maxZ - minZ)).color(0f, 1f, 0f, opacity).endVertex();
                    buffer.pos((maxX - minX), 256f, 0f).color(0f, 1f, 0f, opacity).endVertex();

                    buffer.pos(0f, 0f, 0f).color(0f, 1f, 0f, opacity).endVertex();
                    buffer.pos(0f, 0f, (maxZ - minZ)).color(0f, 1f, 0f, opacity).endVertex();
                    buffer.pos((maxX - minX), 0f, (maxZ - minZ)).color(0f, 1f, 0f, opacity).endVertex();
                    buffer.pos((maxX - minX), 0f, 0f).color(0f, 1f, 0f, opacity).endVertex();
                    Tessellator.getInstance().draw();
                }
                GlStateManager.glLineWidth(1);
                GlStateManager.disableBlend();
                GlStateManager.enableCull();
                if (VillageInfo.DISABLE_DEPTH)
                    GlStateManager.enableDepth();
                GlStateManager.enableLighting();
                GlStateManager.enableTexture2D();
            }
            GlStateManager.popMatrix();

        }
    }

    public static void setSpawnInformation(BlockPos pos, List<Integer> x, List<Integer> z)
    {
        spawnPoint = pos;
        xCoords = x;
        zCoords = z;
    }
}
