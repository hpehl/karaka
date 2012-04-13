package name.pehl.tire.server.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import name.pehl.tire.server.activity.boundary.ActivitiesResource;

public class TireApplication extends Application
{
    private final Set<Class<?>> classes;


    public TireApplication()
    {
        this.classes = new HashSet<Class<?>>();
        this.classes.add(ActivitiesResource.class);
    }


    @Override
    public Set<Class<?>> getClasses()
    {
        return this.classes;
    }
}
