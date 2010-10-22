package name.pehl.tire.client.activity;

import java.util.List;

import name.pehl.piriti.client.property.PropertySetter;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class DayActivitiesSetter implements PropertySetter<Day, List<Activity>>
{
    @Override
    public void set(Day model, List<Activity> value)
    {
        model.clearActivities();
        if (value != null)
        {
            for (Activity activity : value)
            {
                model.addActivity(activity);
            }
        }
    }
}
