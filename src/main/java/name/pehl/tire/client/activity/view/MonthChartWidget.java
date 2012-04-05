package name.pehl.tire.client.activity.view;

import java.util.SortedSet;

import name.pehl.tire.client.ui.FormatUtils;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Week;

import com.google.gwt.core.client.JavaScriptObject;
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
    public void update(Activities activities)
    {
        if (initialized && activities != null && activities.getWeeks() != null && !activities.getWeeks().isEmpty())
        {
            SortedSet<Week> weeks = activities.getWeeks();

            // update title
            String month = NumberFormat.getFormat("00").format(activities.getMonth());
            StringBuilder title = new StringBuilder();
            title.append(month).append(" ").append(activities.getYear()).append(" - ")
                    .append(FormatUtils.hours(activities.getMinutes())).append("\n")
                    .append(FormatUtils.date(activities.getStart())).append(" - ")
                    .append(FormatUtils.date(activities.getEnd()));
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
            int index = 0;
            for (Week week : weeks)
            {
                if (index >= 0 && index < columns.length)
                {
                    JavaScriptObject column = columns[index];
                    JavaScriptObject legend = legends[index];
                    String path = path(index, week.getMinutes());
                    String cw = "CW " + week.getWeek();
                    String tooltip = cw + ": " + FormatUtils.hours(week.getMinutes());
                    animateColumn(column, path, tooltip);
                    updateLegend(legend, cw);
                }
                index++;
            }
        }
    }
}
