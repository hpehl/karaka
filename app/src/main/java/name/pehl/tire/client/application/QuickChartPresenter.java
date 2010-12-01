package name.pehl.tire.client.application;

import java.util.Date;

import name.pehl.tire.client.activity.Activity;
import name.pehl.tire.client.activity.Day;
import name.pehl.tire.client.activity.Week;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

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


    @Inject
    public QuickChartPresenter(final EventBus eventBus, final MyView view)
    {
        super(eventBus, view);
    }


    @Override
    @SuppressWarnings("deprecation")
    protected void onReset()
    {
        // Test
        Week week = new Week();
        week.setCalendarWeek(41);
        for (int i = 0; i < 5; i++)
        {
            Date date = new Date(110, 9, 11 + i);
            Activity activity = new Activity();
            int minutes = (int) Math.round(Math.random() * 200) + 200;
            activity.setStart(date);
            activity.setEnd(date);
            activity.setMinutes(minutes);
            Day day = new Day(date);
            day.addActivity(activity);
            week.addDay(day);
        }
        getView().update(week, true);
    }
}
