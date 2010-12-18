package name.pehl.tire.client.activity.view;

import static name.pehl.tire.model.TimeUnit.MONTH;
import static name.pehl.tire.model.TimeUnit.WEEK;
import name.pehl.tire.client.activity.event.ActivitiesNavigationEvent;
import name.pehl.tire.client.activity.model.Activities;
import name.pehl.tire.client.activity.presenter.ActivitiesNavigationUiHandlers;
import name.pehl.tire.client.activity.presenter.QuickChartPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

/**
 * View for the quick chart showing the activites by week / month.
 * <p>
 * TODO Implement MonthChartWidget
 * 
 * @author $Author$
 * @version $Date$ $Revision: 102
 *          $
 */
public class QuickChartView extends ViewWithUiHandlers<ActivitiesNavigationUiHandlers> implements
        QuickChartPresenter.MyView
{
    // @formatter:off
    interface WeekChartUi extends UiBinder<Widget, QuickChartView> {}
    private static WeekChartUi uiBinder = GWT.create(WeekChartUi.class);
    
    @UiField WeekChartWidget weekChart;
    @UiField MonthChartWidget monthChart;
    // @formatter:on

    private final Widget widget;


    public QuickChartView()
    {
        this.widget = uiBinder.createAndBindUi(this);
        this.monthChart.setVisible(false);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    @Override
    public void updateActivities(Activities activities)
    {
        if (activities.getUnit() == WEEK)
        {
            monthChart.setVisible(false);
            weekChart.setVisible(true);
            weekChart.update(activities);
        }
        else if (activities.getUnit() == MONTH)
        {
            weekChart.setVisible(false);
            monthChart.setVisible(true);
            monthChart.update(activities);
        }
    }


    @UiHandler("monthChart")
    void handleMonthNavigation(ActivitiesNavigationEvent event)
    {
        handleNavigation(event);
    }


    @UiHandler("weekChart")
    void handleWeekNavigation(ActivitiesNavigationEvent event)
    {
        handleNavigation(event);
    }


    void handleNavigation(ActivitiesNavigationEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().changeUnit(event.getUnit());
            switch (event.getDirection())
            {
                case PREV:
                    getUiHandlers().onPrev();
                    break;
                case CURRENT:
                    getUiHandlers().onCurrent();
                    break;
                case NEXT:
                    getUiHandlers().onNext();
                    break;
                default:
                    break;
            }
        }
    }
}
