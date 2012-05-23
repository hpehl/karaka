package name.pehl.tire.server.activity.control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import name.pehl.tire.server.converter.AbstractEntityConverter;
import name.pehl.tire.server.converter.EntityConverter;
import name.pehl.tire.server.project.control.ProjectConverter;
import name.pehl.tire.server.project.control.ProjectRepository;
import name.pehl.tire.server.project.entity.Project;
import name.pehl.tire.server.tag.control.TagConverter;
import name.pehl.tire.server.tag.control.TagRepository;
import name.pehl.tire.server.tag.entity.Tag;
import name.pehl.tire.shared.model.Time;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ActivityConverter extends
        AbstractEntityConverter<name.pehl.tire.server.activity.entity.Activity, name.pehl.tire.shared.model.Activity>
        implements
        EntityConverter<name.pehl.tire.server.activity.entity.Activity, name.pehl.tire.shared.model.Activity>
{
    @Inject ProjectConverter projectConverter;

    @Inject ProjectRepository projectRepository;

    @Inject TagRepository tagRepository;

    @Inject TagConverter tagConverter;


    @Override
    public name.pehl.tire.shared.model.Activity toModel(name.pehl.tire.server.activity.entity.Activity entity)
    {
        assertEntity(entity);
        if (entity.getStart() == null)
        {
            throw new IllegalStateException("Server side activity has no start date");
        }

        // basic properties
        name.pehl.tire.shared.model.Activity model = new name.pehl.tire.shared.model.Activity(String.valueOf(entity
                .getId()), entity.getName());
        model.setStart(new Time(entity.getStart().getDateTime().toDate(), entity.getStart().getYear(), entity
                .getStart().getMonth(), entity.getStart().getWeek(), entity.getStart().getDay()));
        model.setEnd(new Time(entity.getEnd().getDateTime().toDate(), entity.getEnd().getYear(), entity.getEnd()
                .getMonth(), entity.getEnd().getWeek(), entity.getEnd().getDay()));
        model.setPause(entity.getPause());
        model.setMinutes(entity.getMinutes());
        model.setBillable(entity.isBillable());
        model.setStatus(entity.getStatus());

        // relations
        Project project = projectRepository.get(entity.getProject());
        model.setProject(projectConverter.toModel(project));
        List<name.pehl.tire.shared.model.Tag> modelTags = new ArrayList<name.pehl.tire.shared.model.Tag>();
        Collection<Tag> entityTags = tagRepository.ofy().get(entity.getTags()).values();
        for (name.pehl.tire.server.tag.entity.Tag entityTag : entityTags)
        {
            name.pehl.tire.shared.model.Tag modelTag = tagConverter.toModel(entityTag);
            modelTags.add(modelTag);
        }
        model.setTags(modelTags);
        return model;
    }


    @Override
    public name.pehl.tire.server.activity.entity.Activity fromModel(name.pehl.tire.shared.model.Activity model)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
