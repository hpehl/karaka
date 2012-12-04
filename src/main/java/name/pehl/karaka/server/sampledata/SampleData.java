package name.pehl.karaka.server.sampledata;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import name.pehl.karaka.server.activity.control.ActivityRepository;
import name.pehl.karaka.server.activity.entity.Activity;
import name.pehl.karaka.server.client.control.ClientRepository;
import name.pehl.karaka.server.client.entity.Client;
import name.pehl.karaka.server.project.control.ProjectRepository;
import name.pehl.karaka.server.project.entity.Project;
import name.pehl.karaka.server.settings.control.DefaultSettings;
import name.pehl.karaka.server.settings.control.SettingsRepository;
import name.pehl.karaka.server.settings.entity.Settings;
import name.pehl.karaka.server.tag.control.TagRepository;
import name.pehl.karaka.server.tag.entity.Tag;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        if (settingsRepository.list().isEmpty())
        {
            settingsRepository.save(defaultSettings);
            logger.info("Persisted {}", defaultSettings);
        }

        // Clients
        List<Client> savedClients = new ArrayList<Client>();
        for (Client client : clients)
        {
            Client savedClient = clientRepository.save(client);
            savedClients.add(savedClient);
            logger.info("Persisted {}", client);
        }

        // Projects
        List<Project> savedProjects = new ArrayList<Project>();
        for (Project project : projects)
        {
            Client savedClient = savedClients.get(random.nextInt(clients.size()));
            project.setClient(Ref.create(savedClient));
            Project savedProject = projectRepository.save(project);
            savedProjects.add(savedProject);
            logger.info("Persisted {}", project);
        }

        // Tags
        List<Tag> savedTags = new ArrayList<Tag>();
        for (Tag tag : tags)
        {
            Tag savedTag = tagRepository.save(tag);
            savedTags.add(savedTag);
            logger.info("Persisted {}", tag);
        }

        // Activities
        List<Activity> activities = activitiesProducer.produceActivities(savedProjects, savedTags,
                defaultSettings.getTimeZone());
        for (Activity activity : activities)
        {
            activityRepository.save(activity);
            logger.info("Persisted {}", activity);
        }
    }


    void cleanup()
    {
        List<Activity> activities = activityRepository.list();
        if (!activities.isEmpty())
        {
            activityRepository.deleteAll(activities);
            logger.info("Removed {} activities", activities.size());
        }

        // Tags
        List<Tag> tags = tagRepository.list();
        if (!tags.isEmpty())
        {
            tagRepository.deleteAll(tags);
            logger.info("Removed {} tags", tags.size());
        }

        // Projects
        List<Project> projects = projectRepository.list();
        if (!projects.isEmpty())
        {
            projectRepository.deleteAll(projects);
            logger.info("Removed {} projects", projects.size());
        }

        // Clients
        List<Client> clients = clientRepository.list();
        if (!clients.isEmpty())
        {
            clientRepository.deleteAll(clients);
            logger.info("Removed {} clients", clients.size());
        }

        // Settings
        List<Settings> settings = settingsRepository.list();
        if (!settings.isEmpty())
        {
            settingsRepository.deleteAll(settings);
            logger.info("Removed {} settings", settings.size());
        }
    }
}
