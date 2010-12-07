package name.pehl.tire.client.activity.month;

import java.util.List;

import name.pehl.piriti.client.property.PropertySetter;
import name.pehl.tire.client.activity.day.Day;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class MonthDaysSetter implements PropertySetter<Month, List<Day>>
{
    @Override
    public void set(Month model, List<Day> value)
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
