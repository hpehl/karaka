package name.pehl.karaka.server.settings.control;

import com.google.appengine.api.users.UserServiceFactory;
import name.pehl.karaka.server.repository.Repository;
import name.pehl.karaka.server.settings.entity.Settings;
import name.pehl.karaka.server.settings.entity.User;
import org.slf4j.Logger;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-05 00:40:48 +0100 (Fr, 05. Nov 2010) $ $Revision: 121 $
 */
public class SettingsRepository extends Repository<Settings>
{
    @Inject Logger logger;
    @Inject @DefaultSettings Instance<Settings> defaultSettings;


    public SettingsRepository()
    {
        super(Settings.class, null);
    }

    @Produces
    @CurrentSettings
    public Settings currentSettings()
    {
        int count = query().count();
        if (count == 0)
        {
            logger.info("No settings found.");
            Settings newSettings = defaultSettings.get();
            com.google.appengine.api.users.User currentUser = UserServiceFactory.getUserService().getCurrentUser();
            User user = new User(currentUser.getUserId(), currentUser.getNickname(), currentUser.getEmail());
            newSettings.setUser(user);
            save(newSettings);
            logger.info("New settings created and saved: " + newSettings);
            return newSettings;
        }
        else
        {
            if (count > 1)
            {
                logger.warn("More than one records found in settings table. Will return first entry.");
            }
            return query().first().get();
        }
    }
}
