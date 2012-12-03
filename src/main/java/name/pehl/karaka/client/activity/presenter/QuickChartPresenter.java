package name.pehl.karaka.client.activity.presenter;

import static name.pehl.karaka.client.NameTokens.dashboard;
import name.pehl.karaka.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.karaka.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.karaka.client.activity.event.ActivityChangedEvent;
import name.pehl.karaka.client.activity.event.ActivityChangedEvent.ActivityChangedHandler;
import name.pehl.karaka.client.activity.event.TickEvent;
import name.pehl.karaka.client.activity.event.TickEvent.TickHandler;
import name.pehl.karaka.shared.model.Activities;
import name.pehl.karaka.shared.model.Day;
import name.pehl.karaka.shared.model.Week;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 * <p>
 * Presenter for the quick chart showing the activites by week / month. The
 * quickchart syncs with the placeRequestFor displayed in the dashboard.
 * </p>
 * <h3>Ideas</h3>
 * <ul>
 * <li>When unit == month and clicking on a week, switch / animate to this week.
 * <li>When unit == week and clicking on a day, switch / animate to this day.
 * <li>Provide links to the parent level
 * <li>Provide previous / next links to go to the previous / next day / week
 * <li>SVG drawn title popups
 * <li>Different shades of gray for the different placeRequestFor of a day / week
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
public class QuickChartPresenter extends PresenterWidget<QuickChartPresenter.MyView> implements QuickChartUiHandlers,
        ActivitiesLoadedHandler, ActivityChangedHandler, TickHandler
{
    public interface MyView extends View, HasUiHandlers<QuickChartUiHandlers>
    {
        void updateActivities(Activities activities);


        void updateWeek(Week week);


        void updateDay(Day day);
    }

    final PlaceManager placeManager;


    @Inject
    public QuickChartPresenter(final EventBus eventBus, final QuickChartPresenter.MyView view,
            final PlaceManager placeManager)
    {
        super(eventBus, view);
        this.placeManager = placeManager;

        getView().setUiHandlers(this);
        getEventBus().addHandler(ActivitiesLoadedEvent.getType(), this);
        getEventBus().addHandler(ActivityChangedEvent.getType(), this);
        getEventBus().addHandler(TickEvent.getType(), this);
    }


    @Override
    public final void onActivitiesLoaded(ActivitiesLoadedEvent event)
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


    @Override
    public void onCalendarWeekClicked(Week week)
    {
        PlaceRequest placeRequest = new PlaceRequest(dashboard).with("year", String.valueOf(week.getYear())).with(
                "week", String.valueOf(week.getWeek()));
        placeManager.revealPlace(placeRequest);
    }
}
