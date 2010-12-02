package name.pehl.tire.client.application;

import name.pehl.tire.client.activity.GetActivitiesByWeekAction;
import name.pehl.tire.client.activity.GetActivitiesByWeekResult;
import name.pehl.tire.client.activity.Week;
import name.pehl.tire.client.dispatch.TireCallback;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

/**
 * @author $Author$
 * @version $Date$ $Revision: 102
 *          $
 */
public class QuickChartPresenter extends PresenterWidget<QuickChartPresenter.MyView>
{
    public interface MyView extends View
    {
        void update(Week week, boolean animate);
    }

    private final DispatchAsync dispatcher;
    private final PlaceManager placeManager;


    @Inject
    public QuickChartPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher,
            final PlaceManager placeManager)
    {
        super(eventBus, view);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
    }


    @Override
    protected void onReset()
    {
        // Get current week data
        // TODO Current week
        dispatcher.execute(new GetActivitiesByWeekAction(42), new TireCallback<GetActivitiesByWeekResult>(placeManager)
        {
            @Override
            public void onSuccess(GetActivitiesByWeekResult result)
            {
                Week week = result.getWeek();
                getView().update(week, true);
            }
        });

        // Test
        // Week week = new Week();
        // week.setCalendarWeek(41);
        // for (int i = 0; i < 5; i++)
        // {
        // Date date = new Date(110, 9, 11 + i);
        // Activity activity = new Activity();
        // int minutes = (int) Math.round(Math.random() * 200) + 200;
        // activity.setStart(date);
        // activity.setEnd(date);
        // activity.setMinutes(minutes);
        // Day day = new Day(date);
        // day.addActivity(activity);
        // week.addDay(day);
        // }
        // getView().update(week, true);
    }
}
