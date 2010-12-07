package name.pehl.tire.client.activity;

import java.util.List;

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
 * @author $Author$
 * @version $Date$ $Revision: 90
 *          $
 */
public class RecentActivitiesPresenter extends PresenterWidget<RecentActivitiesPresenter.MyView> implements
        RecentActivitiesUiHandlers
{
    public enum Unit
    {
        WEEK,
        MONTH;
    }

    public interface MyView extends View, HasUiHandlers<RecentActivitiesUiHandlers>
    {
        void updateActivities(List<Activity> activities);
    }

    private Unit unit;
    private int currentYear;
    private int currentMonth;
    private int currentWeek;
    private final DispatchAsync dispatcher;
    private final PlaceManager placeManager;


    @Inject
    public RecentActivitiesPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher,
            final PlaceManager placeManager)
    {
        super(eventBus, view);
        this.unit = Unit.WEEK;
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
    }


    /**
     * Loads the activities
     * 
     * @see com.gwtplatform.mvp.client.PresenterWidget#onReveal()
     */
    @Override
    protected void onReveal()
    {
        loadActivities();
    }


    private void loadActivities()
    {
        switch (unit)
        {
            case WEEK:
                dispatcher.execute(new GetWeekAction(currentYear, currentWeek), new TireCallback<GetWeekResult>(
                        placeManager)
                {
                    @Override
                    public void onSuccess(GetWeekResult result)
                    {
                        Week week = result.getWeek();
                        if (week != null)
                        {
                            currentYear = week.getYear();
                            currentMonth = week.getMonth();
                            currentWeek = week.getWeek();
                            getView().updateActivities(week.getActivities());
                        }
                    }
                });
                break;
            case MONTH:
                break;
            default:
                break;
        }
    }


    @Override
    public void onPrev()
    {
        switch (unit)
        {
            case WEEK:
                currentWeek--;
                if (currentWeek < 1)
                {
                    currentYear--;
                    currentWeek = 52;
                }
                break;
            case MONTH:
                break;
            default:
                break;
        }
        loadActivities();
    }


    @Override
    public void onCurrent()
    {
        currentYear = 0;
        currentMonth = 0;
        currentWeek = 0;
        loadActivities();
    }


    @Override
    public void onNext()
    {
        switch (unit)
        {
            case WEEK:
                currentWeek++;
                if (currentWeek > 52)
                {
                    currentYear++;
                    currentWeek = 1;
                }
                break;
            case MONTH:
                break;
            default:
                break;
        }
        loadActivities();
    }
}
