package name.pehl.tire.server.activity.control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import name.pehl.tire.server.converter.AbstractEntityConverter;
import name.pehl.tire.server.converter.EntityConverter;
import name.pehl.tire.server.project.control.ProjectConverter;
import name.pehl.tire.server.project.control.ProjectRepository;
import name.pehl.tire.server.settings.control.CurrentSettings;
import name.pehl.tire.server.settings.entity.Settings;
import name.pehl.tire.server.tag.control.TagConverter;
import name.pehl.tire.server.tag.control.TagRepository;
import name.pehl.tire.shared.model.Activity;

import com.googlecode.objectify.Key;

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
    @Inject @CurrentSettings Settings settings;


    @Override
    public name.pehl.tire.shared.model.Activity toModel(name.pehl.tire.server.activity.entity.Activity entity)
    {
        assertEntity(entity);
        if (entity.getStart() == null)
        {
            throw new IllegalStateException("Server side activity has no start date");
        }

        // basic properties
        name.pehl.tire.shared.model.Activity model = new name.pehl.tire.shared.model.Activity(websafeKey(
                name.pehl.tire.server.activity.entity.Activity.class, entity), entity.getName());
        model.setDescription(entity.getDescription());
        model.setStart(new name.pehl.tire.shared.model.Time(entity.getStart().getDateTime().toDate(), entity.getStart()
                .getYear(), entity.getStart().getMonth(), entity.getStart().getWeek(), entity.getStart().getDay()));
        model.setEnd(new name.pehl.tire.shared.model.Time(entity.getEnd().getDateTime().toDate(), entity.getEnd()
                .getYear(), entity.getEnd().getMonth(), entity.getEnd().getWeek(), entity.getEnd().getDay()));
        model.setPause(entity.getPause());
        model.setMinutes(entity.getMinutes());
        model.setBillable(entity.isBillable());
        model.setStatus(entity.getStatus());

        // relations
        name.pehl.tire.server.project.entity.Project project = projectRepository.get(entity.getProject());
        model.setProject(projectConverter.toModel(project));
        List<name.pehl.tire.shared.model.Tag> modelTags = new ArrayList<name.pehl.tire.shared.model.Tag>();
        Collection<name.pehl.tire.server.tag.entity.Tag> entityTags = tagRepository.ofy().get(entity.getTags())
                .values();
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
        assertModel(model);
        name.pehl.tire.server.activity.entity.Activity entity = new name.pehl.tire.server.activity.entity.Activity(
                model.getName(), settings.getTimeZone());
        internalModelToEntity(model, entity);
        return entity;
    }


    @Override
    public void merge(name.pehl.tire.shared.model.Activity model, name.pehl.tire.server.activity.entity.Activity entity)
    {
        assertNonTransientModel(model);
        entity.setName(model.getName());
        internalModelToEntity(model, entity);
    }


    private void internalModelToEntity(Activity model, name.pehl.tire.server.activity.entity.Activity entity)
    {
        // basic properties
        entity.setDescription(model.getDescription());
        if (model.getStart() != null)
        {
            entity.setStart(new name.pehl.tire.server.activity.entity.Time(model.getStart().getDate(), settings
                    .getTimeZone()));
        }
        else
        {
            entity.setStart(new name.pehl.tire.server.activity.entity.Time(new Date(), settings.getTimeZone()));
        }
        if (model.getEnd() != null)
        {
            entity.setEnd(new name.pehl.tire.server.activity.entity.Time(model.getEnd().getDate(), settings
                    .getTimeZone()));
        }
        else
        {
            entity.setEnd(new name.pehl.tire.server.activity.entity.Time(new Date(), settings.getTimeZone()));
        }
        entity.setPause(model.getPause());
        entity.setBillable(model.isBillable());
        entity.setStatus(model.getStatus());

        // relations
        Key<name.pehl.tire.server.project.entity.Project> projectKey = null;
        if (model.getProject() != null)
        {
            if (model.getProject().isTransient())
            {
                // TODO Store new project
            }
            projectKey = Key.create(model.getProject().getId());
        }
        entity.setProject(projectKey);
        List<Key<name.pehl.tire.server.tag.entity.Tag>> tags = new ArrayList<Key<name.pehl.tire.server.tag.entity.Tag>>();
        if (model.getTags() != null && !model.getTags().isEmpty())
        {
            for (name.pehl.tire.shared.model.Tag tag : model.getTags())
            {
                tags.add(Key.<name.pehl.tire.server.tag.entity.Tag> create(tag.getId()));
            }
        }
        entity.setTags(tags);
    }
}
