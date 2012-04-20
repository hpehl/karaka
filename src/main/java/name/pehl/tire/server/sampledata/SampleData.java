package name.pehl.tire.server.sampledata;

import java.util.List;

import javax.inject.Inject;

import name.pehl.tire.server.activity.control.ActivityRepository;
import name.pehl.tire.server.activity.entity.Activity;

import org.joda.time.DateMidnight;
import org.joda.time.Months;
import org.slf4j.Logger;

class SampleData
{
    @Inject
    Logger logger;

    @Inject
    ActivityRepository activityRepository;

    @Inject
    ActivitiesProducer activitiesProducer;


    void generateAndPersit()
    {
        DateMidnight end = DateMidnight.now();
        DateMidnight start = end.minus(Months.months(2));
        List<Activity> activities = activitiesProducer.produceActivities(start, end);
        for (Activity activity : activities)
        {
            activityRepository.put(activity);
            logger.info("Persisted {}", activity);
        }
    }
}
