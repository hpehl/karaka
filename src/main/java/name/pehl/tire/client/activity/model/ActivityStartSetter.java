package name.pehl.tire.client.activity.model;

import name.pehl.piriti.property.client.PropertySetter;
import name.pehl.tire.shared.model.Activity;
import name.pehl.tire.shared.model.Time;

/**
 * Ensures the DbC (design by contract) for {@link Activity}.
 * 
 * @author $Author$
 * @version $Revision$
 */
public class ActivityStartSetter implements PropertySetter<Activity, Time>
{
    @Override
    public void set(Activity model, Time value)
    {
        if (value == null)
        {
            model.setStart(new Time());
        }
        else
        {
            model.setStart(value);
        }
    }
}
