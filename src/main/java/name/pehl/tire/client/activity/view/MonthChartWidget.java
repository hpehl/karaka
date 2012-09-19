package name.pehl.tire.client.activity.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import name.pehl.tire.client.activity.view.WeekClickedEvent.HasWeekClickedHandlers;
import name.pehl.tire.client.activity.view.WeekClickedEvent.WeekClickedHandler;
import name.pehl.tire.client.ui.FormatUtils;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Week;

import org.moxieapps.gwt.highcharts.client.Point;
import org.moxieapps.gwt.highcharts.client.events.PointClickEvent;
import org.moxieapps.gwt.highcharts.client.plotOptions.PlotOptions.Cursor;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-17 21:37:43 +0100 (Fr, 17 Dez 2010) $ $Revision: 102
 *          $
 */
public class MonthChartWidget extends QuickChartWidget implements HasWeekClickedHandlers
{
    final static String[] WEEKS = new String[] {"n/a", "n/a", "n/a", "n/a"};
    final Map<Week, Point> weekToPoint;
    final Map<String, Week> pointToWeek;


    public MonthChartWidget()
    {
        super(WEEKS, Cursor.POINTER);
        this.weekToPoint = new HashMap<Week, Point>();
        this.pointToWeek = new HashMap<String, Week>();
    }


    @Override
    public void updateActivities(final Activities activities)
    {
        // FIXME When there's a "hole" in the weeks the order of the points is not correct
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
                hours = hours(week);
                tooltip = tooltip(week);
                category = String.valueOf(week.getWeek());
                weekToPoint.put(week, point);
                pointToWeek.put(tooltip, week);
            }
            categories.add(category);
            updatePoint(point, hours, tooltip);
        }
        chart.getXAxis().setCategories(categories.toArray(new String[] {}));
    }


    public void updateWeek(final Week week)
    {
        Point point = weekToPoint.get(week);
        if (point != null)
        {
            updatePoint(point, hours(week), tooltip(week));
        }
    }


    double hours(final Week week)
    {
        return week.getMinutes().getTotalHours();
    }


    String tooltip(final Week week)
    {
        return "CW " + week.getWeek() + ": " + FormatUtils.duration(week.getMinutes()) + "<br/>"
                + FormatUtils.dateDuration(week.getStart(), week.getEnd());
    }


    @Override
    public boolean onClick(final PointClickEvent pointClickEvent)
    {
        Week week = null;
        Point point = pointClickEvent.getPoint();
        JSONObject userData = point.getUserData();
        if (userData != null)
        {
            JSONValue value = userData.get("tooltip");
            if (value != null && value.isString() != null)
            {
                week = pointToWeek.get(value.isString().stringValue());
            }
        }
        if (week != null)
        {
            WeekClickedEvent.fire(this, week);
        }
        return false;
    }


    @Override
    public void fireEvent(final GwtEvent<?> event)
    {
        asWidget().fireEvent(event);
    }


    @Override
    public HandlerRegistration addWeekClickedHandler(final WeekClickedHandler handler)
    {
        return asWidget().addHandler(handler, WeekClickedEvent.getType());
    }
}
