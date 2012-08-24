package name.pehl.tire;

import static name.pehl.tire.shared.model.TimeUnit.DAY;
import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;

import java.util.UUID;

import name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction;
import name.pehl.tire.client.activity.event.ActivityChangedEvent;
import name.pehl.tire.client.activity.event.TickEvent;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Activity;
import name.pehl.tire.shared.model.Time;
import name.pehl.tire.shared.model.TimeUnit;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;

public class TestData
{
    // ------------------------------------------------------------- activities

    public Activities month()
    {
        return newActivities(MONTH);
    }


    public Activities month(int numberOfActivities)
    {
        Activities activities = newActivities(MONTH);
        DateTime start = new MutableDateTime().dayOfMonth().set(1).hourOfDay().set(8).toDateTime();
        for (int i = 0; i < numberOfActivities; i++)
        {
            DateTime end = start.plusHours(1);
            activities.add(newActivity(start, end));
            start = start.plusDays(1);
        }
        return activities;
    }


    public Activities week()
    {
        return newActivities(WEEK);
    }


    public Activities week(int numberOfActivities)
    {
        Activities activities = newActivities(WEEK);
        DateTime start = new MutableDateTime().dayOfWeek().set(1).hourOfDay().set(8).toDateTime();
        for (int i = 0; i < numberOfActivities; i++)
        {
            DateTime end = start.plusHours(1);
            activities.add(newActivity(start, end));
            start = start.plusDays(1);
        }
        return activities;
    }


    public Activities day()
    {
        return newActivities(DAY);
    }


    public Activities day(int numberOfActivities)
    {
        Activities activities = newActivities(DAY);
        DateTime start = new MutableDateTime().dayOfMonth().set(1).hourOfDay().set(8).toDateTime();
        for (int i = 0; i < numberOfActivities; i++)
        {
            DateTime end = start.plusHours(1);
            activities.add(newActivity(start, end));
            start = end.plusHours(1);
        }
        return activities;
    }


    public Activities newActivities(TimeUnit unit)
    {
        DateMidnight now = new DateMidnight();
        return new Activities(now.getYear(), now.getMonthOfYear(), now.getWeekOfWeekyear(), now.getDayOfMonth(), unit);
    }


    // --------------------------------------------------------------- activity

    public Activity newActivity()
    {
        DateTime now = new DateTime();
        return newActivity(now, null);
    }


    public Activity newActivity(DateTime start, DateTime end)
    {
        Activity activity = new Activity(UUID.randomUUID().toString(), "Test activity");
        if (start != null)
        {
            activity.setStart(newTime(start));
        }
        if (end != null)
        {
            activity.setEnd(newTime(end));
        }
        return activity;
    }


    // ------------------------------------------------------------------- time

    public Time newTime(DateTime dateTime)
    {
        return new Time(dateTime.toDate(), dateTime.year().get(), dateTime.monthOfYear().get(), dateTime
                .weekOfWeekyear().get(), dateTime.dayOfMonth().get());
    }


    // ----------------------------------------------------------------- events

    public ActivityChangedEvent newActivityChangedEvent(ChangeAction action)
    {
        return newActivityChangedEvent(action, newActivity());
    }


    public ActivityChangedEvent newActivityChangedEvent(ChangeAction action, Activity activity)
    {
        return new ActivityChangedEvent(action, activity, null);
    }


    public TickEvent newTickEvent()
    {
        return new TickEvent(newActivity());
    }
}
