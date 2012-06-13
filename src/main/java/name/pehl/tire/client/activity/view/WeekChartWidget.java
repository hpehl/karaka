package name.pehl.tire.client.activity.view;

import java.util.Iterator;

import name.pehl.tire.client.ui.FormatUtils;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Day;

import org.moxieapps.gwt.highcharts.client.Point;

import com.google.gwt.json.client.JSONString;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-22 16:10:54 +0100 (Mi, 22. Dez 2010) $ $Revision: 102
 *          $
 */
public class WeekChartWidget extends QuickChartWidget
{
    final static String[] DAYS = new String[] {"Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"};


    public WeekChartWidget()
    {
        super(DAYS);
    }


    @Override
    public void updateActivities(Activities activities)
    {
        Iterator<Day> iter = activities.getDays().iterator();
        for (Point point : series.getPoints())
        {
            double hours = 0;
            if (iter.hasNext())
            {
                Day day = iter.next();
                hours = day.getMinutes() / 60.0;
                String tooltip = FormatUtils.date(day.activities().first().getStart()) + ": "
                        + FormatUtils.hours(day.getMinutes());
                point.getUserData().put("tooltip", new JSONString(tooltip));
            }
            point.update(hours);
        }
        // necessary to fix the alignment of the categories
        chart.getXAxis().setCategories("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su");
    }
}
