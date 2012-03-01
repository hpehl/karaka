package name.pehl.tire.client.activity.view;

import static java.lang.Math.max;

import java.util.List;
import java.util.ListIterator;

import name.pehl.tire.client.activity.model.Activities;
import name.pehl.tire.client.activity.model.Day;
import name.pehl.tire.client.ui.FormatUtils;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.uibinder.client.UiConstructor;

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
            List<Day> days = activities.getDays();

            // update title
            StringBuilder title = new StringBuilder();
            title.append("CW ").append(activities.getWeek()).append(" / ").append(activities.getYear()).append(" - ")
                    .append(FormatUtils.hours(activities.getMinutes())).append("\n")
                    .append(FormatUtils.date(activities.getStart())).append(" - ")
                    .append(FormatUtils.date(activities.getEnd()));
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
            int index = 0;
            for (ListIterator<Day> iter = days.listIterator(days.size()); iter.hasPrevious();)
            {
                Day day = iter.previous();
                if (index >= 0 && index < columns.length)
                {
                    JavaScriptObject column = columns[index];
                    String path = path(index, day.getMinutes());
                    String tooltip = FormatUtils.date(day.getStart()) + ": " + FormatUtils.hours(day.getMinutes());
                    animateColumn(column, path, tooltip);
                }
                index++;
            }
        }
    }
}
