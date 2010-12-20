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
    protected ActivitiesNavigationDataAdapter anda;
    protected final PlaceManager placeManager;


    protected ActivitiesNavigationPresenter(final EventBus eventBus, final V view, final PlaceManager placeManager)
    {
        super(eventBus, view);
        this.placeManager = placeManager;
        this.anda = new ActivitiesNavigationDataAdapter();
        getView().setUiHandlers(this);
        getEventBus().addHandler(ActivitiesLoadedEvent.getType(), this);
    }


    @Override
    public void onRelative(int offset)
    {
        placeManager.revealPlace(anda.toPlaceRequest(currentAnd.relative(offset)));
    }


    @Override
    public void onPrev()
    {
        placeManager.revealPlace(anda.toPlaceRequest(currentAnd.decrease()));
    }


    @Override
    public void onCurrent()
    {
        placeManager.revealPlace(anda.toPlaceRequest(currentAnd.current()));
    }


    @Override
    public void onNext()
    {
        placeManager.revealPlace(anda.toPlaceRequest(currentAnd.increase()));
    }


    @Override
    public void changeUnit(TimeUnit unit)
    {
        currentAnd = currentAnd.changeUnit(unit);
    }


    @Override
    public final void onActivitiesLoaded(ActivitiesLoadedEvent event)
    {
        currentAnd = anda.fromEvent(event);
        currentActivities = event.getActivities();
        getView().updateActivities(currentActivities);
    }
}
