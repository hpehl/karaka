package name.pehl.tire.server.tag.control;

import name.pehl.tire.server.converter.AbstractEntityConverter;
import name.pehl.tire.server.converter.EntityConverter;

public class TagConverter extends
        AbstractEntityConverter<name.pehl.tire.server.tag.entity.Tag, name.pehl.tire.shared.model.Tag> implements
        EntityConverter<name.pehl.tire.server.tag.entity.Tag, name.pehl.tire.shared.model.Tag>
{
    @Override
    public name.pehl.tire.shared.model.Tag toModel(name.pehl.tire.server.tag.entity.Tag entity)
    {
        assertEntity(entity);
        name.pehl.tire.shared.model.Tag model = new name.pehl.tire.shared.model.Tag(websafeKey(
                name.pehl.tire.server.tag.entity.Tag.class, entity), entity.getName());
        return model;
    }


    @Override
    public name.pehl.tire.server.tag.entity.Tag fromModel(name.pehl.tire.shared.model.Tag model)
    {
        assertModel(model);
        name.pehl.tire.server.tag.entity.Tag entity = new name.pehl.tire.server.tag.entity.Tag(model.getName());
        return entity;
    }


    @Override
    public void merge(name.pehl.tire.shared.model.Tag model, name.pehl.tire.server.tag.entity.Tag entity)
    {
        assertNonTransientModel(model);
        entity.setName(model.getName());
    }
}
