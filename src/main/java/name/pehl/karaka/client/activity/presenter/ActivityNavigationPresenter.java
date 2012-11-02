package name.pehl.karaka.client.activity.presenter;

import static name.pehl.karaka.client.NameTokens.dashboard;
import static name.pehl.karaka.shared.model.TimeUnit.MONTH;
import static name.pehl.karaka.shared.model.TimeUnit.WEEK;
import name.pehl.karaka.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.karaka.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.karaka.client.activity.event.ActivityChangedEvent;
import name.pehl.karaka.client.activity.event.ActivityChangedEvent.ActivityChangedHandler;
import name.pehl.karaka.client.activity.event.TickEvent;
import name.pehl.karaka.client.activity.event.TickEvent.TickHandler;
import name.pehl.karaka.shared.model.Activities;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

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
 * <li>none</li>
 * </ul>
 * </ol>
 * <h3>Dispatcher actions</h3>
 * <ul>
 * <li>none</li>
 * </ul>
 */
public class ActivityNavigationPresenter extends PresenterWidget<ActivityNavigationPresenter.MyView> implements
        ActivityNavigationUiHandlers, ActivitiesLoadedHandler, ActivityChangedHandler, TickHandler
{
    // ---------------------------------------------------------- inner classes

    public interface MyView extends View, HasUiHandlers<ActivityNavigationUiHandlers>
    {
        void updateHeader(Activities activities);
    }

    // ------------------------------------------------------- (static) members

    final PlaceManager placeManager;
    final SelectMonthPresenter selectMonthPresenter;
    final SelectWeekPresenter selectWeekPresenter;

    /**
     * The currently displayed activities
     */
    Activities activities;


    // ------------------------------------------------------------------ setup

    @Inject
    public ActivityNavigationPresenter(EventBus eventBus, MyView view, final PlaceManager placeManager,
            final SelectMonthPresenter selectMonthPresenter, final SelectWeekPresenter selectWeekPresenter)
    {
        super(eventBus, view);
        this.placeManager = placeManager;
        this.selectMonthPresenter = selectMonthPresenter;
        this.selectWeekPresenter = selectWeekPresenter;
        getEventBus().addHandler(ActivitiesLoadedEvent.getType(), this);
        getEventBus().addHandler(ActivityChangedEvent.getType(), this);
        getEventBus().addHandler(TickEvent.getType(), this);
        getView().setUiHandlers(this);
    }


    // --------------------------------------------------------- event handling

    @Override
    public void onActivitiesLoaded(ActivitiesLoadedEvent event)
    {
        this.activities = event.getActivities();
        getView().updateHeader(event.getActivities());
    }


    @Override
    public void onActivityChanged(ActivityChangedEvent event)
    {
        this.activities = event.getActivities();
        getView().updateHeader(event.getActivities());
    }


    @Override
    public void onTick(TickEvent event)
    {
        this.activities = event.getActivities();
        getView().updateHeader(event.getActivities());
    }


    // ------------------------------------------------------------ ui handlers

    @Override
    public void onCurrentWeek()
    {
        PlaceRequest placeRequest = new PlaceRequest(dashboard).with("current", WEEK.name().toLowerCase());
        placeManager.revealPlace(placeRequest);
    }


    @Override
    public void onCurrentMonth()
    {
        PlaceRequest placeRequest = new PlaceRequest(dashboard).with("current", MONTH.name().toLowerCase());
        placeManager.revealPlace(placeRequest);
    }


    @Override
    public void onSelectWeek(int left, int top)
    {
        addToPopupSlot(null);
        selectWeekPresenter.getView().setPosition(left, top);
        addToPopupSlot(selectWeekPresenter, false);
    }


    @Override
    public void onSelectMonth(int left, int top)
    {
        addToPopupSlot(null);
        selectMonthPresenter.getView().setPosition(left, top);
        addToPopupSlot(selectMonthPresenter, false);
    }


    @Override
    public void onPrevious()
    {
        if (activities != null)
        {
            PlaceRequest placeRequest = null;
            int newYear = activities.getYear();
            if (activities.getUnit() == MONTH)
            {
                int newMonth = activities.getMonth();
                newMonth--;
                if (newMonth < 1)
                {
                    newMonth = 12;
                    newYear--;
                }
                placeRequest = new PlaceRequest(dashboard).with("year", String.valueOf(newYear)).with("month",
                        String.valueOf(newMonth));
            }
            else if (activities.getUnit() == WEEK)
            {
                int newWeek = activities.getWeek();
                newWeek--;
                if (newWeek < 1)
                {
                    newWeek = 52;
                    newYear--;
                }
                placeRequest = new PlaceRequest(dashboard).with("year", String.valueOf(newYear)).with("week",
                        String.valueOf(newWeek));
            }
            if (placeRequest != null)
            {
                placeManager.revealPlace(placeRequest);
            }
        }
    }


    @Override
    public void onNext()
    {
        if (activities != null)
        {
            PlaceRequest placeRequest = null;
            int newYear = activities.getYear();
            if (activities.getUnit() == MONTH)
            {
                int newMonth = activities.getMonth();
                newMonth++;
                if (newMonth > 12)
                {
                    newMonth = 1;
                    newYear++;
                }
                placeRequest = new PlaceRequest(dashboard).with("year", String.valueOf(newYear)).with("month",
                        String.valueOf(newMonth));
            }
            else if (activities.getUnit() == WEEK)
            {
                int newWeek = activities.getWeek();
                newWeek++;
                if (newWeek > 52)
                {
                    newWeek = 1;
                    newYear++;
                }
                placeRequest = new PlaceRequest(dashboard).with("year", String.valueOf(newYear)).with("week",
                        String.valueOf(newWeek));
            }
            if (placeRequest != null)
            {
                placeManager.revealPlace(placeRequest);
            }
        }
    }
}
