package name.pehl.tire.rest.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import name.pehl.tire.model.Activity;
import name.pehl.tire.model.Time;

import org.joda.time.MutableDateTime;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class ActivitiesGenerator
{
    private Random random = new Random();


    public List<Activity> generate(int year, int week)
    {
        List<Activity> activities = new ArrayList<Activity>();
        MutableDateTime mdt = new MutableDateTime().year().set(year).weekOfWeekyear().set(week).dayOfWeek().set(1)
                .hourOfDay().set(9);
        for (int i = 0; i < 5; i++)
        {
            mdt.addDays(1);
            Activity activity = new Activity(randomString(5), randomString(10));
            activity.setStart(new Time(mdt.toDate()));
            int hour = mdt.hourOfDay().get() + 2 + random.nextInt(6);
            activity.setEnd(new Time(mdt.copy().hourOfDay().set(hour).toDate()));
            activities.add(activity);
        }
        return activities;
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
