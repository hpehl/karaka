package name.pehl.tire.client.activity;

import name.pehl.tire.client.activity.week.Week;
import name.pehl.tire.client.activity.week.WeekChartWidget;

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
    
    @UiField(provided = true) WeekChartWidget weekChart;
    // @formatter:on

    private final Widget widget;


    public QuickChartView()
    {
        this.weekChart = new WeekChartWidget(200, 200, new String[] {"Mo", "Tue", "Wed", "Thu", "Fr", "Sat", "Sun"});
        this.widget = uiBinder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    @Override
    public void updateChart(Week week, boolean animate)
    {
        weekChart.update(week, animate);
    }


    @UiHandler("weekChart")
    void handleWeekNavigation(ActivitiesNavigationEvent event)
    {
        if (getUiHandlers() != null)
        {
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
