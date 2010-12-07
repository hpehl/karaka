package name.pehl.tire.client.activity;

import java.util.List;

import name.pehl.piriti.client.property.PropertySetter;
import name.pehl.tire.client.activity.day.Day;

/**
 * @author $LastChangedBy$
 * @version $LastChangedRevision$
 */
public class DaysSetter implements PropertySetter<Activities, List<Day>>
{
    @Override
    public void set(Activities model, List<Day> value)
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
