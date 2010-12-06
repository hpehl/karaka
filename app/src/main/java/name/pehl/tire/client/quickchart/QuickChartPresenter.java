package name.pehl.tire.client.quickchart;

import name.pehl.tire.client.activity.GetWeekAction;
import name.pehl.tire.client.activity.GetWeekResult;
import name.pehl.tire.client.activity.Week;
import name.pehl.tire.client.dispatch.TireCallback;

import com.google.gwt.core.client.GWT;
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
public class QuickChartPresenter extends PresenterWidget<QuickChartPresenter.MyView> implements QuickChartUiHandlers
{
    public interface MyView extends View, HasUiHandlers<QuickChartUiHandlers>
    {
        void update(Week week, boolean animate);
    }

    private int currentYear;
    private int currentWeek;
    private final DispatchAsync dispatcher;
    private final PlaceManager placeManager;


    @Inject
    public QuickChartPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher,
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
        getActivities(currentWeek, currentYear);
        // Create local test data
        // Week week = new Week();
        // week.setWeek(41);
        // for (int i = 0; i < 5; i++)
        // {
        // @SuppressWarnings("deprecation")
        // Date date = new Date(110, 9, 11 + i);
        // Activity activity = new Activity();
        // int minutes = (int) Math.round(Math.random() * 200) + 200;
        // activity.setStart(date);
        // activity.setEnd(date);
        // activity.setMinutes(minutes);
        // Day day = new Day();
        // day.setDate(date);
        // day.addActivity(activity);
        // week.addDay(day);
        // }
        // getView().update(week, true);
    }


    private void getActivities(int year, int week)
    {
        dispatcher.execute(new GetWeekAction(year, week), new TireCallback<GetWeekResult>(placeManager)
        {
            @Override
            public void onSuccess(GetWeekResult result)
            {
                Week week = result.getWeek();
                if (week != null)
                {
                    getView().update(week, true);
                    currentYear = week.getYear();
                    currentWeek = week.getWeek();
                }
            }
        });
    }


    @Override
    public void onPrev()
    {
        GWT.log("Previous calendarweek");
        currentWeek--;
        if (currentWeek < 0)
        {
            currentYear--;
            currentWeek = 1;
        }
    }


    @Override
    public void onCurrent()
    {
        GWT.log("Current calendarweek");
        currentYear = 0;
        currentWeek = 0;
    }


    @Override
    public void onNext()
    {
        GWT.log("Next calendarweek");
        currentWeek++;
        if (currentWeek > 52)
        {
            currentYear++;
            currentWeek = 1;
        }
    }
}
