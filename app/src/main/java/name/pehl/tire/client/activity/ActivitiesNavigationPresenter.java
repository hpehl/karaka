package name.pehl.tire.client.activity;

import static name.pehl.tire.client.activity.ActivitiesNavigation.Unit.WEEK;
import name.pehl.tire.client.activity.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.tire.client.activity.ActivitiesNavigation.Unit;
import name.pehl.tire.client.dispatch.TireCallback;

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public abstract class ActivitiesNavigationPresenter<V extends ActivitiesNavigationView> extends PresenterWidget<V>
        implements ActivitiesNavigationUiHandlers, ActivitiesLoadedHandler
{
    private Unit unit;
    private ActivitiesNavigationData and;
    private final DispatchAsync dispatcher;
    private final PlaceManager placeManager;


    protected ActivitiesNavigationPresenter(final EventBus eventBus, final V view, final DispatchAsync dispatcher,
            final PlaceManager placeManager)
    {
        super(eventBus, view);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        this.unit = WEEK;
        this.and = new ActivitiesNavigationData();
        getView().setUiHandlers(this);
        getEventBus().addHandler(ActivitiesLoadedEvent.getType(), this);
    }


    private void loadActivities()
    {
        if (unit == WEEK)
        {
            dispatcher.execute(new GetActivitiesAction(and), new TireCallback<GetActivitiesResult>(placeManager)
            {
                @Override
                public void onSuccess(GetActivitiesResult result)
                {
                    Activities activities = result.getActivities();
                    if (activities != null)
                    {
                        ActivitiesLoadedEvent.fire(ActivitiesNavigationPresenter.this, activities);
                    }
                }
            });
        }
        else
        {
            // TODO Implement me!
        }
    }


    @Override
    public void onPrev()
    {
        if (unit == WEEK)
        {
            and.decreaseWeek();
        }
        else
        {
            and.decreaseMonth();
        }
        loadActivities();
    }


    @Override
    public void onCurrent()
    {
        and = new ActivitiesNavigationData();
        loadActivities();
    }


    @Override
    public void onNext()
    {
        if (unit == WEEK)
        {
            and.increaseWeek();
        }
        else
        {
            and.increaseMonth();
        }
        loadActivities();
    }


    @Override
    public final void onActivitiesLoaded(ActivitiesLoadedEvent event)
    {
        Activities activities = event.getActivities();
        if (activities != null)
        {
            and = new ActivitiesNavigationData(activities.getYear(), activities.getMonth(), activities.getWeek());
            getView().updateActivities(activities, unit);
        }
    }


    public Unit getUnit()
    {
        return unit;
    }


    public void setUnit(Unit unit)
    {
        this.unit = unit;
    }
}
