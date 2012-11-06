package name.pehl.karaka.server.settings.control;

import name.pehl.karaka.server.converter.AbstractEntityConverter;
import name.pehl.karaka.server.converter.EntityConverter;
import name.pehl.karaka.shared.model.Settings;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class SettingsConverter extends
        AbstractEntityConverter<name.pehl.karaka.server.settings.entity.Settings, name.pehl.karaka.shared.model.Settings>
        implements
        EntityConverter<name.pehl.karaka.server.settings.entity.Settings, name.pehl.karaka.shared.model.Settings>
{
    @Override
    public name.pehl.karaka.shared.model.Settings toModel(name.pehl.karaka.server.settings.entity.Settings entity)
    {
        assertEntity(entity);

        // basic properties
        name.pehl.karaka.shared.model.Settings model = new name.pehl.karaka.shared.model.Settings(websafeKey(
                name.pehl.karaka.server.settings.entity.Settings.class, entity));
        model.setFormatHoursAsFloatingPointNumber(entity.isFormatHoursAsFloatingPointNumber());
        model.setTimeZoneId(entity.getTimeZone().getID());
        name.pehl.karaka.server.settings.entity.User entityUser = entity.getUser();
        if (entityUser != null)
        {
            name.pehl.karaka.shared.model.User modelUser = new name.pehl.karaka.shared.model.User(entityUser.getUsername(),
                    entityUser.getUsername(), entityUser.getEmail());
            modelUser.setFirstname(entityUser.getFirstname());
            modelUser.setSurname(entityUser.getSurname());
            model.setUser(modelUser);
        }
        return model;
    }


    @Override
    public name.pehl.karaka.server.settings.entity.Settings fromModel(name.pehl.karaka.shared.model.Settings model)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void merge(Settings model, name.pehl.karaka.server.settings.entity.Settings entity)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
