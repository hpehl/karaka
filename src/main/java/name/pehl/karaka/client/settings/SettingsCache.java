package name.pehl.karaka.client.settings;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import name.pehl.karaka.client.dispatch.KarakaCallback;
import name.pehl.karaka.client.model.AbstractModelCache;
import name.pehl.karaka.client.model.ModelCache;
import name.pehl.karaka.shared.model.Settings;
import name.pehl.karaka.shared.model.User;

import static java.util.logging.Level.INFO;

public class SettingsCache extends AbstractModelCache<Settings> implements ModelCache<Settings>, HasHandlers
{
    private static Settings currentSettings = defaultSettings();


    @Inject
    public SettingsCache(final EventBus eventBus, final Scheduler scheduler, final DispatchAsync dispatcher)
    {
        super(eventBus, dispatcher);
        models.add(currentSettings);
    }


    @Override
    public void refresh()
    {
        logger.log(INFO, "About to refresh settings...");
        dispatcher.execute(new GetSettingsAction(), new KarakaCallback<GetSettingsResult>(eventBus)
        {
            @Override
            public void onSuccess(final GetSettingsResult result)
            {
                Settings settings = result.getSettings();
                boolean changed = currentSettings.hasChanged(settings);
                models.clear();
                models.add(settings);
                currentSettings = settings;
                logger.log(INFO, "Settings refreshed.");
                if (changed)
                {
                    SettingsChangedEvent.fire(SettingsCache.this, settings);
                }
            }
        });
    }


    private static Settings defaultSettings()
    {
        Settings defaults = new Settings();
        defaults.setFormatHoursAsFloatingPointNumber(false);
        defaults.setTimeZoneId("UTC");
        User defaultUser = new User();
        defaultUser.setUsername("n/a");
        defaultUser.setFirstname("n/a");
        defaultUser.setSurname("n/a");
        defaultUser.setEmail("n/a");
        defaults.setUser(defaultUser);
        return defaults;
    }


    /**
     * Static helper to access current settings from other static methods
     * 
     * @return
     */
    public static Settings currentSettings()
    {
        return currentSettings;
    }


    @Override
    public void fireEvent(final GwtEvent<?> event)
    {
        eventBus.fireEventFromSource(event, this);
    }
}
