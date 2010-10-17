package name.pehl.tire.client.application;

import name.pehl.tire.client.activity.Week;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * @author $Author$
 * @version $Date$ $Revision: 102
 *          $
 */
public class QuickChartView extends ViewImpl implements QuickChartPresenter.MyView
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
}
