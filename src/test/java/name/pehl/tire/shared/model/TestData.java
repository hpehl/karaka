package name.pehl.tire.shared.model;

import java.util.UUID;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;

import static name.pehl.tire.shared.model.TimeUnit.DAY;
import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;

class TestData
{
    // ------------------------------------------------------------- activities

    Activities month()
    {
        return newActivities(MONTH);
    }


    Activities month(int numberOfActivities)
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


    Activities week()
    {
        return newActivities(WEEK);
    }


    Activities week(int numberOfActivities)
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


    Activities day()
    {
        return newActivities(DAY);
    }


    Activities day(int numberOfActivities)
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


    Activities newActivities(TimeUnit unit)
    {
        DateMidnight now = new DateMidnight();
        return new Activities(now.getYear(), now.getMonthOfYear(), now.getWeekOfWeekyear(), now.getDayOfMonth(), unit);
    }


    // --------------------------------------------------------------- activity

    Activity newActivity()
    {
        DateTime now = new DateTime();
        return newActivity(now, null);
    }


    Activity newActivity(DateTime start, DateTime end)
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

    Time newTime(DateTime dateTime)
    {
        return new Time(dateTime.toDate(), dateTime.year().get(), dateTime.monthOfYear().get(), dateTime
                .weekOfWeekyear().get(), dateTime.dayOfMonth().get());
    }
}
