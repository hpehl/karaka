package name.pehl.karaka.client.application;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import name.pehl.karaka.client.NameTokens;
import name.pehl.karaka.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.karaka.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.karaka.client.settings.SettingsCache;
import name.pehl.karaka.client.settings.SettingsChangedEvent;
import name.pehl.karaka.client.settings.SettingsChangedEvent.SettingsChangedHandler;
import name.pehl.karaka.shared.model.Activities;
import name.pehl.karaka.shared.model.User;

import static name.pehl.karaka.shared.model.TimeUnit.MONTH;
import static name.pehl.karaka.shared.model.TimeUnit.WEEK;

/**
 * <p>
 * Presenter for the top level navigation
 * </p>
 * <h3>Events</h3>
 * <ol>
 * <li>IN</li>
 * <ul>
 * <li>{@linkplain ActivitiesLoadedEvent}</li>
 * <li>{@linkplain SettingsChangedEvent}</li>
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
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-06 17:48:50 +0100 (Mo, 06. Dez 2010) $ $Revision: 95
 *          $
 */
public class NavigationPresenter extends PresenterWidget<NavigationPresenter.MyView> implements
        ActivitiesLoadedHandler, SettingsChangedHandler
{
    public interface MyView extends View
    {
        void highlight(String token);
        void setDashboardToken(String token);
        void updateUser(User user);
    }

    /**
     * Constant for the static message slot.
     */
    public static final Object SLOT_Message = new Object();

    final PlaceManager placeManager;
    final MessagePresenter messagePresenter;
    final SettingsCache settingsCache;


    @Inject
    public NavigationPresenter(final EventBus eventBus, final MyView view, final PlaceManager placeManager,
            final MessagePresenter messagePresenter, final SettingsCache settingsCache)
    {
        super(eventBus, view);
        this.placeManager = placeManager;
        this.messagePresenter = messagePresenter;
        this.settingsCache = settingsCache;

        getEventBus().addHandler(ActivitiesLoadedEvent.getType(), this);
        getEventBus().addHandler(SettingsChangedEvent.getType(), this);
    }


    /**
     * Sets {@link MessagePresenter} in {@link #SLOT_Message}.
     * 
     * @see com.gwtplatform.mvp.client.PresenterWidget#onReveal()
     */
    @Override
    protected void onReveal()
    {
        super.onReveal();
        getView().updateUser(settingsCache.single().getUser());
        setInSlot(SLOT_Message, messagePresenter);
    }


    @Override
    protected void onHide()
    {
        super.onHide();
        removeFromSlot(SLOT_Message, messagePresenter);
    }


    /**
     * {@linkplain NavigationPresenter.MyView#highlight(String) Highlights} the
     * current place in the {@linkplain NavigationPresenter.MyView navigation
     * view}.
     * 
     * @see com.gwtplatform.mvp.client.PresenterWidget#onReset()
     */
    @Override
    protected void onReset()
    {
        PlaceRequest request = placeManager.getCurrentPlaceRequest();
        String token = request.getNameToken();
        getView().highlight(token);
    }


    @Override
    public void onActivitiesLoaded(ActivitiesLoadedEvent event)
    {
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.dashboard);
        Activities activities = event.getActivities();
        if (activities.getUnit() == MONTH)
        {
            placeRequest = placeRequest.with("year", String.valueOf(activities.getYear())).with("month",
                    String.valueOf(activities.getMonth()));
        }
        else if (activities.getUnit() == WEEK)
        {
            placeRequest = placeRequest.with("year", String.valueOf(activities.getYear())).with("week",
                    String.valueOf(activities.getWeek()));
        }
        String token = placeManager.buildHistoryToken(placeRequest);
        getView().setDashboardToken(token);
    }


    @Override
    public void onSettingsChanged(SettingsChangedEvent event)
    {
        getView().updateUser(event.getSettings().getUser());
    }
}
