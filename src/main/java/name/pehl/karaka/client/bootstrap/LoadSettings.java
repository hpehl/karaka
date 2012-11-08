package name.pehl.karaka.client.bootstrap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import name.pehl.karaka.client.settings.SettingsCache;

import static java.lang.Boolean.TRUE;

/**
 * @author Harald Pehl
 * @date 11/08/2012
 */
public class LoadSettings implements BootstrapCommand<Boolean>
{
    private final SettingsCache settingsCache;

    @Inject
    public LoadSettings(final SettingsCache settingsCache)
    {
        this.settingsCache = settingsCache;
    }

    @Override
    public void execute(final AsyncCallback<Boolean> callback)
    {
        settingsCache.refresh();
        callback.onSuccess(TRUE);
    }
}
