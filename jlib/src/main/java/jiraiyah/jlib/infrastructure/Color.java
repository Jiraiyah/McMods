package jiraiyah.jlib.infrastructure;

import jiraiyah.jlib.interfaces.ICopy;
import jiraiyah.jlib.utilities.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

public abstract class Color implements ICopy<Color>
{
    public byte r;
    public byte g;
    public byte b;
    public byte a;

    public Color(int r, int g, int b, int a)
    {
        this.r = (byte) r;
        this.g = (byte) g;
        this.b = (byte) b;
        this.a = (byte) a;
    }

    public Color(Color color)
    {
        r = color.r;
        g = color.g;
        b = color.b;
        a = color.a;
    }

    @SideOnly(Side.CLIENT)
    public void glColor()
    {
        GL11.glColor4ub(r, g, b, a);
    }

    @SideOnly(Side.CLIENT)
    public void glColor(int a)
    {
        GL11.glColor4ub(r, g, b, (byte) a);
    }

    public Color add(Color color2)
    {
        a += color2.a;
        r += color2.r;
        g += color2.g;
        b += color2.b;
        return this;
    }

    public Color sub(Color color2)
    {
        int ia = (a & 0xFF) - (color2.a & 0xFF);
        int ir = (r & 0xFF) - (color2.r & 0xFF);
        int ig = (g & 0xFF) - (color2.g & 0xFF);
        int ib = (b & 0xFF) - (color2.b & 0xFF);
        a = (byte) (ia < 0 ? 0 : ia);
        r = (byte) (ir < 0 ? 0 : ir);
        g = (byte) (ig < 0 ? 0 : ig);
        b = (byte) (ib < 0 ? 0 : ib);
        return this;
    }

    public Color invert()
    {
        a = (byte) (0xFF - (a & 0xFF));
        r = (byte) (0xFF - (r & 0xFF));
        g = (byte) (0xFF - (g & 0xFF));
        b = (byte) (0xFF - (b & 0xFF));
        return this;
    }

    public Color multiply(Color color2)
    {
        a = (byte) ((a & 0xFF) * ((color2.a & 0xFF) / 255D));
        r = (byte) ((r & 0xFF) * ((color2.r & 0xFF) / 255D));
        g = (byte) ((g & 0xFF) * ((color2.g & 0xFF) / 255D));
        b = (byte) ((b & 0xFF) * ((color2.b & 0xFF) / 255D));
        return this;
    }

    public Color scale(double d)
    {
        a = (byte) ((a & 0xFF) * d);
        r = (byte) ((r & 0xFF) * d);
        g = (byte) ((g & 0xFF) * d);
        b = (byte) ((b & 0xFF) * d);
        return this;
    }

    public Color interpolate(Color color2, double d)
    {
        return this.add(color2.copy().sub(this).scale(d));
    }

    public Color multiplyC(double d)
    {
        r = (byte) MathHelper.clip((r & 0xFF) * d, 0, 255);
        g = (byte) MathHelper.clip((g & 0xFF) * d, 0, 255);
        b = (byte) MathHelper.clip((b & 0xFF) * d, 0, 255);

        return this;
    }

    public int rgb()
    {
        return (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);
    }

    public int argb()
    {
        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);
    }

    public int rgba()
    {
        return (r & 0xFF) << 24 | (g & 0xFF) << 16 | (b & 0xFF) << 8 | (a & 0xFF);
    }

    public Color set(Color color)
    {
        r = color.r;
        g = color.g;
        b = color.b;
        a = color.a;
        return this;
    }

    public boolean equals(Color color)
    {
        return color != null && rgba() == color.rgba();
    }

    public abstract Color copy();
    public abstract int pack();
}
