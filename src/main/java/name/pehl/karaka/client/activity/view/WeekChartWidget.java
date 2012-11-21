package name.pehl.karaka.client.activity.view;

import name.pehl.karaka.client.ui.FormatUtils;
import name.pehl.karaka.shared.model.Activities;
import name.pehl.karaka.shared.model.Day;
import org.moxieapps.gwt.highcharts.client.Point;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-22 16:10:54 +0100 (Mi, 22. Dez 2010) $ $Revision: 102
 *          $
 */
public class WeekChartWidget extends QuickChartWidget
{
    final static String[] DAYS = new String[]{"Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"};
    final Map<Day, Point> dayToPoint;


    public WeekChartWidget()
    {
        super(DAYS);
        this.dayToPoint = new HashMap<Day, Point>();
    }

    @Override
    public void updateActivities(final Activities activities)
    {
        // FIXME When there's a "hole" in the weeks the order of the points is not correct
        dayToPoint.clear();
        Iterator<Day> iter = activities.getDays().iterator();
        for (Point point : series.getPoints())
        {
            double hours = 0;
            String tooltip = null;
            if (iter.hasNext())
            {
                Day day = iter.next();
                dayToPoint.put(day, point);
                hours = hours(day);
                tooltip = tooltip(day);
            }
            updatePoint(point, hours, tooltip);
        }
        // necessary to fix the alignment of the categories
        chart.getXAxis().setCategories(DAYS);
    }

    public void updateDay(final Day day)
    {
        Point point = dayToPoint.get(day);
        if (point != null)
        {
            updatePoint(point, hours(day), tooltip(day));
        }
    }

    double hours(final Day day)
    {
        return day.getDuration().getTotalHours();
    }

    String tooltip(final Day day)
    {
        return FormatUtils.date(day.getActivities().first().getStart()) + ": " + FormatUtils
                .duration(day.getDuration());
    }
}
