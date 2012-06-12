package name.pehl.tire.client.activity.view;

import java.util.Iterator;
import java.util.SortedSet;

import name.pehl.tire.client.ui.FormatUtils;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Week;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiConstructor;

import static java.lang.Math.max;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-17 21:37:43 +0100 (Fr, 17 Dez 2010) $ $Revision: 102
 *          $
 */
public class MonthChartWidget extends QuickChartWidget
{

    // ----------------------------------------------------------- constructors

    @UiConstructor
    public MonthChartWidget(int width, int height, String legendTitles)
    {
        super(width, height, legendTitles);
    }


    // ----------------------------------------------------------------- update

    @Override
    public void updateActivities(Activities activities)
    {
        if (initialized && activities != null && activities.getWeeks() != null && !activities.getWeeks().isEmpty())
        {
            SortedSet<Week> weeks = activities.getWeeks();

            // update title
            String month = NumberFormat.getFormat("00").format(activities.getMonth());
            StringBuilder title = new StringBuilder();
            title.append(month).append(" ").append(activities.getYear()).append(" - ")
                    .append(FormatUtils.hours(activities.getMinutes())).append("\n")
                    .append(FormatUtils.dateDuration(activities.getStart(), activities.getEnd()));
            updateTitle(title.toString());

            // update max
            max = 0;
            for (Week week : weeks)
            {
                max = max(max, week.getMinutes());
            }
            max += max * .05;
            oneMinute = (double) usableHeight / max;

            // update columns
            Iterator<Week> iter = weeks.iterator();
            for (int index = 0; index < columns.length; index++)
            {
                String path;
                String cw = "";
                String tooltip = "";
                if (iter.hasNext())
                {
                    Week week = iter.next();
                    path = path(index, week.getMinutes());
                    cw = "CW " + week.getWeek();
                    tooltip = cw + ": " + FormatUtils.hours(week.getMinutes());

                }
                else
                {
                    path = path(index, 0);
                    cw = "n/a";
                }
                animateColumn(columns[index], path, tooltip);
                updateLegend(legends[index], cw);
            }
        }
    }
}
