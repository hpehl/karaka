package name.pehl.tire.server.project.control;

import name.pehl.tire.server.converter.AbstractEntityConverter;
import name.pehl.tire.server.converter.EntityConverter;

public class ProjectConverter extends
        AbstractEntityConverter<name.pehl.tire.server.project.entity.Project, name.pehl.tire.shared.model.Project>
        implements EntityConverter<name.pehl.tire.server.project.entity.Project, name.pehl.tire.shared.model.Project>
{
    @Override
    public name.pehl.tire.shared.model.Project toModel(name.pehl.tire.server.project.entity.Project entity)
    {
        assertEntity(entity);
        name.pehl.tire.shared.model.Project model = new name.pehl.tire.shared.model.Project(String.valueOf(entity
                .getId()), entity.getName());
        model.setDescription(entity.getDescription());
        return model;
    }


    @Override
    public name.pehl.tire.server.project.entity.Project fromModel(name.pehl.tire.shared.model.Project model)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
