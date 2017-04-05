package jiraiyah.jlib.utilities;

import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

public class Log
{
    public static void log(Level logLevel, Object object, String MOD_NAME)
    {
        FMLLog.log( MOD_NAME, logLevel, String.valueOf( object ) );
    }

    public static void log(Level logLevel, Object object)
    {
        FMLLog.log( logLevel, String.valueOf( object ) );
    }

    public static void all ( Object object, String MOD_NAME )
    {
        log( Level.ALL, object, MOD_NAME );
    }

    public static void debug ( Object object, String MOD_NAME )
    {
        log( Level.DEBUG, object, MOD_NAME );
    }

    public static void error ( Object object, String MOD_NAME )
    {
        log( Level.ERROR, object, MOD_NAME );
    }

    public static void fatal ( Object object, String MOD_NAME )
    {
        log( Level.FATAL, object, MOD_NAME );
    }

    public static void info ( Object object, String MOD_NAME )
    {
        log( Level.INFO, object, MOD_NAME );
    }

    public static void off ( Object object, String MOD_NAME )
    {
        log( Level.OFF, object, MOD_NAME );
    }

    public static void trace ( Object object, String MOD_NAME )
    {
        log( Level.TRACE, object, MOD_NAME );
    }

    public static void warn ( Object object, String MOD_NAME )
    {
        log( Level.WARN, object, MOD_NAME );
    }

    public static void all ( Object object )
    {
        log( Level.ALL, object );
    }

    public static void debug ( Object object )
    {
        log( Level.DEBUG, object );
    }

    public static void error ( Object object )
    {
        log( Level.ERROR, object );
    }

    public static void fatal ( Object object )
    {
        log( Level.FATAL, object );
    }

    public static void info ( Object object )
    {
        log( Level.INFO, object );
    }

    public static void off ( Object object )
    {
        log( Level.OFF, object );
    }

    public static void trace ( Object object )
    {
        log( Level.TRACE, object );
    }

    public static void warn ( Object object )
    {
        log( Level.WARN, object );
    }
}
