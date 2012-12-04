package name.pehl.karaka.client.activity.presenter;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
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

import static name.pehl.karaka.client.NameTokens.dashboard;
import static name.pehl.karaka.client.activity.dispatch.ActivitiesRequest.ACTIVITIES_PARAM;
import static name.pehl.karaka.client.activity.dispatch.ActivitiesRequest.SEPERATOR;
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
    static final String ACTIVITIES = "/activities/";
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
        placeManager.revealPlace(new PlaceRequest(dashboard).with(ACTIVITIES_PARAM, "currentWeek"));
    }

    @Override
    public void onCurrentMonth()
    {
        placeManager.revealPlace(new PlaceRequest(dashboard).with(ACTIVITIES_PARAM, "currentMonth"));
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
            placeManager.revealPlace(placeRequestFor(links.get(PREV)));
        }
    }

    @Override
    public void onNext()
    {
        if (links != null && links.hasNext())
        {
            placeManager.revealPlace(placeRequestFor(links.get(NEXT)));
        }
    }

    private PlaceRequest placeRequestFor(String url)
    {
        PlaceRequest placeRequest = new PlaceRequest(dashboard);
        if (Strings.emptyToNull(url) != null)
        {
            String path = extractPath(url, ACTIVITIES);
            path = CharMatcher.is('/').replaceFrom(path, SEPERATOR);
            placeRequest = placeRequest.with(ACTIVITIES_PARAM, path);
        }
        return placeRequest;
    }

    private String extractPath(String url, String after)
    {
        String path = url;
        int start = url.indexOf(after);
        if (start != -1)
        {
            path = url.substring(start + after.length());
        }
        int end = path.indexOf("?");
        if (end != -1)
        {
            path = path.substring(0, end);
        }
        return path;
    }


    public interface MyView extends View, HasUiHandlers<ActivityNavigationUiHandlers>
    {
        void updateHeader(Activities activities);
        void updateNavigation(HasLinks links);
    }
}
