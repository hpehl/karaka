package name.pehl.tire.client.activity.presenter;

import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.DELETE;
import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.tire.client.activity.event.ActivityChangedEvent;
import name.pehl.tire.client.activity.event.ActivityChangedEvent.ActivityChangedHandler;
import name.pehl.tire.client.activity.event.TickEvent;
import name.pehl.tire.client.activity.event.TickEvent.TickHandler;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Activity;
import name.pehl.tire.shared.model.Day;
import name.pehl.tire.shared.model.Week;

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
 * <li>SVG drawn title popups
 * <li>Different shades of gray for the different activities of a day / week
 * <li>Draw an X axis
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


        void updateWeek(Week week);


        void updateDay(Day day);
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
        if (event.getAction() == DELETE)
        {
            getView().updateActivities(activities);
        }
        else
        {
            updateActivity(event.getActivity());
        }
    }


    @Override
    public void onTick(TickEvent event)
    {
        updateActivity(event.getActivity());
    }


    void updateActivity(Activity activity)
    {
        if (activities.getUnit() == MONTH)
        {
            Week week = activities.weekOf(activity);
            if (week != null)
            {
                getView().updateWeek(week);
            }
        }
        else if (activities.getUnit() == WEEK)
        {
            Day day = activities.dayOf(activity);
            if (day != null)
            {
                getView().updateDay(day);
            }
        }
    }
}
