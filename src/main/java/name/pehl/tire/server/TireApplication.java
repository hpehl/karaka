package name.pehl.tire.server;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import name.pehl.tire.server.activity.boundary.ActivitiesResource;
import name.pehl.tire.server.settings.boundary.SettingsResource;

public class TireApplication extends Application
{
    private final Set<Class<?>> classes;


    public TireApplication()
    {
        this.classes = new HashSet<Class<?>>();
        this.classes.add(ActivitiesResource.class);
        this.classes.add(SettingsResource.class);
    }


    @Override
    public Set<Class<?>> getClasses()
    {
        return this.classes;
    }
}
