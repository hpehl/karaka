package name.pehl.tire.server.project;

import name.pehl.taoki.security.Secured;
import name.pehl.taoki.xml.Context;
import name.pehl.taoki.xml.TemplateConverter;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class ProjectsResource extends ServerResource
{
    private final ProjectService service;
    private final TemplateConverter converter;


    @Inject
    public ProjectsResource(ProjectService service, TemplateConverter converter)
    {
        this.service = service;
        this.converter = converter;
    }


    @Secured
    @Override
    protected Representation get()
    {
        Context context = new Context();
        context.set("projects", service.list());
        String xml = converter.convert("templates/projects.vm", context);
        return new StringRepresentation(xml, MediaType.TEXT_XML);
    }


    @Override
    protected Representation post(Representation entity)
    {
        Representation result = null;

        Form form = new Form(entity);
        String name = form.getFirstValue("name");
        String description = form.getFirstValue("description");
        Project project = new Project(name, description);
        service.save(project);
        setStatus(Status.SUCCESS_CREATED);
        Representation rep = new StringRepresentation("Project created", MediaType.TEXT_PLAIN);
        rep.setIdentifier(getRequest().getResourceRef().getIdentifier() + "/" + project.getKey());
        result = rep;

        return result;
    }
}
