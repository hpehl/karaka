package name.pehl.tire.client.activity.view;

import name.pehl.tire.client.activity.presenter.QuickChartPresenter;
import name.pehl.tire.shared.model.Activities;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;

/**
 * View for the quick chart showing the activites by week / month.
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-22 16:43:49 +0100 (Mi, 22. Dez 2010) $ $Revision: 102
 *          $
 */
public class QuickChartView extends ViewImpl implements QuickChartPresenter.MyView
{
    public interface Binder extends UiBinder<Panel, QuickChartView>
    {
    }

    private final Panel panel;
    private final WeekChartWidget weekChart;
    private final MonthChartWidget monthChart;


    @Inject
    public QuickChartView(final Binder binder)
    {
        this.weekChart = new WeekChartWidget();
        this.monthChart = new MonthChartWidget();
        this.panel = binder.createAndBindUi(this);
        this.panel.add(weekChart);
        this.panel.add(monthChart);

    }


    @Override
    public Widget asWidget()
    {
        return panel;
    }


    @Override
    public void updateActivities(Activities activities)
    {
        if (activities.getUnit() == WEEK)
        {
            monthChart.asWidget().setVisible(false);
            weekChart.asWidget().setVisible(true);
            weekChart.updateActivities(activities);
        }
        else if (activities.getUnit() == MONTH)
        {
            weekChart.asWidget().setVisible(false);
            monthChart.asWidget().setVisible(true);
            monthChart.updateActivities(activities);
        }
    }
}
