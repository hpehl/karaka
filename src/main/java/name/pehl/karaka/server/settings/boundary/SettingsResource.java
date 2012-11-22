package name.pehl.karaka.server.settings.boundary;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import name.pehl.karaka.server.settings.control.CurrentSettings;
import name.pehl.karaka.server.settings.control.SettingsConverter;
import name.pehl.karaka.server.settings.entity.Settings;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Supported methods: <ul> <li>GET /settings: Get the current settings</li> </ul>
 */
@Path("/settings")
@Produces(APPLICATION_JSON)
public class SettingsResource
{
    @Inject @CurrentSettings Instance<Settings> settings;
    @Inject SettingsConverter settingsConverter;

    @GET
    public name.pehl.karaka.shared.model.Settings currentSettings(@Context UriInfo uriInfo)
    {
        name.pehl.karaka.shared.model.Settings result = settingsConverter.toModel(settings.get());
        UserService userService = UserServiceFactory.getUserService();
        if (userService != null && result.getUser() != null)
        {
            String logoutUrl = uriInfo.getBaseUriBuilder().path("/login/openid.html").build().toASCIIString();
            logoutUrl = userService.createLogoutURL(logoutUrl);
            result.getUser().setLogoutUrl(logoutUrl);
        }
        return result;
    }
}
