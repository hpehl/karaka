package name.pehl.karaka.client;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import name.pehl.karaka.client.activity.presenter.ActivityController;
import name.pehl.karaka.client.client.ClientsCache;
import name.pehl.karaka.client.model.ModelCache;
import name.pehl.karaka.client.project.ProjectsCache;
import name.pehl.karaka.client.settings.SettingsCache;
import name.pehl.karaka.client.tag.TagsCache;
import name.pehl.karaka.shared.model.BaseModel;
import name.pehl.karaka.shared.model.Client;
import name.pehl.karaka.shared.model.Project;
import name.pehl.karaka.shared.model.Settings;
import name.pehl.karaka.shared.model.Tag;

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
    final ActivityController activityController;
    final List<ScheduledCommand> startupCommands;


    @Inject
    public StartupManager(final EventBus eventBus, final Scheduler scheduler, final DispatchAsync dispatcher,
            final ActivityController activityController, final SettingsCache settingsCache, final TagsCache tagsCache,
            final ClientsCache clientsCache, final ProjectsCache projectsCache)
    {
        this.eventBus = eventBus;
        this.scheduler = scheduler;
        this.dispatcher = dispatcher;
        this.activityController = activityController;
        this.startupCommands = new ArrayList<ScheduledCommand>();
        this.startupCommands.add(new InitCacheCommand<Settings>(settingsCache));
        this.startupCommands.add(new InitCacheCommand<Tag>(tagsCache));
        this.startupCommands.add(new InitCacheCommand<Client>(clientsCache));
        this.startupCommands.add(new InitCacheCommand<Project>(projectsCache));
    }


    @Override
    public void fireEvent(final GwtEvent<?> event)
    {
        this.eventBus.fireEventFromSource(event, this);
    }


    public void startup()
    {
        activityController.start();
        for (ScheduledCommand command : startupCommands)
        {
            scheduler.scheduleDeferred(command);
        }
    }

    static class InitCacheCommand<T extends BaseModel> implements ScheduledCommand
    {
        final ModelCache<T> modelCache;


        public InitCacheCommand(final ModelCache<T> modelCache)
        {
            this.modelCache = modelCache;
        }


        @Override
        public void execute()
        {
            modelCache.refresh();
        }
    }
}
