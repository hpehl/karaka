package name.pehl.tire.rest.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import name.pehl.tire.model.Activity;
import name.pehl.tire.model.Time;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class ActivityGenerator
{
    private Random random = new Random();


    public Activity generate(Date date)
    {
        Activity activity = new Activity(random(5), random(10));
        activity.setStart(new Time(date));
        activity.setEnd(new Time(new Date(date.getTime() + random.nextInt())));
        return activity;
    }


    public List<Activity> generate(Date date, int amount)
    {
        List<Activity> activities = new ArrayList<Activity>();
        for (int i = 0; i < amount; i++)
        {
            activities.add(generate(date));
        }
        return activities;
    }


    private String random(int amount)
    {
        int start = 'A';
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
