package name.pehl.tire.client.quickchart;

import name.pehl.tire.client.activity.Week;

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
public class QuickChartView extends ViewWithUiHandlers<QuickChartUiHandlers> implements QuickChartPresenter.MyView
{
    interface QuickChartUi extends UiBinder<Widget, QuickChartView>
    {
    }

    private static QuickChartUi uiBinder = GWT.create(QuickChartUi.class);

    private final Widget widget;

    @UiField(provided = true)
    CalendarWeekChart calendarWeekChart;


    public QuickChartView()
    {
        this.calendarWeekChart = new CalendarWeekChart(200, 200, new String[] {"Mo", "Tue", "Wed", "Thu", "Fr"});
        this.widget = uiBinder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    @Override
    public void update(Week week, boolean animate)
    {
        calendarWeekChart.update(week, animate);
    }


    @UiHandler("calendarWeekChart")
    void handleCalendarWeekNavigation(CalendarWeekNavigationEvent event)
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
