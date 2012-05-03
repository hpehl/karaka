package name.pehl.tire.client.application;

import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.tire.client.activity.model.ActivitiesNavigator;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-06 17:48:50 +0100 (Mo, 06. Dez 2010) $ $Revision: 95
 *          $
 */
public class NavigationPresenter extends PresenterWidget<NavigationPresenter.MyView> implements ActivitiesLoadedHandler
{
    public interface MyView extends View
    {
        void highlight(String token);


        void setDashboardToken(String token);
    }

    /**
     * Constant for the static message slot.
     */
    public static final Object SLOT_Message = new Object();

    private final PlaceManager placeManager;
    private final MessagePresenter messagePresenter;


    @Inject
    public NavigationPresenter(final EventBus eventBus, final MyView view, final PlaceManager placeManager,
            final MessagePresenter messagePresenter)
    {
        super(eventBus, view);
        this.placeManager = placeManager;
        this.messagePresenter = messagePresenter;
        getEventBus().addHandler(ActivitiesLoadedEvent.getType(), this);
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
        ActivitiesNavigator activitiesNavigator = ActivitiesNavigator.fromEvent(event);
        PlaceRequest placeRequest = activitiesNavigator.toPlaceRequest();
        String token = placeManager.buildHistoryToken(placeRequest);
        getView().setDashboardToken(token);
    }
}
