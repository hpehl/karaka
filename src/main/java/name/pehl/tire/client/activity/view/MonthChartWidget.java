package name.pehl.tire.client.activity.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import name.pehl.tire.client.ui.FormatUtils;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Week;

import org.moxieapps.gwt.highcharts.client.Point;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-17 21:37:43 +0100 (Fr, 17 Dez 2010) $ $Revision: 102
 *          $
 */
public class MonthChartWidget extends QuickChartWidget
{
    final static String[] WEEKS = new String[] {"n/a", "n/a", "n/a", "n/a"};
    final Map<Week, Point> weekToPoint;


    public MonthChartWidget()
    {
        super(WEEKS);
        this.weekToPoint = new HashMap<Week, Point>();
    }


    @Override
    public void updateActivities(Activities activities)
    {
        weekToPoint.clear();
        List<String> categories = new ArrayList<String>();
        Iterator<Week> iter = activities.getWeeks().iterator();
        for (Point point : series.getPoints())
        {
            double hours = 0;
            String tooltip = null;
            String category = "n/a";
            if (iter.hasNext())
            {
                Week week = iter.next();
                weekToPoint.put(week, point);
                hours = hours(week);
                tooltip = tooltip(week);
                category = String.valueOf(week.getWeek());
            }
            categories.add(category);
            updatePoint(point, hours, tooltip);
        }
        chart.getXAxis().setCategories(categories.toArray(new String[] {}));
    }


    public void updateWeek(Week week)
    {
        Point point = weekToPoint.get(week);
        if (point != null)
        {
            updatePoint(point, hours(week), tooltip(week));
        }
    }


    double hours(Week week)
    {
        return week.getMinutes() / 60.0;
    }


    String tooltip(Week week)
    {
        return "CW " + week.getWeek() + ": " + FormatUtils.hours(week.getMinutes());
    }
}
