package name.pehl.tire.server.converter;

import name.pehl.tire.server.entity.BaseEntity;
import name.pehl.tire.shared.model.BaseModel;

public interface EntityConverter<E extends BaseEntity, M extends BaseModel>
{
    M toModel(E entity);


    E fromModel(M model);
}
