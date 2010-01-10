package name.pehl.tire.server.rest;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Finder;
import org.restlet.resource.ServerResource;

import com.google.inject.Injector;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class GuiceFinder extends Finder
{
    private final Injector injector;


    public GuiceFinder(Injector injector, Context context, Class<?> targetClass)
    {
        super(context, targetClass);
        this.injector = injector;
    }


    @Override
    public ServerResource create(Class<? extends ServerResource> targetClass, Request request, Response response)
    {
        return injector.getInstance(targetClass);
    }
}
