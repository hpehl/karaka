package name.pehl.tire.client.application;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * @author $Author$
 * @version $Date$ $Revision$
 */
public class QuickChartView extends ViewImpl implements QuickChartPresenter.MyView
{
    interface QuickChartUi extends UiBinder<Widget, QuickChartView>
    {
    }

    private static QuickChartUi uiBinder = GWT.create(QuickChartUi.class);

    private final Widget widget;

    @UiField
    CalendarWeekChart chart;


    public QuickChartView()
    {
        this.widget = uiBinder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    @Override
    public void setHours(double... hours)
    {
        chart.setHours(hours);
    }
}
