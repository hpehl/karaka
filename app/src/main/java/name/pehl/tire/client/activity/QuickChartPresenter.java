package name.pehl.tire.client.activity;

import static name.pehl.tire.client.activity.ActivitiesNavigation.Unit.*;
import name.pehl.tire.client.activity.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.tire.client.activity.ActivitiesNavigation.Unit;
import name.pehl.tire.client.activity.week.GetWeekAction;
import name.pehl.tire.client.activity.week.GetWeekResult;
import name.pehl.tire.client.activity.week.Week;
import name.pehl.tire.client.dispatch.TireCallback;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

/**
 * Presenter for the quick chart showing the activites by week / month.
 * 
 * @author $Author$
 * @version $Date$ $Revision: 102
 *          $
 */
public class QuickChartPresenter extends PresenterWidget<QuickChartPresenter.MyView> implements
        ActivitiesNavigationUiHandlers, ActivitiesLoadedHandler
{
    public interface MyView extends View, HasUiHandlers<ActivitiesNavigationUiHandlers>
    {
        void updateChart(Week week, boolean animate);
    }

    private Unit unit;
    private ActivitiesNavigationData and;
    private final DispatchAsync dispatcher;
    private final PlaceManager placeManager;


    @Inject
    public QuickChartPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher,
            final PlaceManager placeManager)
    {
        super(eventBus, view);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        this.unit = WEEK;
        this.and = new ActivitiesNavigationData(0, 0, 0);
        getView().setUiHandlers(this);
        getEventBus().addHandler(ActivitiesLoadedEvent.getType(), this);
    }


    @Override
    protected void onReveal()
    {
        loadActivities();
    }


    private void loadActivities()
    {
        if (unit == WEEK)
        {
            dispatcher.execute(new GetWeekAction(and.getYear(), and.getWeek()), new TireCallback<GetWeekResult>(
                    placeManager)
            {
                @Override
                public void onSuccess(GetWeekResult result)
                {
                    Week week = result.getWeek();
                    if (week != null)
                    {
                        ActivitiesLoadedEvent.fire(QuickChartPresenter.this, null, week);
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
        and = new ActivitiesNavigationData(0, 0, 0);
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
    public void onActivitiesLoaded(ActivitiesLoadedEvent event)
    {
        Week week = event.getWeek();
        getView().updateChart(week, true);
        and = new ActivitiesNavigationData(week.getYear(), week.getMonth(), week.getWeek());
    }
}
