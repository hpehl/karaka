package name.pehl.tire.server.project.control;

public class ProjectConverter
{
    public name.pehl.tire.shared.model.Project toModel(name.pehl.tire.server.project.entity.Project entity)
    {
        if (entity == null)
        {
            throw new IllegalStateException("Server side project is null");
        }
        if (entity.isTransient())
        {
            throw new IllegalStateException("Server side project is transient");
        }

        name.pehl.tire.shared.model.Project model = new name.pehl.tire.shared.model.Project(String.valueOf(entity
                .getId()), entity.getName());
        model.setDescription(entity.getDescription());
        return model;
    }
}
