package name.pehl.tire.client.activity.week;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

/**
 * @author $Author$
 * @version $Date$ $Revision: 102
 *          $
 */
public class WeekChartView extends ViewWithUiHandlers<WeekChartUiHandlers> implements WeekChartPresenter.MyView
{
    // @formatter:off
    interface WeekChartUi extends UiBinder<Widget, WeekChartView> {}
    private static WeekChartUi uiBinder = GWT.create(WeekChartUi.class);
    
    @UiField(provided = true) WeekChartWidget weekChart;
    // @formatter:on

    private final Widget widget;


    public WeekChartView()
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
    void handleWeekNavigation(WeekNavigationEvent event)
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
