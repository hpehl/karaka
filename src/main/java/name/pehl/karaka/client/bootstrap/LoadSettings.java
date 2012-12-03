package name.pehl.karaka.client.bootstrap;

import com.google.gwt.user.client.Command;
import com.google.inject.Inject;
import name.pehl.karaka.client.settings.SettingsCache;

import java.util.Iterator;

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
    public void execute(Iterator<BootstrapStep> iterator, final Command command)
    {
        settingsCache.refresh();
        next(iterator, command);
    }
}
