package name.pehl.tire.client.model;

import name.pehl.piriti.client.property.PropertySetter;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class BaseModelIdSetter implements PropertySetter<BaseModel, Long>
{
    @Override
    public void set(BaseModel model, Long value)
    {
        model.setId(value);
    }
}
