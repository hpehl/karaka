package name.pehl.tire.server.project;

import name.pehl.taoki.converter.Converter;
import name.pehl.taoki.converter.ConverterFactory;
import name.pehl.taoki.security.Secured;

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
    private final ConverterFactory converterFactory;


    @Inject
    public ProjectsResource(ProjectService service, ConverterFactory converterFactory)
    {
        this.service = service;
        this.converterFactory = converterFactory;
    }


    @Secured
    @Override
    protected Representation get()
    {
        // TODO
        Converter<Project> converter = converterFactory.createConverter(null);
        return converter.convert(service.list(), null);
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
