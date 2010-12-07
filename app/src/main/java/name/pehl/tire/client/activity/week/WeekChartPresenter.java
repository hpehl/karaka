package name.pehl.tire.client.activity.week;

import name.pehl.tire.client.dispatch.TireCallback;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

/**
 * @author $Author$
 * @version $Date$ $Revision: 102
 *          $
 */
public class WeekChartPresenter extends PresenterWidget<WeekChartPresenter.MyView> implements WeekChartUiHandlers
{
    public interface MyView extends View, HasUiHandlers<WeekChartUiHandlers>
    {
        void updateChart(Week week, boolean animate);
    }

    private int currentYear;
    private int currentWeek;
    private final DispatchAsync dispatcher;
    private final PlaceManager placeManager;


    @Inject
    public WeekChartPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher,
            final PlaceManager placeManager)
    {
        super(eventBus, view);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
    }


    @Override
    protected void onReveal()
    {
        loadWeek();
    }


    private void loadWeek()
    {
        dispatcher.execute(new GetWeekAction(currentYear, currentWeek), new TireCallback<GetWeekResult>(placeManager)
        {
            @Override
            public void onSuccess(GetWeekResult result)
            {
                Week week = result.getWeek();
                if (week != null)
                {
                    getView().updateChart(week, true);
                    currentYear = week.getYear();
                    currentWeek = week.getWeek();
                }
            }
        });
    }


    @Override
    public void onPrev()
    {
        currentWeek--;
        if (currentWeek < 1)
        {
            currentYear--;
            currentWeek = 52;
        }
        loadWeek();
    }


    @Override
    public void onCurrent()
    {
        currentYear = 0;
        currentWeek = 0;
        loadWeek();
    }


    @Override
    public void onNext()
    {
        currentWeek++;
        if (currentWeek > 52)
        {
            currentYear++;
            currentWeek = 1;
        }
        loadWeek();
    }
}
