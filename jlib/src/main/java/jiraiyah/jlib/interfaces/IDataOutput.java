package jiraiyah.jlib.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public interface IDataOutput
{
    IDataOutput writeLong(long l);
    IDataOutput writeInt(int i);
    IDataOutput writeShort(short s);
    IDataOutput writeByte(byte b);
    IDataOutput writeDouble(double d);
    IDataOutput writeFloat(float f);
    IDataOutput writeBoolean(boolean b);
    IDataOutput writeChar(char c);
    IDataOutput writeVarInt(int i);
    IDataOutput writeVarShort(int s);
    IDataOutput writeByteArray(byte[] array);
    IDataOutput writeString(String s);
    IDataOutput writeCoords(int x, int y, int z);
    IDataOutput writeCoords(BlockPos pos);
    IDataOutput writeNBTCompound(NBTTagCompound tag);
    IDataOutput writeItemStack(ItemStack stack);
}
