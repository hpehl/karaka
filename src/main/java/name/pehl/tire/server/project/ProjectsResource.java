package name.pehl.tire.server.project;

import java.util.Iterator;

import name.pehl.taoki.security.Secured;
import name.pehl.tire.shared.project.Project;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class ProjectsResource extends ServerResource
{
    private final ProjectService service;


    @Inject
    public ProjectsResource(ProjectService service)
    {
        this.service = service;
    }


    @Get("xml")
    public Iterator<Project> listXml()
    {
        return internalListProjects();
    }


    @Get("plain")
    public Iterator<Project> listPlain()
    {
        return internalListProjects();
    }


    @Secured
    protected Iterator<Project> internalListProjects()
    {
        return service.list();
    }
}
