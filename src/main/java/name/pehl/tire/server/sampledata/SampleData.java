package name.pehl.tire.server.sampledata;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import name.pehl.tire.server.activity.control.ActivityRepository;
import name.pehl.tire.server.activity.entity.Activity;
import name.pehl.tire.server.client.control.ClientRepository;
import name.pehl.tire.server.client.entity.Client;
import name.pehl.tire.server.paging.entity.PageResult;
import name.pehl.tire.server.project.control.ProjectRepository;
import name.pehl.tire.server.project.entity.Project;
import name.pehl.tire.server.settings.control.SettingsRepository;
import name.pehl.tire.server.settings.entity.Settings;
import name.pehl.tire.server.tag.control.TagRepository;
import name.pehl.tire.server.tag.entity.Tag;

import org.slf4j.Logger;

import com.googlecode.objectify.Key;

class SampleData
{
    @Inject Logger logger;
    @Inject Random random;

    @Inject @DefaultSettings Settings defaultSettings;
    @Inject SettingsRepository settingsRepository;

    @Inject List<Client> clients;
    @Inject ClientRepository clientRepository;

    @Inject List<Project> projects;
    @Inject ProjectRepository projectRepository;

    @Inject List<Tag> tags;
    @Inject TagRepository tagRepository;

    @Inject ActivityRepository activityRepository;
    @Inject ActivitiesProducer activitiesProducer;


    void persit()
    {
        // Settings
        settingsRepository.put(defaultSettings);
        logger.info("Persisted {}", defaultSettings);

        // Clients
        List<Key<Client>> clientKeys = new ArrayList<Key<Client>>();
        for (Client client : clients)
        {
            Key<Client> key = clientRepository.put(client);
            clientKeys.add(key);
            logger.info("Persisted {}", client);
        }

        // Projects
        List<Key<Project>> projectKeys = new ArrayList<Key<Project>>();
        for (Project project : projects)
        {
            Key<Client> client = clientKeys.get(random.nextInt(clients.size()));
            project.setClient(client);
            Key<Project> key = projectRepository.put(project);
            projectKeys.add(key);
            logger.info("Persisted {}", project);
        }

        // Tags
        List<Key<Tag>> tagKeys = new ArrayList<Key<Tag>>();
        for (Tag tag : tags)
        {
            Key<Tag> key = tagRepository.put(tag);
            tagKeys.add(key);
            logger.info("Persisted {}", tag);
        }

        // Activities
        List<Activity> activities = activitiesProducer.produceActivities(projectKeys, tagKeys,
                defaultSettings.getTimeZone());
        for (Activity activity : activities)
        {
            activityRepository.put(activity);
            logger.info("Persisted {}", activity);
        }
    }


    void cleanup()
    {
        PageResult<Activity> activities = activityRepository.list();
        if (!activities.isEmpty())
        {
            activityRepository.deleteAll(activities);
            logger.info("Removed {} activities", activities.size());
        }

        // Tags
        PageResult<Tag> tags = tagRepository.list();
        if (!tags.isEmpty())
        {
            tagRepository.deleteAll(tags);
            logger.info("Removed {} tags", tags.size());
        }

        // Projects
        PageResult<Project> projects = projectRepository.list();
        if (!projects.isEmpty())
        {
            projectRepository.deleteAll(projects);
            logger.info("Removed {} projects", projects.size());
        }

        // Clients
        PageResult<Client> clients = clientRepository.list();
        if (!clients.isEmpty())
        {
            clientRepository.deleteAll(clients);
            logger.info("Removed {} clients", clients.size());
        }

        // Settings
        PageResult<Settings> settings = settingsRepository.list();
        if (!settings.isEmpty())
        {
            settingsRepository.deleteAll(settings);
            logger.info("Removed settings");
        }
    }
}
