package name.pehl.tire.server.settings.control;

import name.pehl.tire.server.converter.AbstractEntityConverter;
import name.pehl.tire.server.converter.EntityConverter;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class SettingsConverter extends
        AbstractEntityConverter<name.pehl.tire.server.settings.entity.Settings, name.pehl.tire.shared.model.Settings>
        implements
        EntityConverter<name.pehl.tire.server.settings.entity.Settings, name.pehl.tire.shared.model.Settings>
{
    @Override
    public name.pehl.tire.shared.model.Settings toModel(name.pehl.tire.server.settings.entity.Settings entity)
    {
        assertEntity(entity);

        // basic properties
        name.pehl.tire.shared.model.Settings model = new name.pehl.tire.shared.model.Settings(String.valueOf(entity
                .getId()));
        model.setHoursPerMonth(entity.getHoursPerMonth());
        model.setTimeZoneId(entity.getTimeZone().getID());
        name.pehl.tire.server.settings.entity.User entityUser = entity.getUser();
        if (entityUser != null)
        {
            name.pehl.tire.shared.model.User modelUser = new name.pehl.tire.shared.model.User(entityUser.getUsername(),
                    entityUser.getUsername(), entityUser.getEmail());
            modelUser.setFirstname(entityUser.getFirstname());
            modelUser.setSurname(entityUser.getSurname());
            model.setUser(modelUser);
        }
        return model;
    }


    @Override
    public name.pehl.tire.server.settings.entity.Settings fromModel(name.pehl.tire.shared.model.Settings model)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
