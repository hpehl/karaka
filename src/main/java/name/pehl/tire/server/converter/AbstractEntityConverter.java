package name.pehl.tire.server.converter;

import name.pehl.tire.server.entity.BaseEntity;
import name.pehl.tire.shared.model.BaseModel;

public abstract class AbstractEntityConverter<E extends BaseEntity, M extends BaseModel> implements
        EntityConverter<E, M>
{
    protected void assertEntity(E entity)
    {
        if (entity == null)
        {
            throw new IllegalStateException("Entity is null");
        }
        if (entity.isTransient())
        {
            throw new IllegalStateException(String.format("Entity %s is transient", entity));
        }
    }
}
