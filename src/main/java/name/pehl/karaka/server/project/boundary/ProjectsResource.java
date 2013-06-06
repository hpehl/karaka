package name.pehl.karaka.server.project.boundary;

import name.pehl.karaka.server.project.control.ProjectConverter;
import name.pehl.karaka.server.project.control.ProjectRepository;
import name.pehl.karaka.server.project.entity.Project;
import org.jboss.resteasy.annotations.cache.Cache;
import org.jboss.resteasy.spi.NotFoundException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Supported methods:
 * <ul>
 * <li>GET /projects/: List all projects
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
@Cache(maxAge = 36000)
@Produces(APPLICATION_JSON)
public class ProjectsResource
{
    @Inject ProjectRepository repository;
    @Inject ProjectConverter converter;


    @GET
    public List<name.pehl.karaka.shared.model.Project> list()
    {
        List<Project> projects = repository.list();
        if (projects.isEmpty())
        {
            throw new NotFoundException("No projects found");
        }
        List<name.pehl.karaka.shared.model.Project> result = new ArrayList<name.pehl.karaka.shared.model.Project>();
        for (Project project : projects)
        {
            result.add(converter.toModel(project));
        }
        return result;
    }
}
