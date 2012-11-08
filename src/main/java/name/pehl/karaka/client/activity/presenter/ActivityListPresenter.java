package name.pehl.karaka.client.activity.presenter;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import name.pehl.karaka.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.karaka.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.karaka.client.activity.event.ActivityAction.Action;
import name.pehl.karaka.client.activity.event.ActivityActionEvent;
import name.pehl.karaka.client.activity.event.ActivityChangedEvent;
import name.pehl.karaka.client.activity.event.ActivityChangedEvent.ActivityChangedHandler;
import name.pehl.karaka.client.activity.event.TickEvent;
import name.pehl.karaka.client.activity.event.TickEvent.TickHandler;
import name.pehl.karaka.shared.model.Activities;
import name.pehl.karaka.shared.model.Activity;

import static name.pehl.karaka.client.logging.Logger.Category;
import static name.pehl.karaka.client.logging.Logger.trace;

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
            trace(Category.activity, "Open " + activity + " for edit");
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
