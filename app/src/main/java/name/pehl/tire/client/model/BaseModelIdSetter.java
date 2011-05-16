package name.pehl.tire.client.model;

import name.pehl.piriti.property.client.PropertySetter;

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
