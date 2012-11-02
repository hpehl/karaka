package name.pehl.karaka.server.client.control;

import name.pehl.karaka.server.converter.AbstractEntityConverter;
import name.pehl.karaka.server.converter.EntityConverter;

public class ClientConverter extends
        AbstractEntityConverter<name.pehl.karaka.server.client.entity.Client, name.pehl.karaka.shared.model.Client>
        implements EntityConverter<name.pehl.karaka.server.client.entity.Client, name.pehl.karaka.shared.model.Client>
{
    @Override
    public name.pehl.karaka.shared.model.Client toModel(name.pehl.karaka.server.client.entity.Client entity)
    {
        assertEntity(entity);
        name.pehl.karaka.shared.model.Client model = new name.pehl.karaka.shared.model.Client(websafeKey(
                name.pehl.karaka.server.client.entity.Client.class, entity), entity.getName());
        return model;
    }


    @Override
    public name.pehl.karaka.server.client.entity.Client fromModel(name.pehl.karaka.shared.model.Client model)
    {
        assertModel(model);
        name.pehl.karaka.server.client.entity.Client entity = new name.pehl.karaka.server.client.entity.Client(
                model.getName(), model.getDescription());
        return entity;
    }


    @Override
    public void merge(name.pehl.karaka.shared.model.Client model, name.pehl.karaka.server.client.entity.Client entity)
    {
        assertNonTransientModel(model);
        entity.setName(model.getName());
        entity.setDescription(model.getDescription());
    }
}
