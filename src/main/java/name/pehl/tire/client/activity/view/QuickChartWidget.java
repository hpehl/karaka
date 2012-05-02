package name.pehl.tire.client.activity.view;

import static java.lang.Math.max;
import static java.lang.Math.round;
import name.pehl.tire.shared.model.Activities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-17 21:37:43 +0100 (Fr, 17 Dez 2010) $ $Revision: 102
 *          $
 */
public abstract class QuickChartWidget extends Widget
{
    // -------------------------------------------------------------- constants

    private static final int TITLE_HEIGHT = 30;
    private static final int LEGEND_HEIGHT = 20;
    private static final double COLUMN_GAP_PERCENTAGE = .05;
    private static final int MIN_COLUMN_GAP = 5;
    private static final String COLOR = "#3d3d3d";
    private static final String BACKGROUND_COLOR = "#eaeaea";

    // -------------------------------------------------------- private members

    protected long max;
    protected final int width;
    protected final int height;
    protected final String[] legendTitles;
    protected final int usableHeight;
    protected final double columnWidth;
    protected final double columnGap;
    protected double oneMinute;

    protected final String color;
    protected final String bgColor;
    protected final Element holder;
    protected final JavaScriptObject[] columns;
    protected final JavaScriptObject[] legends;

    protected boolean initialized;
    protected JavaScriptObject raphael;
    protected JavaScriptObject title;


    // ----------------------------------------------------------- constructors

    protected QuickChartWidget(final int width, final int height, final String legendTitles)
    {
        this.width = width;
        this.height = height;
        this.legendTitles = legendTitles.split("[, ]+");
        this.holder = Document.get().createDivElement();
        this.color = COLOR;
        this.bgColor = BACKGROUND_COLOR;

        setElement(holder);
        setWidth(String.valueOf(width));
        setHeight(String.valueOf(height));

        usableHeight = height - TITLE_HEIGHT - LEGEND_HEIGHT;
        oneMinute = usableHeight / 100.0;
        columnGap = max(width * COLUMN_GAP_PERCENTAGE, MIN_COLUMN_GAP);
        int columnCount = this.legendTitles != null && this.legendTitles.length != 0 ? this.legendTitles.length : 1;
        columns = new JavaScriptObject[columnCount];
        legends = new JavaScriptObject[columnCount];
        columnWidth = (width - (this.legendTitles.length - 1) * columnGap) / columnCount;

        // paper and title
        raphael = initRaphael(width, height);
        int x = width / 2;
        int y = TITLE_HEIGHT / 2;
        title = initTitle(x, y);

        // columns and legend
        for (int i = 0; i < this.legendTitles.length; i++)
        {
            String path = path(i, 0);
            columns[i] = initColumn(path);
            x = (int) round(i * (columnWidth + columnGap) + columnWidth / 2);
            y = (int) round(height - LEGEND_HEIGHT / 2.0);
            legends[i] = initLegend(x, y, this.legendTitles[i]);
        }
        initialized = true;
    }


    // ------------------------------------------------------------------- init

    private native JavaScriptObject initRaphael(int width, int height) /*-{
		var holder = this.@name.pehl.tire.client.activity.view.QuickChartWidget::holder;
		return $wnd.Raphael(holder, width, height);
    }-*/;


    private native JavaScriptObject initTitle(int x, int y) /*-{
		var safeThis = this;
		var raphael = safeThis.@name.pehl.tire.client.activity.view.QuickChartWidget::raphael;
		var color = safeThis.@name.pehl.tire.client.activity.view.QuickChartWidget::color;
		var bgColor = safeThis.@name.pehl.tire.client.activity.view.QuickChartWidget::bgColor;
		var text = raphael.text(x, y, "").attr({
			font : "10px Verdana",
			fill : color
		});
		return text;
    }-*/;


    private native JavaScriptObject initColumn(String path) /*-{
		var raphael = this.@name.pehl.tire.client.activity.view.QuickChartWidget::raphael;
		var color = this.@name.pehl.tire.client.activity.view.QuickChartWidget::color;
		return raphael.path(path).attr({
			stroke : color,
			fill : color
		});
    }-*/;


    private native JavaScriptObject initLegend(int x, int y, String text) /*-{
		var raphael = this.@name.pehl.tire.client.activity.view.QuickChartWidget::raphael;
		var color = this.@name.pehl.tire.client.activity.view.QuickChartWidget::color;
		return raphael.text(x, y, text).attr({
			font : "10px Verdana",
			fill : color
		});
    }-*/;


    // ----------------------------------------------------------------- update

    public abstract void update(Activities activities);


    protected native void updateTitle(String newTitle) /*-{
		var title = this.@name.pehl.tire.client.activity.view.QuickChartWidget::title;
		title.attr("text", newTitle);
    }-*/;


    protected native void animateColumn(JavaScriptObject column, String path, String tooltip) /*-{
		column.animate({
			path : path
		}, 1500, ">");
		column.attr("title", tooltip);
    }-*/;


    protected native void updateColumn(JavaScriptObject column, String path, String tooltip) /*-{
		column.attr({
			path : path,
			title : tooltip
		});
    }-*/;


    protected native void updateLegend(JavaScriptObject legend, String newLegend) /*-{
		legend.attr("text", newLegend);
    }-*/;


    // --------------------------------------------------------- helper methods

    protected String path(int index, long minutes)
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
