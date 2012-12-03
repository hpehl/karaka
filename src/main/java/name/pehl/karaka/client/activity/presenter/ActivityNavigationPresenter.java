package name.pehl.karaka.client.activity.presenter;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import name.pehl.karaka.client.activity.dispatch.ActivitiesRequest;
import name.pehl.karaka.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.karaka.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.karaka.client.activity.event.ActivitiesNotFoundEvent;
import name.pehl.karaka.client.activity.event.ActivityChangedEvent;
import name.pehl.karaka.client.activity.event.ActivityChangedEvent.ActivityChangedHandler;
import name.pehl.karaka.client.activity.event.TickEvent;
import name.pehl.karaka.client.activity.event.TickEvent.TickHandler;
import name.pehl.karaka.shared.model.Activities;
import name.pehl.karaka.shared.model.HasLinks;

import java.security.acl.AclNotFoundException;

import static name.pehl.karaka.shared.model.HasLinks.NEXT;
import static name.pehl.karaka.shared.model.HasLinks.PREV;

/**
 * <h3>Events</h3>
 * <ol>
 * <li>IN</li>
 * <ul>
 * <li>{@linkplain ActivitiesLoadedEvent}</li>
 * <li>{@linkplain AclNotFoundException}</li>
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
        ActivityNavigationUiHandlers, ActivitiesLoadedHandler, ActivitiesNotFoundEvent.ActivitiesNotFoundHandler,
        ActivityChangedHandler, TickHandler
{
    final PlaceManager placeManager;
    final SelectMonthPresenter selectMonthPresenter;
    final SelectWeekPresenter selectWeekPresenter;
    /**
     * The current links used for navigation. These are most likey the same as the current placeRequestFor.
     * However in case of ActivitiesNotFoundEvent (404), the links are valid whereas the placeRequestFor are not.
     */
    HasLinks links;
    /**
     * The currently displayed placeRequestFor
     */
    Activities activities;


    @Inject
    public ActivityNavigationPresenter(EventBus eventBus, MyView view, final PlaceManager placeManager,
            final SelectMonthPresenter selectMonthPresenter, final SelectWeekPresenter selectWeekPresenter)
    {
        super(eventBus, view);
        this.placeManager = placeManager;
        this.selectMonthPresenter = selectMonthPresenter;
        this.selectWeekPresenter = selectWeekPresenter;

        getEventBus().addHandler(ActivitiesLoadedEvent.getType(), this);
        getEventBus().addHandler(ActivitiesNotFoundEvent.getType(), this);
        getEventBus().addHandler(ActivityChangedEvent.getType(), this);
        getEventBus().addHandler(TickEvent.getType(), this);
        getView().setUiHandlers(this);
    }


    // ------------------------------------------------------------------ setup

    @Override
    public void onActivitiesLoaded(ActivitiesLoadedEvent event)
    {
        this.links = event.getActivities();
        this.activities = event.getActivities();
        getView().updateHeader(event.getActivities());
        getView().updateNavigation(event.getActivities());
    }

    @Override
    public void onActivitiesNotFound(final ActivitiesNotFoundEvent event)
    {
        // Only update links when there are no activities present
        if (activities == null)
        {
            links = event.getLinks();
            getView().updateNavigation(links);
        }
    }

    // --------------------------------------------------------- event handling

    @Override
    public void onActivityChanged(ActivityChangedEvent event)
    {
        this.links = event.getActivities();
        this.activities = event.getActivities();
        getView().updateHeader(event.getActivities());
        getView().updateNavigation(event.getActivities());
    }

    @Override
    public void onTick(TickEvent event)
    {
        this.links = event.getActivities();
        this.activities = event.getActivities();
    }


    // ------------------------------------------------------------ ui handlers

    @Override
    public void onCurrentWeek()
    {
        placeManager.revealPlace(ActivitiesRequest.placeRequestFor("currentWeek"));
    }

    @Override
    public void onCurrentMonth()
    {
        placeManager.revealPlace(ActivitiesRequest.placeRequestFor("currentMonth"));
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
        if (links != null && links.hasPrev())
        {
            placeManager.revealPlace(ActivitiesRequest.placeRequestFor(links.get(PREV)));
        }
    }

    @Override
    public void onNext()
    {
        if (links != null && links.hasNext())
        {
            placeManager.revealPlace(ActivitiesRequest.placeRequestFor(links.get(NEXT)));
        }
    }


    public interface MyView extends View, HasUiHandlers<ActivityNavigationUiHandlers>
    {
        void updateHeader(Activities activities);
        void updateNavigation(HasLinks links);
    }
}
