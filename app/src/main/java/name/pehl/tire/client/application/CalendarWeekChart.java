package name.pehl.tire.client.application;

import static java.lang.Math.max;
import static java.lang.Math.round;
import name.pehl.tire.client.ui.UiUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author $Author$
 * @version $Date$ $Revision: 102
 *          $
 */
public class CalendarWeekChart extends Widget
{
    private static final int TITLE_HEIGHT = 30;
    private static final int LEGEND_HEIGHT = 20;
    private static final double COLUMN_GAP_PERCENTAGE = .05;
    private static final int MIN_COLUMN_GAP = 5;
    private static final String COLUMN_COLOR = "#3d3d3d";
    private static final String TEXT_COLOR = "#3d3d3d";

    // -------------------------------------------------------- private members

    private int max;
    private final int width;
    private final int height;
    private final String[] weekdays;
    private final int usableHeight;
    private final double columnWidth;
    private final double columnGap;
    private double oneMinute;

    private final Element holder;
    private JavaScriptObject raphael;
    private JavaScriptObject prev;
    private JavaScriptObject title;
    private JavaScriptObject next;
    private final JavaScriptObject[] columns;


    // ----------------------------------------------------------- constructors

    public CalendarWeekChart(final int width, final int height, final String[] weekdays)
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
        title = initTitle(raphael, x, y, " \n ");

        // prev
        x = (int) (columnWidth / 2) + 5;
        y = TITLE_HEIGHT / 2 - 5;
        StringBuilder prevPath = new StringBuilder().append("M").append(x).append(",").append(y);
        y += 10;
        prevPath.append("L").append(x).append(",").append(y);
        x -= 10;
        y -= 5;
        prevPath.append("L").append(x).append(",").append(y).append("Z");
        prev = initPrev(raphael, prevPath.toString());

        // next
        x = (int) (round((weekdays.length - 1) * (columnWidth + columnGap)) + columnWidth / 2 - 5);
        y = TITLE_HEIGHT / 2 - 5;
        StringBuilder nextPath = new StringBuilder().append("M").append(x).append(",").append(y);
        y += 10;
        nextPath.append("L").append(x).append(",").append(y);
        x += 10;
        y -= 5;
        nextPath.append("L").append(x).append(",").append(y).append("Z");
        next = initNext(raphael, nextPath.toString());

        // columns and legend
        for (int i = 0; i < weekdays.length; i++)
        {
            String path = path(i, 0);
            columns[i] = initColumn(raphael, path);
            x = (int) round(i * (columnWidth + columnGap) + columnWidth / 2);
            y = height - LEGEND_HEIGHT / 2;
            initLegend(raphael, x, y, weekdays[i]);
        }
    }


    private native JavaScriptObject initTitle(JavaScriptObject raphael, int x, int y, String title) /*-{
        var color = @name.pehl.tire.client.application.CalendarWeekChart::TEXT_COLOR;
        return raphael.text(x, y, title).attr({font: "10px Verdana", fill: color});
    }-*/;


    private native JavaScriptObject initPrev(JavaScriptObject raphael, String path) /*-{
        var color = @name.pehl.tire.client.application.CalendarWeekChart::TEXT_COLOR;
        var prev = raphael.path(path).attr({fill: color, stroke: color, opacity: .66});
        prev.mouseover(function (event) {
        this.attr({opacity: 1.0});
        });
        prev.mouseout(function (event) {
        this.attr({opacity: .66});
        });
        prev.click(function (event) {
        this.@name.pehl.tire.client.application.CalendarWeekChart::onPrev();
        });
        return prev;
    }-*/;


    private native JavaScriptObject initNext(JavaScriptObject raphael, String path) /*-{
        var color = @name.pehl.tire.client.application.CalendarWeekChart::TEXT_COLOR;
        var next = raphael.path(path).attr({fill: color, stroke: color, opacity: .66});
        next.mouseover(function (event) {
        this.attr({opacity: 1.0});
        });
        next.mouseout(function (event) {
        this.attr({opacity: .66});
        });
        next.click(function (event) {
        this.@name.pehl.tire.client.application.CalendarWeekChart::onNext();
        });
        return next;
    }-*/;


    private native JavaScriptObject initRaphael(Element holder, int width, int height) /*-{
        return $wnd.Raphael(holder, width, height);
    }-*/;


    private native JavaScriptObject initColumn(JavaScriptObject raphael, String path) /*-{
        var color = @name.pehl.tire.client.application.CalendarWeekChart::COLUMN_COLOR;
        return raphael.path(path).attr({stroke: color, fill: color});
    }-*/;


    private native void initLegend(JavaScriptObject raphael, int x, int y, String weekday) /*-{
        var color = @name.pehl.tire.client.application.CalendarWeekChart::TEXT_COLOR;
        raphael.text(x, y, weekday).attr({font: "10px Verdana", fill: color});
    }-*/;


    // ----------------------------------------------------------------- update

    public void update(CalendarWeekData cwd, boolean animate)
    {
        if (raphael != null && columns != null && cwd != null)
        {
            // update title
            StringBuilder value = new StringBuilder();
            value.append("CW ").append(cwd.getCalendarWeek()).append("\n")
                    .append(UiUtils.DATE_FORMAT.format(cwd.getStart())).append(" - ")
                    .append(UiUtils.DATE_FORMAT.format(cwd.getEnd()));
            internalUpdateTitle(title, value.toString());

            // update max
            for (int minutes : cwd)
            {
                max = max(max, minutes);
            }
            max += max * .05;
            oneMinute = (double) usableHeight / max;

            // update columns
            int index = 0;
            for (int minutes : cwd)
            {
                if (index >= 0 && index < columns.length)
                {
                    JavaScriptObject column = columns[index];
                    String path = path(index, minutes);
                    if (animate)
                    {
                        internalAnimate(column, path);
                    }
                    else
                    {
                        internalUpdate(column, path);
                    }
                }
                index++;
            }
        }
    }


    private native void internalUpdateTitle(JavaScriptObject title, String value) /*-{
        title.attr("text", value);
    }-*/;


    private native void internalAnimate(JavaScriptObject column, String path) /*-{
        column.animate({path: path}, 1500, ">");
    }-*/;


    private native void internalUpdate(JavaScriptObject column, String path) /*-{
        column.attr("path", path);
    }-*/;


    // ------------------------------------------------------------ prev / next

    private void onPrev()
    {
        GWT.log("Previous calendarweek");
    }


    private void onNext()
    {
        GWT.log("Next calendarweek");
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
