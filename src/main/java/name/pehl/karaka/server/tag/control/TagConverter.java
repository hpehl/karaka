package name.pehl.karaka.server.tag.control;

import name.pehl.karaka.server.converter.AbstractEntityConverter;
import name.pehl.karaka.server.converter.EntityConverter;

public class TagConverter extends
        AbstractEntityConverter<name.pehl.karaka.server.tag.entity.Tag, name.pehl.karaka.shared.model.Tag> implements
        EntityConverter<name.pehl.karaka.server.tag.entity.Tag, name.pehl.karaka.shared.model.Tag>
{
    @Override
    public name.pehl.karaka.shared.model.Tag toModel(name.pehl.karaka.server.tag.entity.Tag entity)
    {
        assertEntity(entity);
        name.pehl.karaka.shared.model.Tag model = new name.pehl.karaka.shared.model.Tag(websafeKey(
                name.pehl.karaka.server.tag.entity.Tag.class, entity), entity.getName());
        return model;
    }


    @Override
    public name.pehl.karaka.server.tag.entity.Tag fromModel(name.pehl.karaka.shared.model.Tag model)
    {
        assertModel(model);
        name.pehl.karaka.server.tag.entity.Tag entity = new name.pehl.karaka.server.tag.entity.Tag(model.getName());
        return entity;
    }


    @Override
    public void merge(name.pehl.karaka.shared.model.Tag model, name.pehl.karaka.server.tag.entity.Tag entity)
    {
        assertNonTransientModel(model);
        entity.setName(model.getName());
    }
}
