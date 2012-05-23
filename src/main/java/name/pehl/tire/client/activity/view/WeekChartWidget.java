package name.pehl.tire.client.activity.view;

import java.util.Iterator;
import java.util.SortedSet;

import name.pehl.tire.client.ui.FormatUtils;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Day;

import com.google.gwt.uibinder.client.UiConstructor;

import static java.lang.Math.max;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-22 16:10:54 +0100 (Mi, 22. Dez 2010) $ $Revision: 102
 *          $
 */
public class WeekChartWidget extends QuickChartWidget
{

    // ----------------------------------------------------------- constructors

    @UiConstructor
    public WeekChartWidget(int width, int height, String legendTitles)
    {
        super(width, height, legendTitles);
    }


    // ----------------------------------------------------------------- update

    @Override
    public void update(Activities activities)
    {
        if (initialized && activities != null && activities.getDays() != null && !activities.getDays().isEmpty())
        {
            SortedSet<Day> days = activities.getDays();

            // update title
            StringBuilder title = new StringBuilder();
            title.append("CW ").append(activities.getWeek()).append(" / ").append(activities.getYear()).append(" - ")
                    .append(FormatUtils.hours(activities.getMinutes())).append("\n")
                    .append(FormatUtils.dateDuration(activities.getStart(), activities.getEnd()));
            updateTitle(title.toString());

            // update max
            max = 0;
            for (Day day : days)
            {
                max = max(max, day.getMinutes());
            }
            max += max * .05;
            oneMinute = (double) usableHeight / max;

            // update columns
            Iterator<Day> iter = days.iterator();
            for (int index = 0; index < columns.length; index++)
            {
                String path;
                String tooltip = "";
                if (iter.hasNext())
                {
                    Day day = iter.next();
                    path = path(index, day.getMinutes());
                    tooltip = FormatUtils.date(day.getStart()) + ": " + FormatUtils.hours(day.getMinutes());

                }
                else
                {
                    path = path(index, 0);
                }
                animateColumn(columns[index], path, tooltip);
            }
        }
    }
}
