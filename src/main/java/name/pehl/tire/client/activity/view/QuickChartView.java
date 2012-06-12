package name.pehl.tire.client.activity.view;

import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;
import name.pehl.tire.client.activity.presenter.QuickChartPresenter;
import name.pehl.tire.client.ui.FormatUtils;
import name.pehl.tire.shared.model.Activities;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Legend;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * View for the quick chart showing the activites by week / month.
 * <p>
 * TODO Implement MonthChartWidget
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-22 16:43:49 +0100 (Mi, 22. Dez 2010) $ $Revision: 102
 *          $
 */
public class QuickChartView extends ViewImpl implements QuickChartPresenter.MyView
{
    public interface Binder extends UiBinder<SimplePanel, QuickChartView>
    {
    }

    private static final String COLOR = "#3d3d3d";
    private static final String BACKGROUND_COLOR = "#eaeaea";

    private final SimplePanel simplePanel;
    private final Chart weekChart;
    private final Chart monthChart;


    @Inject
    public QuickChartView(final Binder binder)
    {
        this.simplePanel = binder.createAndBindUi(this);
        this.weekChart = new Chart().setLegend(new Legend().setEnabled(false));
        this.weekChart.getXAxis().setCategories("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su");
        this.monthChart = new Chart().setLegend(new Legend().setEnabled(false));
    }


    @Override
    public Widget asWidget()
    {
        return simplePanel;
    }


    @Override
    public void updateActivities(Activities activities)
    {
        if (activities.getUnit() == WEEK)
        {
            monthChart.setVisible(false);
            weekChart.setVisible(true);
            updateWeekChart(activities);
        }
        else if (activities.getUnit() == MONTH)
        {
            weekChart.setVisible(false);
            monthChart.setVisible(true);
            updateMonthChart(activities);
        }
    }


    private void updateWeekChart(Activities activities)
    {
        StringBuilder title = new StringBuilder().append(activities).append(" - ")
                .append(FormatUtils.hours(activities.getMinutes()));
        weekChart.setChartTitleText(title.toString());
        StringBuilder subtitle = new StringBuilder().append(FormatUtils.dateDuration(activities.getStart(),
                activities.getEnd()));
        weekChart.setChartSubtitleText(subtitle.toString());
    }


    private void updateMonthChart(Activities activities)
    {
    }
}
