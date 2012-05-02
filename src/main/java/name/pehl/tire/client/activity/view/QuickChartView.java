package name.pehl.tire.client.activity.view;

import name.pehl.tire.client.activity.presenter.QuickChartPresenter;
import name.pehl.tire.client.activity.presenter.RecentActivitiesUiHandlers;
import name.pehl.tire.shared.model.Activities;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;

/**
 * View for the quick chart showing the activites by week / month.
 * <p>
 * TODO Implement MonthChartWidget
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-22 16:43:49 +0100 (Mi, 22. Dez 2010) $ $Revision: 102
 *          $
 */
public class QuickChartView extends ViewWithUiHandlers<RecentActivitiesUiHandlers> implements
        QuickChartPresenter.MyView
{
    public interface Binder extends UiBinder<Widget, QuickChartView>
    {
    }

    private final Widget widget;
    @UiField WeekChartWidget weekChart;
    @UiField MonthChartWidget monthChart;


    @Inject
    public QuickChartView(final Binder binder)
    {
        this.widget = binder.createAndBindUi(this);
        this.monthChart.setVisible(false);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    @Override
    public void mask()
    {
    }


    @Override
    public void unmask()
    {
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
}
