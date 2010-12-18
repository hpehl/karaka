package name.pehl.tire.client.activity.view;

import static java.lang.Math.max;

import java.util.List;
import java.util.ListIterator;

import name.pehl.tire.client.activity.event.ActivitiesNavigationEvent;
import name.pehl.tire.client.activity.model.Activities;
import name.pehl.tire.client.activity.model.Direction;
import name.pehl.tire.client.activity.model.Week;
import name.pehl.tire.client.ui.FormatUtils;
import name.pehl.tire.model.TimeUnit;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiConstructor;

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
            List<Week> weeks = activities.getWeeks();

            // update title
            String month = NumberFormat.getFormat("00").format(activities.getMonth());
            StringBuilder title = new StringBuilder();
            title.append(month).append(" / ").append(activities.getYear()).append(" - ")
                    .append(FormatUtils.hours(activities.getMinutes()));
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
            for (ListIterator<Week> iter = weeks.listIterator(weeks.size()); iter.hasPrevious();)
            {
                Week week = iter.previous();
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


    // --------------------------------------------------------- event handling

    @Override
    protected void onPrev()
    {
        ActivitiesNavigationEvent.fire(this, TimeUnit.MONTH, Direction.PREV);
    }


    @Override
    protected void onCurrent()
    {
        ActivitiesNavigationEvent.fire(this, TimeUnit.MONTH, Direction.CURRENT);
    }


    @Override
    protected void onNext()
    {
        ActivitiesNavigationEvent.fire(this, TimeUnit.MONTH, Direction.NEXT);
    }
}
