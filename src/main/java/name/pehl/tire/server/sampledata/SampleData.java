package name.pehl.tire.server.sampledata;

import java.util.List;

import javax.inject.Inject;

import name.pehl.tire.server.activity.control.ActivityRepository;
import name.pehl.tire.server.activity.entity.Activity;
import name.pehl.tire.server.paging.entity.PageResult;
import name.pehl.tire.server.settings.control.SettingsRepository;
import name.pehl.tire.server.settings.entity.Settings;

import org.joda.time.DateMidnight;
import org.joda.time.Months;
import org.slf4j.Logger;

class SampleData
{
    @Inject Logger logger;
    @Inject ActivityRepository activityRepository;
    @Inject ActivitiesProducer activitiesProducer;
    @Inject @DefaultSettings Settings defaultSettings;
    @Inject SettingsRepository settingsRepository;


    void generateAndPersit()
    {
        cleanup();

        DateMidnight end = DateMidnight.now();
        DateMidnight start = end.minus(Months.months(2));
        List<Activity> activities = activitiesProducer.produceActivities(start, end);
        for (Activity activity : activities)
        {
            activityRepository.put(activity);
            logger.info("Persisted {}", activity);
        }
        settingsRepository.put(defaultSettings);
        logger.info("Persisted {}", defaultSettings);
    }


    private void cleanup()
    {
        PageResult<Activity> activities = activityRepository.list();
        if (!activities.isEmpty())
        {
            logger.info("Removed {} old activities", activities.size());
            activityRepository.deleteAll(activities);
        }
        PageResult<Settings> settings = settingsRepository.list();
        if (!settings.isEmpty())
        {
            logger.info("Removed old settings");
            settingsRepository.deleteAll(settings);
        }
    }
}
