package name.pehl.karaka.client.logging;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author Harald Pehl
 * @date 11/08/2012
 */
public class Logger
{
    public static void debug(final Category category, final String message) {Log.debug(category.name(), message);}

    public static void debug(final Category category, final String message, final JavaScriptObject e)
    {
        Log.debug(category.name(), message, e);
    }

    public static void debug(final Category category, final String message, final Throwable e)
    {
        Log.debug(category.name(), message, e);
    }

    public static void error(final Category category, final String message) {Log.error(category.name(), message);}

    public static void error(final Category category, final String message, final JavaScriptObject e)
    {
        Log.error(category.name(), message, e);
    }

    public static void error(final Category category, final String message, final Throwable e)
    {
        Log.error(category.name(), message, e);
    }

    public static void fatal(final Category category, final String message) {Log.fatal(category.name(), message);}

    public static void fatal(final Category category, final String message, final JavaScriptObject e)
    {
        Log.fatal(category.name(), message, e);
    }

    public static void fatal(final Category category, final String message, final Throwable e)
    {
        Log.fatal(category.name(), message, e);
    }

    public static void info(final Category category, final String message) {Log.info(category.name(), message);}

    public static void info(final Category category, final String message, final JavaScriptObject e)
    {
        Log.info(category.name(), message, e);
    }

    public static void info(final Category category, final String message, final Throwable e)
    {
        Log.info(category.name(), message, e);
    }

    public static boolean isDebugEnabled() {return Log.isDebugEnabled();}

    public static boolean isErrorEnabled() {return Log.isErrorEnabled();}

    public static boolean isFatalEnabled() {return Log.isFatalEnabled();}

    public static boolean isInfoEnabled() {return Log.isInfoEnabled();}

    public static boolean isLoggingEnabled() {return Log.isLoggingEnabled();}

    public static boolean isTraceEnabled() {return Log.isTraceEnabled();}

    public static boolean isWarnEnabled() {return Log.isWarnEnabled();}

    public static void trace(final Category category, final String message) {Log.trace(category.name(), message);}

    public static void trace(final Category category, final String message, final JavaScriptObject e)
    {
        Log.trace(category.name(), message, e);
    }

    public static void trace(final Category category, final String message, final Throwable e)
    {
        Log.trace(category.name(), message, e);
    }

    public static void warn(final Category category, final String message) {Log.warn(category.name(), message);}

    public static void warn(final Category category, final String message, final JavaScriptObject e)
    {
        Log.warn(category.name(), message, e);
    }

    public static void warn(final Category category, final String message, final Throwable e)
    {
        Log.warn(category.name(), message, e);
    }


    public static enum Category
    {
        activity, bootstrap, cache, client, dispatch, project, report, rest, settings, tags;
    }
}
