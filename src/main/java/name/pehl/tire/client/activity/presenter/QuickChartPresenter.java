package name.pehl.tire.client.activity.presenter;

import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.tire.client.activity.event.ActivityChangedEvent;
import name.pehl.tire.client.activity.event.ActivityChangedEvent.ActivityChangedHandler;
import name.pehl.tire.client.activity.event.TickEvent;
import name.pehl.tire.client.activity.event.TickEvent.TickHandler;
import name.pehl.tire.shared.model.Activities;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * <p>
 * Presenter for the quick chart showing the activites by week / month. The
 * quickchart syncs with the activities displayed in the dashboard.
 * </p>
 * <h3>Ideas</h3>
 * <ul>
 * <li>When unit == month and clicking on a week, switch / animate to this week.
 * <li>When unit == week and clicking on a day, switch / animate to this day.
 * <li>Provide links to the parent level
 * <li>Provide previous / next links to go to the previous / next day / week
 * </ul>
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
 * <li>none</li>
 * </ul>
 * </ol>
 * <h3>Dispatcher actions</h3>
 * <ul>
 * <li>none
 * </ul>
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-22 16:43:49 +0100 (Mi, 22. Dez 2010) $ $Revision: 102
 *          $
 */
public class QuickChartPresenter extends PresenterWidget<QuickChartPresenter.MyView> implements
        ActivitiesLoadedHandler, ActivityChangedHandler, TickHandler
{
    public interface MyView extends View
    {
        void updateActivities(Activities activities);
    }

    Activities activities;


    @Inject
    public QuickChartPresenter(final EventBus eventBus, final QuickChartPresenter.MyView view)
    {
        super(eventBus, view);
        getEventBus().addHandler(ActivitiesLoadedEvent.getType(), this);
        getEventBus().addHandler(ActivityChangedEvent.getType(), this);
        getEventBus().addHandler(TickEvent.getType(), this);
    }


    @Override
    public final void onActivitiesLoaded(ActivitiesLoadedEvent event)
    {
        activities = event.getActivities();
        getView().updateActivities(event.getActivities());
    }


    @Override
    public void onActivityChanged(ActivityChangedEvent event)
    {
        getView().updateActivities(activities);
    }


    @Override
    public void onTick(TickEvent event)
    {
        getView().updateActivities(activities);
    }
}
