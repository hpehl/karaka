package name.pehl.karaka.server.activity.control;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import name.pehl.karaka.server.converter.AbstractEntityConverter;
import name.pehl.karaka.server.converter.EntityConverter;
import name.pehl.karaka.server.project.control.ProjectConverter;
import name.pehl.karaka.server.project.control.ProjectRepository;
import name.pehl.karaka.server.settings.control.CurrentSettings;
import name.pehl.karaka.server.settings.entity.Settings;
import name.pehl.karaka.server.tag.control.TagConverter;
import name.pehl.karaka.server.tag.control.TagRepository;
import name.pehl.karaka.shared.model.Duration;
import org.joda.time.DateTime;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ActivityConverter extends
        AbstractEntityConverter<name.pehl.karaka.server.activity.entity.Activity, name.pehl.karaka.shared.model.Activity>
        implements
        EntityConverter<name.pehl.karaka.server.activity.entity.Activity, name.pehl.karaka.shared.model.Activity>
{
    @Inject ProjectConverter projectConverter;
    @Inject ProjectRepository projectRepository;
    @Inject TagRepository tagRepository;
    @Inject TagConverter tagConverter;
    @Inject @CurrentSettings Instance<Settings> settings;

    @Override
    public name.pehl.karaka.shared.model.Activity toModel(name.pehl.karaka.server.activity.entity.Activity entity)
    {
        assertEntity(entity);
        if (entity.getStart() == null)
        {
            throw new IllegalStateException("Server side activity has no start date");
        }

        // basic properties
        name.pehl.karaka.shared.model.Activity model = new name.pehl.karaka.shared.model.Activity(websafeKey(
                name.pehl.karaka.server.activity.entity.Activity.class, entity), entity.getName());
        model.setDescription(entity.getDescription());
        model.setStart(
                new name.pehl.karaka.shared.model.Time(entity.getStart().toDate(), entity.getStart()
                        .getYear(), entity.getStart().getMonth(), entity.getStart().getWeek(),
                        entity.getStart().getDay()));
        model.setEnd(new name.pehl.karaka.shared.model.Time(entity.getEnd().toDate(), entity.getEnd()
                .getYear(), entity.getEnd().getMonth(), entity.getEnd().getWeek(), entity.getEnd().getDay()));
        model.setPause(new Duration(entity.getPause()));
        model.setDuration(new Duration(entity.getDuration()));
        model.setBillable(entity.isBillable());
        model.setStatus(entity.getStatus());

        // relations
        try
        {
            name.pehl.karaka.server.project.entity.Project project = projectRepository.get(entity.getProject());
            model.setProject(projectConverter.toModel(project));
        }
        catch (NotFoundException e)
        {
            // no project - no conversion
        }
        try
        {
            List<name.pehl.karaka.shared.model.Tag> modelTags = new ArrayList<name.pehl.karaka.shared.model.Tag>();
            Collection<name.pehl.karaka.server.tag.entity.Tag> entityTags = tagRepository.ofy().get(entity.getTags())
                    .values();
            for (name.pehl.karaka.server.tag.entity.Tag entityTag : entityTags)
            {
                name.pehl.karaka.shared.model.Tag modelTag = tagConverter.toModel(entityTag);
                modelTags.add(modelTag);
            }
            model.setTags(modelTags);
        }
        catch (NotFoundException e)
        {
            // no tags - no conversion
        }
        return model;
    }

    @Override
    public name.pehl.karaka.server.activity.entity.Activity fromModel(name.pehl.karaka.shared.model.Activity model)
    {
        assertModel(model);
        name.pehl.karaka.server.activity.entity.Activity entity = new name.pehl.karaka.server.activity.entity.Activity(
                model.getName(), settings.get().getTimeZone());
        internalModelToEntity(model, entity);
        return entity;
    }

    @Override
    public void merge(name.pehl.karaka.shared.model.Activity model,
            name.pehl.karaka.server.activity.entity.Activity entity)
    {
        assertNonTransientModel(model);
        entity.setName(model.getName());
        internalModelToEntity(model, entity);
    }

    /**
     * Transforms the client side activity into a server side activity. Normally the init and end time is taken from
     * the client side activity and the minutes are calculated. There's one exception to this behaviour: If </p> <ul>
     * <li>the activity is stopped <li>the init time is present <li>the end time is not present and <li>the minutes
     * are
     * specified </ul>
     * <p/>
     * then the end time is calculated.
     *
     * @param model
     * @param entity
     */
    private void internalModelToEntity(name.pehl.karaka.shared.model.Activity model,
            name.pehl.karaka.server.activity.entity.Activity entity)
    {
        // basic properties
        entity.setDescription(model.getDescription());
        if (model.getStart() != null)
        {
            entity.setStart(new name.pehl.karaka.server.activity.entity.Time(model.getStart().getDate(), settings
                    .get().getTimeZone()));
        }
        else
        {
            entity.setStart(new name.pehl.karaka.server.activity.entity.Time(new Date(), settings.get().getTimeZone()));
        }
        if (model.getEnd() != null)
        {
            entity.setEnd(new name.pehl.karaka.server.activity.entity.Time(model.getEnd().getDate(), settings
                    .get().getTimeZone()));
        }
        else
        {
            // Normally the end time is taken from the model and the duration in minutes is calculated.
            // There's one exception to this rule: If
            //   - the activity is stopped
            //   - the init time is present
            //   - the end time is not present and
            //   - the duration in minutes is specified
            // then the end time is calculated.
            if (model.isStopped() && model.getStart() != null && !model.getDuration().isZero())
            {
                DateTime end = entity.getStart().plusMinutes((int) model.getDuration().getTotalMinutes());
                entity.setEnd(
                        new name.pehl.karaka.server.activity.entity.Time(end.toDate(), settings.get().getTimeZone()));
            }
            else
            {
                // fall back to current time
                entity.setEnd(
                        new name.pehl.karaka.server.activity.entity.Time(new Date(), settings.get().getTimeZone()));
            }
        }
        entity.setPause(model.getPause().getTotalMinutes());
        entity.setBillable(model.isBillable());
        // entity.setStatus(model.getStatus()); Status can only be changed calling distinct service methods!

        // relations
        Key<name.pehl.karaka.server.project.entity.Project> projectKey = null;
        if (model.getProject() != null)
        {
            if (model.getProject().isTransient())
            {
                name.pehl.karaka.server.project.entity.Project newProjectEntity = projectConverter.fromModel(model
                        .getProject());
                projectKey = projectRepository.put(newProjectEntity);
            }
            else
            {
                projectKey = Key.<name.pehl.karaka.server.project.entity.Project>create(model.getProject().getId());
            }
        }
        entity.setProject(projectKey);
        List<Key<name.pehl.karaka.server.tag.entity.Tag>> tags = new ArrayList<Key<name.pehl.karaka.server.tag.entity.Tag>>();
        if (model.getTags() != null && !model.getTags().isEmpty())
        {
            for (name.pehl.karaka.shared.model.Tag tag : model.getTags())
            {
                if (tag.isTransient())
                {
                    name.pehl.karaka.server.tag.entity.Tag newTagEntity = tagConverter.fromModel(tag);
                    tags.add(tagRepository.put(newTagEntity));
                }
                else
                {
                    tags.add(Key.<name.pehl.karaka.server.tag.entity.Tag>create(tag.getId()));
                }
            }
        }
        entity.setTags(tags);
    }
}
