package name.pehl.tire.server.converter;

import name.pehl.tire.server.entity.BaseEntity;
import name.pehl.tire.shared.model.BaseModel;

import com.googlecode.objectify.Key;

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


    protected void assertModel(M model)
    {
        if (model == null)
        {
            throw new IllegalStateException("Model is null");
        }
    }


    protected void assertNonTransientModel(M model)
    {
        assertModel(model);
        if (model.isTransient())
        {
            throw new IllegalStateException(String.format("Model %s is transient", model));
        }
    }


    protected String websafeKey(Class<E> entityClass, E entity)
    {
        Key<E> key = Key.create(entityClass, entity.getId());
        return key.getString();
    }
}
