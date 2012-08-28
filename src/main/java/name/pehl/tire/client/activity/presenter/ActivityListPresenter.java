package name.pehl.tire.client.activity.presenter;

import java.util.logging.Logger;

import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.tire.client.activity.event.ActivityAction.Action;
import name.pehl.tire.client.activity.event.ActivityActionEvent;
import name.pehl.tire.client.activity.event.ActivityChangedEvent;
import name.pehl.tire.client.activity.event.ActivityChangedEvent.ActivityChangedHandler;
import name.pehl.tire.client.activity.event.TickEvent;
import name.pehl.tire.client.activity.event.TickEvent.TickHandler;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Activity;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * <h3>Events</h3>
 * <ol>
 * <li>IN</li>
 * <ul>
 * <li>{@linkplain ActivitiesLoadedEvent}</li>
 * <li>{@linkplain ActivityChangedEvent}</li>
 * <li>{@linkplain TickEvent}</li>
 * </ul>
 * <li>OUT</li>
 * <ul>
 * <li>{@linkplain ActivityActionEvent}</li>
 * </ul>
 * </ol>
 * <h3>Dispatcher actions</h3>
 * <ul>
 * <li>none</li>
 * </ul>
 */
public class ActivityListPresenter extends PresenterWidget<ActivityListPresenter.MyView> implements
        ActivityListUiHandlers, ActivitiesLoadedHandler, ActivityChangedHandler, TickHandler
{
    // ---------------------------------------------------------- inner classes

    public interface MyView extends View, HasUiHandlers<ActivityListUiHandlers>
    {
        void updateActivities(Activities activities);
    }

    // ------------------------------------------------------- (static) members

    static final Logger logger = Logger.getLogger(ActivityListPresenter.class.getName());
    final EditActivityPresenter editActivityPresenter;


    // ------------------------------------------------------------------ setup

    @Inject
    public ActivityListPresenter(EventBus eventBus, MyView view, final EditActivityPresenter editActivityPresenter)
    {
        super(eventBus, view);
        this.editActivityPresenter = editActivityPresenter;
        getEventBus().addHandler(ActivitiesLoadedEvent.getType(), this);
        getEventBus().addHandler(ActivityChangedEvent.getType(), this);
        getEventBus().addHandler(TickEvent.getType(), this);
        getView().setUiHandlers(this);
    }


    // --------------------------------------------------------- event handling

    @Override
    public void onActivitiesLoaded(ActivitiesLoadedEvent event)
    {
        getView().updateActivities(event.getActivities());
    }


    @Override
    public void onActivityChanged(ActivityChangedEvent event)
    {
        getView().updateActivities(event.getActivities());
    }


    @Override
    public void onTick(TickEvent event)
    {
        getView().updateActivities(event.getActivities());
    }


    // ------------------------------------------------------------ ui handlers

    @Override
    public void onActivityAction(Action action, Activity activity)
    {
        if (action == Action.DETAILS)
        {
            logger.fine("Open " + activity + " for edit");
            editActivityPresenter.getView().setActivity(activity);
            addToPopupSlot(null);
            addToPopupSlot(editActivityPresenter);
        }
        else
        {
            // Forward to Activitycontroller....
            ActivityActionEvent.fire(this, action, activity);
        }
    }
}
