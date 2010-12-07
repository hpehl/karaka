package name.pehl.tire.client.activity.week;

import java.util.List;

import name.pehl.piriti.client.property.PropertySetter;
import name.pehl.tire.client.activity.day.Day;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class WeekDaysSetter implements PropertySetter<Week, List<Day>>
{
    @Override
    public void set(Week model, List<Day> value)
    {
        model.clearDays();
        if (value != null)
        {
            for (Day day : value)
            {
                model.addDay(day);
            }
        }
    }
}
