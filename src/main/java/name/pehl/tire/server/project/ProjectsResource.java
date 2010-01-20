package name.pehl.tire.server.project;

import java.io.IOException;
import java.util.List;

import name.pehl.taoki.security.Secured;
import name.pehl.tire.shared.project.Project;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.engine.converter.ConverterUtils;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.representation.Variant;
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


    @Secured
    @Override
    protected Representation get()
    {
        Representation representation = null;
        List<Project> projects = service.list();
        Variant preferredVariant = getPreferredVariant(getVariants());
        ConverterHelper converterHelper = ConverterUtils.getBestHelper(projects, preferredVariant, this);
        try
        {
            representation = converterHelper.toRepresentation(projects, preferredVariant, this);
        }
        catch (IOException e)
        {
            // TODO
        }
        return representation;
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
