package name.pehl.karaka.client.bootstrap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import name.pehl.karaka.client.settings.SettingsCache;

import java.util.Iterator;

import static java.lang.Boolean.TRUE;

/**
 * @author Harald Pehl
 * @date 11/08/2012
 */
public class LoadSettings extends BootstrapStep
{
    private final SettingsCache settingsCache;

    @Inject
    public LoadSettings(final SettingsCache settingsCache)
    {
        this.settingsCache = settingsCache;
    }

    @Override
    public void execute(Iterator<BootstrapStep> iterator, final AsyncCallback<Boolean> callback)
    {
        settingsCache.refresh();
        callback.onSuccess(TRUE);

        execute(iterator, callback);
    }
}
