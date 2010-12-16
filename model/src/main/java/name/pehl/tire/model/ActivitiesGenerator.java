package name.pehl.tire.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joda.time.MutableDateTime;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class ActivitiesGenerator
{
    private static long nextId = 0;
    private final Random random = new Random();


    public List<Activity> generateMonth(int year, int month)
    {
        List<Activity> activities = new ArrayList<Activity>();
        MutableDateTime mdt = new MutableDateTime().year().set(year).monthOfYear().set(month).dayOfMonth().set(1)
                .hourOfDay().set(9);
        for (int i = 0; i < 23; i++)
        {
            Activity activity = newActivity(mdt);
            activities.add(activity);
            mdt.addDays(1);
        }
        return activities;
    }


    public List<Activity> generateWeek(int year, int week)
    {
        List<Activity> activities = new ArrayList<Activity>();
        MutableDateTime mdt = new MutableDateTime().year().set(year).weekOfWeekyear().set(week).dayOfWeek().set(1)
                .hourOfDay().set(9);
        for (int i = 0; i < 7; i++)
        {
            Activity activity = newActivity(mdt);
            activities.add(activity);
            mdt.addDays(1);
        }
        return activities;
    }


    private Activity newActivity(MutableDateTime date)
    {
        Activity activity = new Activity(randomString(5), randomString(10));
        activity.setId(nextId++);
        activity.setStart(new Time(date.toDate()));
        int hour = date.hourOfDay().get() + 2 + random.nextInt(6);
        activity.setEnd(new Time(date.copy().hourOfDay().set(hour).toDate()));

        int tags = random.nextInt(3);
        for (int j = 0; j < tags; j++)
        {
            Tag tag = new Tag(randomString(5));
            tag.setId(nextId++);
            activity.addTag(tag);
        }
        return activity;
    }


    private String randomString(int amount)
    {
        int start = 'a';
        int end = 'z';
        int gap = end - start;

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < amount; i++)
        {
            char c = (char) (random.nextInt(gap) + start);
            builder.append(c);
        }
        return builder.toString();
    }
}
