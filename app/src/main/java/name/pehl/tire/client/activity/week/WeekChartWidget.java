package name.pehl.tire.client.activity.week;

import static java.lang.Math.*;
import name.pehl.tire.client.activity.ActivitiesNavigation.Direction;
import name.pehl.tire.client.activity.ActivitiesNavigation.Unit;
import name.pehl.tire.client.activity.ActivitiesNavigationEvent;
import name.pehl.tire.client.activity.day.Day;
import name.pehl.tire.client.ui.UiUtils;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author $Author$
 * @version $Date$ $Revision: 102
 *          $
 */
public class WeekChartWidget extends Widget implements ActivitiesNavigationEvent.HasActivitiesNavigationHandlers
{

    private static final int TITLE_HEIGHT = 30;
    private static final int LEGEND_HEIGHT = 20;
    private static final double COLUMN_GAP_PERCENTAGE = .05;
    private static final int MIN_COLUMN_GAP = 5;
    private static final String COLUMN_COLOR = "#3d3d3d";
    private static final String TEXT_COLOR = "#3d3d3d";
    private static final String BACKGROUND_COLOR = "#eaeaea";

    // -------------------------------------------------------- private members

    private int max;
    private final int width;
    private final int height;
    private final String[] weekdays;
    private final int usableHeight;
    private final double columnWidth;
    private final double columnGap;
    private double oneMinute;
    private Week currentWeek;

    private final Element holder;
    private JavaScriptObject raphael;
    private JavaScriptObject prev;
    private JavaScriptObject title;
    private JavaScriptObject next;
    private final JavaScriptObject[] columns;


    // ----------------------------------------------------------- constructors

    public WeekChartWidget(final int width, final int height, final String[] weekdays)
    {
        this.width = width;
        this.height = height;
        this.weekdays = weekdays;
        this.holder = DOM.createDiv();
        setElement(holder);
        setWidth(String.valueOf(width));
        setHeight(String.valueOf(height));

        usableHeight = height - TITLE_HEIGHT - LEGEND_HEIGHT;
        oneMinute = usableHeight / 100.0;
        columnGap = max(width * COLUMN_GAP_PERCENTAGE, MIN_COLUMN_GAP);
        int columnCount = weekdays != null && weekdays.length != 0 ? weekdays.length : 1;
        columns = new JavaScriptObject[columnCount];
        columnWidth = (width - (weekdays.length - 1) * columnGap) / columnCount;
    }


    // ------------------------------------------------------------------- init

    @Override
    protected void onLoad()
    {
        // paper and title
        raphael = initRaphael(holder, width, height);
        int x = width / 2;
        int y = TITLE_HEIGHT / 2;
        int rectX = (int) round(columnWidth);
        int rectY = 0;
        int rectWidth = (int) round(width - 2.0 * columnWidth);
        int rectHeight = TITLE_HEIGHT;
        title = initTitle(raphael, x, y, " \n ", rectX, rectY, rectWidth, rectHeight);

        // prev
        x = (int) round(columnWidth / 2 + 5);
        y = (int) round(TITLE_HEIGHT / 2.0 - 5);
        StringBuilder prevPath = new StringBuilder().append("M").append(x).append(",").append(y);
        y += 10;
        prevPath.append("L").append(x).append(",").append(y);
        x -= 10;
        y -= 5;
        prevPath.append("L").append(x).append(",").append(y).append("Z");
        rectX = 0;
        rectY = 0;
        rectWidth = (int) round(columnWidth);
        rectHeight = TITLE_HEIGHT;
        prev = initPrev(raphael, prevPath.toString(), rectX, rectY, rectWidth, rectHeight);

        // next
        x = (int) (round((weekdays.length - 1) * (columnWidth + columnGap)) + columnWidth / 2 - 5);
        y = (int) round(TITLE_HEIGHT / 2.0 - 5);
        StringBuilder nextPath = new StringBuilder().append("M").append(x).append(",").append(y);
        y += 10;
        nextPath.append("L").append(x).append(",").append(y);
        x += 10;
        y -= 5;
        nextPath.append("L").append(x).append(",").append(y).append("Z");
        rectX = (int) round((weekdays.length - 1) * (columnWidth + columnGap));
        rectY = 0;
        rectWidth = (int) round(columnWidth);
        rectHeight = TITLE_HEIGHT;
        next = initNext(raphael, nextPath.toString(), rectX, rectY, rectWidth, rectHeight);

        // columns and legend
        for (int i = 0; i < weekdays.length; i++)
        {
            String path = path(i, 0);
            columns[i] = initColumn(raphael, path);
            x = (int) round(i * (columnWidth + columnGap) + columnWidth / 2);
            y = (int) round(height - LEGEND_HEIGHT / 2.0);
            initLegend(raphael, x, y, weekdays[i]);
        }
    }


    private native JavaScriptObject initTitle(JavaScriptObject raphael, int x, int y, String title, int rectX,
            int rectY, int rectWidth, int rectHeight) /*-{
        var safeThis = this;
        var color = @name.pehl.tire.client.activity.week.WeekChartWidget::TEXT_COLOR;
        var bgColor = @name.pehl.tire.client.activity.week.WeekChartWidget::BACKGROUND_COLOR;
        var rect = raphael.rect(rectX, rectY, rectWidth, rectHeight).attr({cursor: "pointer", stroke: "none", fill: bgColor, title: "Current calendarweek"});
        var text = raphael.text(x, y, title).attr({cursor: "pointer", font: "10px Verdana", fill: color, title: "Current calendarweek"});
        rect.node.onclick = text.node.onclick = function() {
        safeThis.@name.pehl.tire.client.activity.week.WeekChartWidget::onCurrent()();
        };
        return text;
    }-*/;


    private native JavaScriptObject initPrev(JavaScriptObject raphael, String path, int rectX, int rectY,
            int rectWidth, int rectHeight) /*-{
        var safeThis = this;
        var color = @name.pehl.tire.client.activity.week.WeekChartWidget::TEXT_COLOR;
        var bgColor = @name.pehl.tire.client.activity.week.WeekChartWidget::BACKGROUND_COLOR;
        var rect = raphael.rect(rectX, rectY, rectWidth, rectHeight).attr({cursor: "pointer", stroke: "none", fill: bgColor, title: "Previous calendarweek"});
        var prev = raphael.path(path).attr({cursor: "pointer", fill: color, stroke: color, opacity: .66, title: "Previous calendarweek"});
        rect.node.onclick = prev.node.onclick = function() {
        safeThis.@name.pehl.tire.client.activity.week.WeekChartWidget::onPrev()();
        };
        rect.node.onmouseover = prev.node.onmouseover = function() {
        prev.attr({opacity: 1.0});
        };
        rect.node.onmouseout = prev.node.onmouseout = function() {
        prev.attr({opacity: .66});
        };
        return prev;
    }-*/;


    private native JavaScriptObject initNext(JavaScriptObject raphael, String path, int rectX, int rectY,
            int rectWidth, int rectHeight) /*-{
        var safeThis = this;
        var color = @name.pehl.tire.client.activity.week.WeekChartWidget::TEXT_COLOR;
        var bgColor = @name.pehl.tire.client.activity.week.WeekChartWidget::BACKGROUND_COLOR;
        var rect = raphael.rect(rectX, rectY, rectWidth, rectHeight).attr({cursor: "pointer", stroke: "none", fill: bgColor, title: "Next calendarweek"});
        var next = raphael.path(path).attr({cursor: "pointer", fill: color, stroke: color, opacity: .66, title: "Next calendarweek"});
        rect.node.onclick = next.node.onclick = function(){
        safeThis.@name.pehl.tire.client.activity.week.WeekChartWidget::onNext()();
        };
        rect.node.onmouseover = next.node.onmouseover = function() {
        next.attr({opacity: 1.0});
        };
        rect.node.onmouseout = next.node.onmouseout = function() {
        next.attr({opacity: .66});
        };
        return next;
    }-*/;


    private native JavaScriptObject initRaphael(Element holder, int width, int height) /*-{
        return $wnd.Raphael(holder, width, height);
    }-*/;


    private native JavaScriptObject initColumn(JavaScriptObject raphael, String path) /*-{
        var color = @name.pehl.tire.client.activity.week.WeekChartWidget::COLUMN_COLOR;
        return raphael.path(path).attr({stroke: color, fill: color});
    }-*/;


    private native void initLegend(JavaScriptObject raphael, int x, int y, String weekday) /*-{
        var color = @name.pehl.tire.client.activity.week.WeekChartWidget::TEXT_COLOR;
        raphael.text(x, y, weekday).attr({font: "10px Verdana", fill: color});
    }-*/;


    // ----------------------------------------------------------------- update

    public void update(Week week, boolean animate)
    {
        if (raphael != null && columns != null && week != null && !week.isEmpty())
        {
            // update title
            StringBuilder value = new StringBuilder();
            value.append("CW ").append(week.getWeek()).append("\n")
                    .append(UiUtils.DATE_FORMAT.format(week.getStart().getDate())).append(" - ")
                    .append(UiUtils.DATE_FORMAT.format(week.getEnd().getDate()));
            internalUpdateTitle(title, value.toString());

            // update max
            max = 0;
            for (Day day : week)
            {
                max = max(max, day.getMinutes());
            }
            max += max * .05;
            oneMinute = (double) usableHeight / max;

            // update columns
            int index = 0;
            for (Day day : week)
            {
                if (index >= 0 && index < columns.length)
                {
                    JavaScriptObject column = columns[index];
                    String path = path(index, day.getMinutes());
                    String date = UiUtils.DATE_FORMAT.format(day.getDate());
                    String hours = UiUtils.NUMBER_FORMAT.format(day.getMinutes() / 60.0);
                    if (animate)
                    {
                        internalAnimate(column, path, date, hours);
                    }
                    else
                    {
                        internalUpdate(column, path, date, hours);
                    }
                }
                index++;
            }
        }
    }


    private native void internalUpdateTitle(JavaScriptObject title, String value) /*-{
        title.attr("text", value);
    }-*/;


    private native void internalAnimate(JavaScriptObject column, String path, String date, String hours) /*-{
        column.animate({path: path}, 1500, ">");
        column.attr("title", date + ": " + hours + "h");
    }-*/;


    private native void internalUpdate(JavaScriptObject column, String path, String date, String hours) /*-{
        column.attr({path: path, title: date + ": " + hours + "h"});
    }-*/;


    // --------------------------------------------------------- event handling

    @Override
    public HandlerRegistration addActivitiesNavigationHandler(
            ActivitiesNavigationEvent.ActivitiesNavigationHandler handler)
    {
        return addHandler(handler, ActivitiesNavigationEvent.getType());
    }


    private void onPrev()
    {
        ActivitiesNavigationEvent.fire(this, Direction.PREV, Unit.WEEK);
    }


    private void onCurrent()
    {
        ActivitiesNavigationEvent.fire(this, Direction.CURRENT, Unit.WEEK);
    }


    private void onNext()
    {
        ActivitiesNavigationEvent.fire(this, Direction.NEXT, Unit.WEEK);
    }


    // --------------------------------------------------------- helper methods

    private String path(int index, int minutes)
    {
        StringBuilder path = new StringBuilder();
        long x = round(index * (columnWidth + columnGap));
        long y = round(TITLE_HEIGHT + usableHeight);
        path.append("M").append(x).append(",").append(y);
        x += columnWidth;
        path.append("L").append(x).append(",").append(y);
        y -= round(minutes * oneMinute);
        path.append("L").append(x).append(",").append(y);
        x -= columnWidth;
        path.append("L").append(x).append(",").append(y).append("Z");
        return path.toString();
    }
}
