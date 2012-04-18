package name.pehl.tire.server.activity.control;

import javax.inject.Inject;

import name.pehl.tire.server.project.control.ProjectConverter;
import name.pehl.tire.server.project.control.ProjectRepository;
import name.pehl.tire.server.project.entity.Project;
import name.pehl.tire.server.tag.control.TagConverter;

import com.google.appengine.api.datastore.EntityNotFoundException;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ActivityConverter
{
    @Inject
    ProjectConverter projectConverter;

    @Inject
    ProjectRepository projectRepository;

    @Inject
    TagConverter tagConverter;


    public name.pehl.tire.shared.model.Activity toModel(name.pehl.tire.server.activity.entity.Activity entity)
    {
        if (entity == null)
        {
            throw new IllegalStateException("Server side activity is null");
        }
        if (entity.isTransient())
        {
            throw new IllegalStateException("Server side activity is transient");
        }
        if (entity.getStart() == null)
        {
            throw new IllegalStateException("Server side activity has no start date");
        }

        // basic properties
        name.pehl.tire.shared.model.Activity model = new name.pehl.tire.shared.model.Activity(String.valueOf(entity
                .getId()), entity.getName(), entity.getStart().getDate());
        model.setEnd(entity.getEnd().getDate());
        model.setPause(entity.getPause());
        model.setMinutes(entity.getMinutes());
        model.setBillable(entity.isBillable());
        model.setStatus(entity.getStatus());

        // relations
        Project project;
        try
        {
            project = projectRepository.get(entity.getProject());
            model.setProject(projectConverter.toModel(project));
        }
        catch (EntityNotFoundException e)
        {
            // TODO Auto-generated catch block
        }
        // TODO Tags
        return model;
    }
}
