package name.pehl.karaka.server.settings.boundary;

import name.pehl.karaka.server.settings.control.CurrentSettings;
import name.pehl.karaka.server.settings.control.SettingsConverter;
import name.pehl.karaka.server.settings.entity.Settings;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Supported methods:
 * <ul>
 * <li>GET /settings: Get the current settings
 * </ul>
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2011-05-16 12:54:26 +0200 (Mo, 16. Mai 2011) $ $Revision: 110
 *          $
 */
@Path("/settings")
@Produces(MediaType.APPLICATION_JSON)
public class SettingsResource
{
    @Inject @CurrentSettings Instance<Settings> settings;
    @Inject SettingsConverter settingsConverter;


    @GET
    public name.pehl.karaka.shared.model.Settings currentSettings()
    {
        return settingsConverter.toModel(settings.get());
    }
}
