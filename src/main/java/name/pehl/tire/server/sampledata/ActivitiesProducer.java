package name.pehl.tire.server.sampledata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

import name.pehl.tire.server.activity.entity.Activity;
import name.pehl.tire.server.activity.entity.Time;
import name.pehl.tire.server.client.control.ClientRepository;
import name.pehl.tire.server.client.entity.Client;
import name.pehl.tire.server.project.control.ProjectRepository;
import name.pehl.tire.server.project.entity.Project;
import name.pehl.tire.server.settings.entity.Settings;
import name.pehl.tire.server.tag.control.TagRepository;
import name.pehl.tire.server.tag.entity.Tag;

import org.joda.time.DateMidnight;
import org.joda.time.DateTimeZone;
import org.joda.time.MutableDateTime;

import com.googlecode.objectify.Key;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
class ActivitiesProducer
{
    static final int ACTIVITIES_PER_DAY = 2;

    static final int CLIENTS = 3;
    static final int PROJECTS = 5;
    static final int TAGS = 7;
    static final int TAGS_PER_ACTIVITY = 3;

    //@formatter:off
    @Inject Settings settings;
    
    @Inject @Count(CLIENTS) List<Client> clients;
    @Inject @Count(PROJECTS) List<Project> projects;
    @Inject @Count(TAGS) List<Tag> tags;

    @Inject Random random;
    @Inject RandomString randomString;

    @Inject ClientRepository clientRepository;
    @Inject ProjectRepository projectRepository;
    @Inject TagRepository tagRepository;
    //@formatter:on

    public List<Activity> produceActivities(DateMidnight start, DateMidnight end)
    {
        // Clients
        Map<Key<Client>, Client> persistentClients = clientRepository.putAll(clients);
        List<Key<Client>> clientKeys = new ArrayList<Key<Client>>(persistentClients.keySet());

        // Projects
        for (Project project : projects)
        {
            Key<Client> client = clientKeys.get(random.nextInt(CLIENTS));
            project.setClient(client);
        }
        Map<Key<Project>, Project> persistentProjects = projectRepository.putAll(projects);
        List<Key<Project>> projectKeys = new ArrayList<Key<Project>>(persistentProjects.keySet());

        // Tags
        Map<Key<Tag>, Tag> persistentTags = tagRepository.putAll(tags);
        List<Key<Tag>> tagKeys = new ArrayList<Key<Tag>>(persistentTags.keySet());

        // Activities
        DateTimeZone timeZone = settings.getTimeZone();
        List<Activity> activities = new ArrayList<Activity>();
        MutableDateTime mdt = new MutableDateTime(start);
        while (mdt.isBefore(end))
        {
            mdt.hourOfDay().set(9);
            int activitiesCount = 1 + random.nextInt(ACTIVITIES_PER_DAY);
            int hours = 2 + random.nextInt(6) / activitiesCount;
            for (int j = 0; j < activitiesCount; j++)
            {
                Activity activity = new Activity(randomString.next(5), randomString.next(10), timeZone);
                activity.setStart(new Time(mdt.toDate(), timeZone));
                int hour = mdt.hourOfDay().get() + hours;
                activity.setEnd(new Time(mdt.copy().hourOfDay().set(hour).toDate(), timeZone));
                activity.setProject(projectKeys.get(random.nextInt(PROJECTS)));
                for (int i = 0; i < TAGS_PER_ACTIVITY; i++)
                {
                    activity.addTag(tagKeys.get(random.nextInt(TAGS)));
                }
                activities.add(activity);
                mdt.hourOfDay().add(hours);
            }
            mdt.addDays(1);
        }
        return activities;
    }
}
