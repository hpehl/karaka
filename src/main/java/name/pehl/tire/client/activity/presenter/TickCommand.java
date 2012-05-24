package name.pehl.tire.client.activity.presenter;

import java.util.logging.Logger;

import name.pehl.tire.client.activity.event.TickEvent;
import name.pehl.tire.shared.model.Activity;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.EventBus;

public class TickCommand implements HasHandlers, RepeatingCommand
{
    private static final Logger logger = Logger.getLogger(TickCommand.class.getName());
    private boolean running;
    private Activity activity;
    private final EventBus eventBus;
    private final Scheduler scheduler;


    public TickCommand(final EventBus eventBus, final Scheduler scheduler)
    {
        this.eventBus = eventBus;
        this.scheduler = scheduler;
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
        this.scheduler.scheduleFixedPeriod(this, 60000);
    }


    public void stop()
    {
        this.running = false;
        this.activity = null;
    }


    @Override
    public boolean execute()
    {
        if (activity != null)
        {
            logger.info("Tick for " + activity);
            activity.tick();
            TickEvent.fire(this, activity);
        }
        return running;
    }
}
