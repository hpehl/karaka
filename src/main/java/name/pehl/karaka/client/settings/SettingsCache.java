package name.pehl.karaka.client.settings;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import name.pehl.karaka.client.dispatch.KarakaCallback;
import name.pehl.karaka.client.dispatch.RestException;
import name.pehl.karaka.client.model.AbstractModelCache;
import name.pehl.karaka.client.model.ModelCache;
import name.pehl.karaka.shared.model.Settings;
import name.pehl.karaka.shared.model.User;

import static name.pehl.karaka.client.logging.Logger.Category.cache;
import static name.pehl.karaka.client.logging.Logger.info;
import static name.pehl.karaka.client.logging.Logger.warn;

public class SettingsCache extends AbstractModelCache<Settings> implements ModelCache<Settings>, HasHandlers
{
    private static Settings currentSettings = defaultSettings();


    @Inject
    public SettingsCache(final Scheduler scheduler, final EventBus eventBus, final DispatchAsync dispatcher)
    {
        super(scheduler, eventBus, dispatcher);
        models.add(currentSettings);
    }

    private static Settings defaultSettings()
    {
        Settings defaults = new Settings();
        defaults.setFormatHoursAsFloatingPointNumber(false);
        defaults.setTimeZoneId("UTC");
        User defaultUser = new User();
        defaultUser.setUserId("n/a");
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
    public void refresh()
    {
        info(cache, "About to refresh settings...");
        scheduler.scheduleDeferred(new Scheduler.ScheduledCommand()
        {
            @Override
            public void execute()
            {
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
                        info(cache, "Settings refreshed.");
                        if (changed)
                        {
                            SettingsChangedEvent.fire(SettingsCache.this, settings);
                        }
                    }

                    @Override
                    public void onNotFound(final RestException caught)
                    {
                        warn(cache, "No settings found.");
                    }
                });
            }
        });
    }

    @Override
    public void fireEvent(final GwtEvent<?> event)
    {
        eventBus.fireEventFromSource(event, this);
    }
}
