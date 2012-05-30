package name.pehl.tire.client.activity.presenter;

import java.util.logging.Logger;

import name.pehl.tire.client.activity.dispatch.SaveActivityAction;
import name.pehl.tire.client.activity.dispatch.SaveActivityResult;
import name.pehl.tire.client.activity.event.TickEvent;
import name.pehl.tire.client.dispatch.TireCallback;
import name.pehl.tire.shared.model.Activity;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;

public class TickCommand implements HasHandlers, RepeatingCommand
{
    private static final Logger logger = Logger.getLogger(TickCommand.class.getName());
    private boolean running;
    private Activity activity;
    private final EventBus eventBus;
    private final Scheduler scheduler;
    private final DispatchAsync dispatcher;


    public TickCommand(final EventBus eventBus, final Scheduler scheduler, final DispatchAsync dispatcher)
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


    public void start(Activity activity)
    {
        this.running = true;
        this.activity = activity;
        this.execute();
        this.scheduler.scheduleFixedPeriod(this, 60000);
    }


    public void stop()
    {
        this.running = false;
        this.activity = null;
    }


    public void update(Activity activity)
    {
        this.activity = activity;
    }


    @Override
    public boolean execute()
    {
        if (activity != null)
        {
            logger.info("Tick for " + activity);
            activity.tick();
            dispatcher.execute(new SaveActivityAction(activity), new TireCallback<SaveActivityResult>(eventBus)
            {
                @Override
                public void onSuccess(SaveActivityResult result)
                {
                    activity = result.getStoredActivity();
                    TickEvent.fire(TickCommand.this, activity);
                }
            });
        }
        return running;
    }
}
