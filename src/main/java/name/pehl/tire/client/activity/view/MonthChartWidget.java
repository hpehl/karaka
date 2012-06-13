package name.pehl.tire.client.activity.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import name.pehl.tire.client.ui.FormatUtils;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Week;

import org.moxieapps.gwt.highcharts.client.Point;

import com.google.gwt.json.client.JSONString;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-17 21:37:43 +0100 (Fr, 17 Dez 2010) $ $Revision: 102
 *          $
 */
public class MonthChartWidget extends QuickChartWidget
{
    final static String[] WEEKS = new String[] {"n/a", "n/a", "n/a", "n/a"};


    public MonthChartWidget()
    {
        super(WEEKS);
    }


    @Override
    public void updateActivities(Activities activities)
    {
        List<String> categories = new ArrayList<String>();
        Iterator<Week> iter = activities.getWeeks().iterator();
        for (Point point : series.getPoints())
        {
            double hours = 0;
            String category = "n/a";
            if (iter.hasNext())
            {
                Week week = iter.next();
                hours = week.getMinutes() / 60.0;
                category = String.valueOf(week.getWeek());
                String tooltip = "CW " + week.getWeek() + ": " + FormatUtils.hours(week.getMinutes());
                point.getUserData().put("tooltip", new JSONString(tooltip));
            }
            point.update(hours);
            categories.add(category);
        }
        chart.getXAxis().setCategories(categories.toArray(new String[] {}));
    }
}
