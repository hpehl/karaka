package name.pehl.tire.client.activity.model;

import name.pehl.tire.client.resources.Enums;
import name.pehl.tire.client.ui.FormatUtils;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.TimeUnit;
import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;

public class ActivitiesFormater
{
    public String instant(Activities activities, Enums enums)
    {
        return instant(activities.getUnit(), activities.getYear(), activities.getMonth(), activities.getWeek(), enums);
    }


    public String instant(ActivitiesNavigator activitiesNavigator, Enums enums)
    {
        if (activitiesNavigator.getYear() == 0)
        {
            // relative
            StringBuilder instant = new StringBuilder();
            if (activitiesNavigator.getUnit() == MONTH)
            {
                instant.append("relative month (").append(activitiesNavigator.getMonth()).append(")");
            }
            else if (activitiesNavigator.getUnit() == WEEK)
            {
                instant.append("relative week (").append(activitiesNavigator.getWeek()).append(")");
            }
            return instant.toString();
        }
        return instant(activitiesNavigator.getUnit(), activitiesNavigator.getYear(), activitiesNavigator.getMonth(),
                activitiesNavigator.getWeek(), enums);
    }


    private String instant(TimeUnit unit, int year, int month, int week, Enums enums)
    {
        StringBuilder title = new StringBuilder();
        if (unit == WEEK)
        {
            title.append("CW ").append(week).append(" / ").append(year);
        }
        else if (unit == MONTH)
        {
            String monthKey = "month_" + month;
            title.append(enums.getString(monthKey)).append(" ").append(year);
        }
        return title.toString();
    }


    public String period(Activities activities)
    {
        StringBuilder duration = new StringBuilder();
        duration.append(FormatUtils.hours(activities.getMinutes())).append(" - ")
                .append(FormatUtils.date(activities.getStart())).append(" - ")
                .append(FormatUtils.date(activities.getEnd()));
        return duration.toString();
    }
}
