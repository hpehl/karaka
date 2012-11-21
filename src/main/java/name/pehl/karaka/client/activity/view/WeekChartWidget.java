package name.pehl.karaka.client.activity.view;

import name.pehl.karaka.client.ui.FormatUtils;
import name.pehl.karaka.shared.model.Activities;
import name.pehl.karaka.shared.model.Day;
import org.moxieapps.gwt.highcharts.client.Point;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

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
        int index = 0;
        dayToPoint.clear();
        SortedSet<Day> days = activities.getDays();
        for (Day day : days)
        {
            double hours = 0;
            String tooltip = null;
            Point point = series.getPoints()[index];
            dayToPoint.put(day, point);
            if (!day.isEmpty())
            {
                hours = hours(day);
                tooltip = tooltip(day);
            }
            updatePoint(point, hours, tooltip);
            index++;
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
        if (day.getDuration().isZero())
        {
            return "";
        }
        return FormatUtils.date(day.getActivities().first().getStart()) + ": " + FormatUtils
                .duration(day.getDuration());
    }
}
