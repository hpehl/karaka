package name.pehl.tire.client.application;

import static java.lang.Math.max;
import static java.lang.Math.round;

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
    private static final int POPUP_HEIGHT = 20;
    private static final int LABEL_HEIGHT = 20;
    private static final double COLUMN_GAP_PERCENTAGE = .05;
    private static final int MIN_COLUMN_GAP = 5;

    // -------------------------------------------------------- private members

    private final int width;
    private final int height;
    private final String[] weekdays;
    private final int usableHeight;
    private final double columnWidth;
    private final double columnGap;

    private int max;
    private double oneMinute;

    private final Element holder;
    private final JavaScriptObject[] columns;
    private JavaScriptObject raphael;


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

        usableHeight = height - POPUP_HEIGHT - LABEL_HEIGHT;
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
        raphael = initRaphael(holder, width, height);
        for (int i = 0; i < weekdays.length; i++)
        {
            CalendarWeekData cwd = new CalendarWeekData(i);
            String path = path(cwd);
            columns[i] = initColumn(raphael, path);
        }
    }


    private native JavaScriptObject initRaphael(Element holder, int width, int height) /*-{
        return $wnd.Raphael(holder, width, height);
    }-*/;


    private native JavaScriptObject initColumn(JavaScriptObject raphael, String path) /*-{
        return raphael.path(path).attr({stroke: "#3d3d3d", fill: "#3d3d3d"});
    }-*/;


    // ------------------------------------------------------- animate / update

    public void update(boolean animate, CalendarWeekData... data)
    {
        if (raphael != null && columns != null && data != null)
        {
            updateMax(data);
            for (CalendarWeekData cwd : data)
            {
                int index = cwd.getIndex();
                if (index >= 0 && index < columns.length)
                {
                    String path = path(cwd);
                    JavaScriptObject column = columns[cwd.getIndex()];
                    if (animate)
                    {
                        internalAnimate(column, path);
                    }
                    else
                    {
                        internalUpdate(column, path);
                    }
                }
            }
        }
    }


    private native void internalAnimate(JavaScriptObject column, String path) /*-{
        column.animate({path: path}, 1000, ">");
    }-*/;


    private native void internalUpdate(JavaScriptObject column, String path) /*-{

    }-*/;


    // --------------------------------------------------------- helper methods

    private String path(CalendarWeekData cwd)
    {
        StringBuilder path = new StringBuilder();
        long x = round(cwd.getIndex() * (columnWidth + columnGap));
        long y = round(POPUP_HEIGHT + usableHeight);
        path.append("M").append(x).append(",").append(y);
        x += columnWidth;
        path.append("L").append(x).append(",").append(y);
        y -= round(cwd.getMinutes() * oneMinute);
        path.append("L").append(x).append(",").append(y);
        x -= columnWidth;
        path.append("L").append(x).append(",").append(y).append("Z");
        return path.toString();
    }


    private void updateMax(CalendarWeekData... data)
    {
        for (CalendarWeekData cwd : data)
        {
            max = max(max, cwd.getMinutes());
        }
        max += max * .05;
        oneMinute = (double) usableHeight / max;
    }
}
