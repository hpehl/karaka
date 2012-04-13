package name.pehl.tire.server.activity.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.joda.time.MutableDateTime;

import biz.accelsis.taima.business.store.control.Count;
import biz.accelsis.taima.business.store.control.Month;
import biz.accelsis.taima.business.store.control.RandomString;
import biz.accelsis.taima.business.store.control.Week;
import biz.accelsis.taima.business.store.entity.Activity;
import biz.accelsis.taima.business.store.entity.Project;
import biz.accelsis.taima.business.store.entity.Tag;

public class ActivityProducer
{
    private static final int ACTIVITIES_PER_MONTH = 23;
    private static final int MAX_ACTIVITIES_PER_DAY = 2;
    private static final int COUNT = 4;

    @Inject
    Random random;

    @Inject
    RandomString randomString;

    @Inject
    @Count(COUNT)
    List<Project> projects;

    @Inject
    @Count(COUNT)
    List<Tag> tags;


    @Produces
    @Month(year = 2012, month = 2)
    public List<Activity> generateMonth(InjectionPoint ip)
    {
        List<Activity> activities = new ArrayList<Activity>();
        Month month = ip.getAnnotated().getAnnotation(Month.class);
        if (month != null && month.year() > 1900 && month.year() < 3000 && month.month() > 0 && month.month() < 13)
        {
            MutableDateTime mdt = new MutableDateTime().year().set(month.year()).monthOfYear().set(month.month())
                    .dayOfMonth().set(1);
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
        }
        return activities;
    }


    @Produces
    @Week
    public List<Activity> generateWeek(InjectionPoint ip)
    {
        List<Activity> activities = new ArrayList<Activity>();
        Week week = ip.getAnnotated().getAnnotation(Week.class);
        if (week != null && week.year() > 1900 && week.year() < 3000 && week.week() > 0 && week.week() < 53)
        {
            MutableDateTime mdt = new MutableDateTime().year().set(week.year()).weekOfWeekyear().set(week.week())
                    .dayOfWeek().set(1);
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
        }
        return activities;
    }


    private Activity newActivity(MutableDateTime date, int hours)
    {
        Activity activity = new Activity("Activity " + randomString.next(5), "Activity description "
                + randomString.next(10));
        activity.setStart(date.toDate());
        int hour = date.hourOfDay().get() + hours;
        activity.setEnd(date.copy().hourOfDay().set(hour).toDate());

        int projectIndex = random.nextInt(COUNT);
        activity.setProject(projects.get(projectIndex));

        int tagCount = random.nextInt(COUNT);
        for (int j = 0; j < tagCount; j++)
        {
            int tagIndex = random.nextInt(COUNT);
            Tag tag = tags.get(tagIndex);
            activity.addTag(tag);
        }
        return activity;
    }
}
