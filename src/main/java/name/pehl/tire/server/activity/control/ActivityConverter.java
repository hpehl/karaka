package name.pehl.tire.server.activity.control;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ActivityConverter
{
    public name.pehl.tire.shared.model.Activity toModel(name.pehl.tire.server.activity.entity.Activity entity)
    {
        if (entity == null)
        {
            throw new IllegalStateException("Server side activity is null");
        }
        if (entity.isTransient())
        {
            throw new IllegalStateException("Server side activity is transient");
        }
        if (entity.getStart() == null)
        {
            throw new IllegalStateException("Server side activity has no start date");
        }
        name.pehl.tire.shared.model.Activity model = new name.pehl.tire.shared.model.Activity(String.valueOf(entity
                .getId()), entity.getName(), entity.getStart().getDate());
        model.setEnd(entity.getEnd().getDate());
        model.setPause(entity.getPause());
        model.setMinutes(entity.getMinutes());
        model.setBillable(entity.isBillable());
        model.setStatus(entity.getStatus());
        // TODO Project und Tags
        return model;
    }
}
