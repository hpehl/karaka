package name.pehl.tire.server.sampledata;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import name.pehl.tire.server.activity.entity.Activity;
import name.pehl.tire.server.activity.entity.Time;
import name.pehl.tire.server.project.entity.Project;
import name.pehl.tire.server.tag.entity.Tag;

import org.joda.time.DateMidnight;
import org.joda.time.DateTimeZone;
import org.joda.time.Months;
import org.joda.time.MutableDateTime;
import org.slf4j.Logger;

import com.googlecode.objectify.Key;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
class ActivitiesProducer
{
    static final int ACTIVITIES_PER_DAY = 2;
    static final int TAGS_PER_ACTIVITY = 3;

    @Inject Logger logger;
    @Inject Random random;
    @Inject LoremIpsum loremIpsum;


    public List<Activity> produceActivities(List<Key<Project>> projectKeys, List<Key<Tag>> tagKeys,
            DateTimeZone timeZone)
    {
        DateMidnight end = DateMidnight.now().plusDays(1);
        DateMidnight start = end.minus(Months.months(2));
        List<Activity> activities = new ArrayList<Activity>();
        MutableDateTime mdt = new MutableDateTime(start);
        while (mdt.isBefore(end))
        {
            mdt.hourOfDay().set(9);
            int activitiesCount = 1 + random.nextInt(ACTIVITIES_PER_DAY);
            int hours = 2 + random.nextInt(6) / activitiesCount;
            for (int j = 0; j < activitiesCount; j++)
            {
                Activity activity = new Activity(loremIpsum.randomWords(2), loremIpsum.randomWords(4), timeZone);
                activity.setStart(new Time(mdt.toDate(), timeZone));
                int hour = mdt.hourOfDay().get() + hours;
                activity.setEnd(new Time(mdt.copy().hourOfDay().set(hour).toDate(), timeZone));
                activity.setProject(projectKeys.get(random.nextInt(projectKeys.size())));
                for (int i = 0; i < TAGS_PER_ACTIVITY; i++)
                {
                    activity.addTag(tagKeys.get(random.nextInt(tagKeys.size())));
                }
                activities.add(activity);
                mdt.hourOfDay().add(hours);
            }
            mdt.addDays(1);
        }
        return activities;
    }
}
