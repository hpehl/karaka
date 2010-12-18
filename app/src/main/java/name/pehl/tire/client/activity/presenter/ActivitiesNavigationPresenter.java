package name.pehl.tire.client.activity.presenter;

import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.tire.client.activity.model.Activities;
import name.pehl.tire.client.activity.model.ActivitiesNavigationData;
import name.pehl.tire.client.activity.model.ActivitiesNavigationDataAdapter;
import name.pehl.tire.model.TimeUnit;

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public abstract class ActivitiesNavigationPresenter<V extends ActivitiesNavigationPresenter.MyView> extends
        PresenterWidget<V> implements ActivitiesNavigationUiHandlers, ActivitiesLoadedHandler
{
    public interface MyView extends View, HasUiHandlers<ActivitiesNavigationUiHandlers>
    {
        void updateActivities(Activities activities);
    }

    protected Activities currentActivities;
    protected ActivitiesNavigationData currentAnd;
    protected final PlaceManager placeManager;


    protected ActivitiesNavigationPresenter(final EventBus eventBus, final V view, final PlaceManager placeManager)
    {
        super(eventBus, view);
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
        getEventBus().addHandler(ActivitiesLoadedEvent.getType(), this);
    }


    @Override
    public void onPrev()
    {
        PlaceRequest placeRequest = new ActivitiesNavigationDataAdapter().toPlaceRequest(currentAnd.decrease());
        placeManager.revealPlace(placeRequest);
    }


    @Override
    public void onCurrent()
    {
        PlaceRequest placeRequest = new ActivitiesNavigationDataAdapter().toPlaceRequest(currentAnd.current());
        placeManager.revealPlace(placeRequest);
    }


    @Override
    public void onNext()
    {
        PlaceRequest placeRequest = new ActivitiesNavigationDataAdapter().toPlaceRequest(currentAnd.increase());
        placeManager.revealPlace(placeRequest);
    }


    @Override
    public void changeUnit(TimeUnit unit)
    {
        currentAnd = currentAnd.changeUnit(unit);
    }


    @Override
    public final void onActivitiesLoaded(ActivitiesLoadedEvent event)
    {
        currentActivities = event.getActivities();
        currentAnd = new ActivitiesNavigationDataAdapter().fromEvent(event);
        getView().updateActivities(currentActivities);
    }
}
