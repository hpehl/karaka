package name.pehl.tire.client;

import java.util.logging.Logger;

import name.pehl.tire.client.dispatch.TireCallback;
import name.pehl.tire.client.settings.CurrentSettings;
import name.pehl.tire.client.settings.GetSettingsAction;
import name.pehl.tire.client.settings.GetSettingsResult;
import name.pehl.tire.client.settings.SettingsChangedEvent;
import name.pehl.tire.shared.model.Settings;

import org.mortbay.log.Log;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;

public class StartupManager implements HasHandlers
{
    static final Logger logger = Logger.getLogger(StartupManager.class.getName());
    final EventBus eventBus;
    final Scheduler scheduler;
    final DispatchAsync dispatcher;


    @Inject
    public StartupManager(final EventBus eventBus, final Scheduler scheduler, final DispatchAsync dispatcher)
    {
        this.eventBus = eventBus;
        this.scheduler = scheduler;
        this.dispatcher = dispatcher;
    }


    @Override
    public void fireEvent(GwtEvent<?> event)
    {
        this.eventBus.fireEventFromSource(event, this);
    }


    public void startup()
    {
        // Get settings
        scheduler.scheduleDeferred(new ScheduledCommand()
        {
            @Override
            public void execute()
            {
                Log.info("Startup info: Settings...");
                dispatcher.execute(new GetSettingsAction(), new TireCallback<GetSettingsResult>(eventBus)
                {
                    @Override
                    public void onSuccess(GetSettingsResult result)
                    {
                        Settings settings = result.getSettings();
                        CurrentSettings.set(settings);
                        SettingsChangedEvent.fire(StartupManager.this, settings);
                        Log.info("Startup info: Settings DONE");
                    }
                });
            }
        });

        // Get projects
        scheduler.scheduleDeferred(new ScheduledCommand()
        {
            @Override
            public void execute()
            {
                Log.info("Startup info: Projects...");
            }
        });

        // Get tags
        scheduler.scheduleDeferred(new ScheduledCommand()
        {
            @Override
            public void execute()
            {
                Log.info("Startup info: Tags...");
            }
        });

        // Get clients
        scheduler.scheduleDeferred(new ScheduledCommand()
        {
            @Override
            public void execute()
            {
                Log.info("Startup info: Clients...");
            }
        });
    }
}
