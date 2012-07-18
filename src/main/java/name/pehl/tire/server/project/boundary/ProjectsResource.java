package name.pehl.tire.server.project.boundary;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import name.pehl.tire.server.paging.entity.PageResult;
import name.pehl.tire.server.project.control.ProjectConverter;
import name.pehl.tire.server.project.control.ProjectRepository;
import name.pehl.tire.server.project.entity.Project;

import org.jboss.resteasy.spi.NotFoundException;

/**
 * Supported methods:
 * <ul>
 * <li>GET /projects/: List all projects activities are stored
 * <li>POST: Create a new project
 * <li>PUT /projects/{id}: Update an existing project
 * <li>DELETE /projects/{id}: Delete an existing project
 * </ul>
 * 
 * @todo implement ETag
 * @author $Author: harald.pehl $
 * @version $Date: 2011-05-16 12:54:26 +0200 (Mo, 16. Mai 2011) $ $Revision: 110
 *          $
 */
@Path("/projects")
@Produces(MediaType.APPLICATION_JSON)
public class ProjectsResource
{
    @Inject ProjectRepository repository;
    @Inject ProjectConverter converter;


    @GET
    public List<name.pehl.tire.shared.model.Project> list()
    {
        PageResult<Project> projects = repository.list();
        if (projects.isEmpty())
        {
            throw new NotFoundException("No projects found");
        }
        List<name.pehl.tire.shared.model.Project> result = new ArrayList<name.pehl.tire.shared.model.Project>();
        for (Project project : projects)
        {
            result.add(converter.toModel(project));
        }
        return result;
    }
}
