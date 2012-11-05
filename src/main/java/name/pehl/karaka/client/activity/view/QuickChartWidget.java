package name.pehl.karaka.client.activity.view;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import name.pehl.karaka.shared.model.Activities;
import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Credits;
import org.moxieapps.gwt.highcharts.client.Exporting;
import org.moxieapps.gwt.highcharts.client.Legend;
import org.moxieapps.gwt.highcharts.client.Point;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.Style;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.ToolTipData;
import org.moxieapps.gwt.highcharts.client.ToolTipFormatter;
import org.moxieapps.gwt.highcharts.client.events.PointClickEvent;
import org.moxieapps.gwt.highcharts.client.events.PointClickEventHandler;
import org.moxieapps.gwt.highcharts.client.labels.DataLabels;
import org.moxieapps.gwt.highcharts.client.plotOptions.ColumnPlotOptions;
import org.moxieapps.gwt.highcharts.client.plotOptions.PlotOptions.Cursor;
import org.moxieapps.gwt.highcharts.client.plotOptions.SeriesPlotOptions;

import static org.moxieapps.gwt.highcharts.client.Series.Type.COLUMN;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-17 21:37:43 +0100 (Fr, 17 Dez 2010) $ $Revision: 102 $
 */
public abstract class QuickChartWidget implements IsWidget, PointClickEventHandler
{
    // -------------------------------------------------------------- constants

    static final String COLOR = "#3d3d3d";
    static final String BACKGROUND_COLOR = "#eaeaea";

    // -------------------------------------------------------- private members
    final Chart chart;
    final Series series;


    // ----------------------------------------------------------- constructors


    protected QuickChartWidget(String[] categories)
    {
        this(categories, Cursor.NONE);
    }


    protected QuickChartWidget(String[] categories, Cursor cursor)
    {
        this.chart = new Chart()
                .setType(COLUMN)
                .setChartTitleText(null)
                .setBackgroundColor(BACKGROUND_COLOR)
                .setLegend(new Legend().setEnabled(false))
                .setExporting(new Exporting().setEnabled(false))
                .setCredits(new Credits().setEnabled(false))
                .setSeriesPlotOptions(
                        new SeriesPlotOptions()
                                .setShadow(false)
                                .setColor(COLOR)
                                .setCursor(cursor)
                                .setDataLabels(
                                        new DataLabels().setStyle(new Style()
                                                .setFont("normal 10px Verdana, sans-serif")))
                                .setPointClickEventHandler(this))
                .setColumnPlotOptions(
                        new ColumnPlotOptions().setBorderWidth(0).setMinPointLength(1).setPointPadding(.001))
                .setToolTip(
                        new ToolTip().setBorderColor(COLOR).setBorderWidth(1)
                                .setStyle(new Style().setFont("normal 10px Verdana, sans-serif"))
                                .setFormatter(new ToolTipFormatter()
                                {
                                    @Override
                                    public String format(ToolTipData toolTipData)
                                    {
                                        String tooltip = null;
                                        JSONObject userData = toolTipData.getPoint().getUserData();
                                        if (userData != null)
                                        {
                                            JSONValue value = userData.get("tooltip");
                                            if (value != null && value.isString() != null)
                                            {
                                                tooltip = value.isString().stringValue();
                                            }
                                        }
                                        return tooltip;
                                    }
                                }));

        this.series = this.chart.createSeries();
        Point[] points = new Point[categories.length];
        for (int i = 0; i < categories.length; i++)
        {
            points[i] = new Point(0);
            JSONObject userData = new JSONObject();
            points[i].setUserData(userData);
        }
        this.series.setPoints(points);
        this.chart.addSeries(series);

        this.chart.getXAxis().setMin(0);
        this.chart.getYAxis().setAxisTitleText(null).setMin(0);
        this.chart.setSize("212px", "250px");
        this.chart.setVisible(false);
    }


    // ----------------------------------------------------------------- update

    public abstract void updateActivities(Activities activities);

    void updatePoint(Point point, double hours, String tooltip)
    {
        JSONObject userData = point.getUserData();
        if (tooltip != null)
        {
            userData.put("tooltip", new JSONString(tooltip));
        }
        else
        {
            userData.put("tooltip", JSONNull.getInstance());
        }
        point.update(hours);
    }


    // --------------------------------------------------------- event handlers

    @Override
    public boolean onClick(PointClickEvent pointClickEvent)
    {
        return false;
    }


    // --------------------------------------------------------- widget methods

    @Override
    public Widget asWidget()
    {
        return chart;
    }
}
