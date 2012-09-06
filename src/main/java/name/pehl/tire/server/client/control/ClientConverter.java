package name.pehl.tire.server.client.control;

import name.pehl.tire.server.converter.AbstractEntityConverter;
import name.pehl.tire.server.converter.EntityConverter;

public class ClientConverter extends
        AbstractEntityConverter<name.pehl.tire.server.client.entity.Client, name.pehl.tire.shared.model.Client>
        implements EntityConverter<name.pehl.tire.server.client.entity.Client, name.pehl.tire.shared.model.Client>
{
    @Override
    public name.pehl.tire.shared.model.Client toModel(name.pehl.tire.server.client.entity.Client entity)
    {
        assertEntity(entity);
        name.pehl.tire.shared.model.Client model = new name.pehl.tire.shared.model.Client(websafeKey(
                name.pehl.tire.server.client.entity.Client.class, entity), entity.getName());
        return model;
    }


    @Override
    public name.pehl.tire.server.client.entity.Client fromModel(name.pehl.tire.shared.model.Client model)
    {
        assertModel(model);
        name.pehl.tire.server.client.entity.Client entity = new name.pehl.tire.server.client.entity.Client(
                model.getName(), model.getDescription());
        return entity;
    }


    @Override
    public void merge(name.pehl.tire.shared.model.Client model, name.pehl.tire.server.client.entity.Client entity)
    {
        assertNonTransientModel(model);
        entity.setName(model.getName());
        entity.setDescription(model.getDescription());
    }
}
