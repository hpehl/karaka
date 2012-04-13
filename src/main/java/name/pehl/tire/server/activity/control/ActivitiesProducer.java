package name.pehl.tire.server.activity.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import name.pehl.tire.server.activity.entity.Activity;
import name.pehl.tire.server.activity.entity.Time;
import name.pehl.tire.server.config.RandomString;
import name.pehl.tire.server.tag.entity.Tag;

import org.joda.time.MutableDateTime;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class ActivitiesProducer
{
    static final int ACTIVITIES_PER_MONTH = 23;
    static final int MAX_ACTIVITIES_PER_DAY = 2;
    static final int MAX_TAGS = 4;

    static long nextId = 0;

    @Inject
    Random random;

    @Inject
    RandomString randomString;


    public List<Activity> forMonth(int year, int month)
    {
        List<Activity> activities = new ArrayList<Activity>();
        MutableDateTime mdt = new MutableDateTime().year().set(year).monthOfYear().set(month).dayOfMonth().set(1);
        for (int i = 0; i < ACTIVITIES_PER_MONTH; i++)
        {
            mdt.hourOfDay().set(9);
            int activitiesCount = 1 + random.nextInt(MAX_ACTIVITIES_PER_DAY);
            int hours = 2 + random.nextInt(6) / activitiesCount;
            for (int j = 0; j < activitiesCount; j++)
            {
                Activity activity = newActivity(mdt, hours);
                activities.add(activity);
                mdt.hourOfDay().add(hours);
            }
            mdt.addDays(1);
        }
        return activities;
    }


    public List<Activity> forWeek(int year, int week)
    {
        List<Activity> activities = new ArrayList<Activity>();
        MutableDateTime mdt = new MutableDateTime().year().set(year).weekOfWeekyear().set(week).dayOfWeek().set(1);
        for (int i = 0; i < 7; i++)
        {
            mdt.hourOfDay().set(9);
            int activitiesCount = 1 + random.nextInt(MAX_ACTIVITIES_PER_DAY);
            int hours = 2 + random.nextInt(8) / activitiesCount;
            for (int j = 0; j < activitiesCount; j++)
            {
                Activity activity = newActivity(mdt, hours);
                activities.add(activity);
                mdt.hourOfDay().add(hours);
            }
            mdt.addDays(1);
        }
        return activities;
    }


    private Activity newActivity(MutableDateTime date, int hours)
    {
        Activity activity = new Activity(randomString.next(5), randomString.next(10));
        activity.setId(nextId++);
        activity.setStart(new Time(date.toDate()));
        int hour = date.hourOfDay().get() + hours;
        activity.setEnd(new Time(date.copy().hourOfDay().set(hour).toDate()));

        int tags = random.nextInt(MAX_TAGS);
        for (int j = 0; j < tags; j++)
        {
            Tag tag = new Tag(randomString.next(5));
            tag.setId(nextId++);
            activity.addTag(tag);
        }
        return activity;
    }
}
