package name.pehl.tire.server.project;

import java.util.List;

import name.pehl.tire.shared.model.Project;

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
    public List<Project> listXml()
    {
        return internalListProjects();
    }


    @Get("plain")
    public List<Project> listPlain()
    {
        return internalListProjects();
    }


    protected List<Project> internalListProjects()
    {
        return service.list();
    }
}
