package name.pehl.karaka.server.converter;

import name.pehl.karaka.server.entity.BaseEntity;
import name.pehl.karaka.shared.model.BaseModel;

public interface EntityConverter<E extends BaseEntity, M extends BaseModel>
{
    M toModel(E entity);


    E fromModel(M model);


    void merge(M model, E entity);
}
