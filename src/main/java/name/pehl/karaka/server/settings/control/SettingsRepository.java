package name.pehl.karaka.server.settings.control;

import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import name.pehl.karaka.server.repository.BaseEntityRepository;
import name.pehl.karaka.server.sampledata.DefaultSettings;
import name.pehl.karaka.server.settings.entity.Settings;

import org.slf4j.Logger;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-05 00:40:48 +0100 (Fr, 05. Nov 2010) $ $Revision: 121
 *          $
 */
public class SettingsRepository extends BaseEntityRepository<Settings>
{
    @Inject Logger logger;
    @Inject @DefaultSettings Settings defaultSettings;


    public SettingsRepository()
    {
        super(Settings.class, null);
    }


    @Produces
    @CurrentSettings
    public Settings currentSettings()
    {
        List<Settings> settings = query().list();
        if (settings.isEmpty())
        {
            logger.error("No settings found. Will return default settings.");
            return defaultSettings;
        }
        else
        {
            if (settings.size() > 1)
            {
                logger.warn("More than one records found in settings table. Will return first entry.");
            }
            return settings.get(0);
        }
    }
}
