package name.pehl.karaka.server;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import name.pehl.karaka.server.activity.boundary.ActivitiesResource;
import name.pehl.karaka.server.client.boundary.ClientsResource;
import name.pehl.karaka.server.project.boundary.ProjectsResource;
import name.pehl.karaka.server.settings.boundary.SettingsResource;
import name.pehl.karaka.server.tag.boundary.TagsResource;

public class TireApplication extends Application
{
    private final Set<Class<?>> classes;


    public TireApplication()
    {
        this.classes = new HashSet<Class<?>>();
        this.classes.add(ActivitiesResource.class);
        this.classes.add(ClientsResource.class);
        this.classes.add(ProjectsResource.class);
        this.classes.add(SettingsResource.class);
        this.classes.add(TagsResource.class);
    }


    @Override
    public Set<Class<?>> getClasses()
    {
        return this.classes;
    }
}
