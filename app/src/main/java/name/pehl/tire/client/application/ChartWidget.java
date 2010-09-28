package name.pehl.tire.client.application;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class ChartWidget extends Widget
{
    private String width;
    private String height;
    private final Element holder;


    public ChartWidget()
    {
        holder = DOM.createDiv();
        setElement(holder);
    }


    @Override
    public void setWidth(String width)
    {
        this.width = width;
        super.setWidth(width);
    }


    @Override
    public void setHeight(String height)
    {
        this.height = height;
        super.setHeight(height);
    }


    public void setHours(double... hours)
    {
        double[] hoursToPass = new double[5];
        if (hours != null)
        {
            for (int i = 0; i < hoursToPass.length; i++)
            {
                if (i < hours.length)
                {
                    hoursToPass[i] = hours[i];
                }
                else
                {
                    hoursToPass[i] = 0;
                }
            }
        }
        createChart(holder, width, height, hoursToPass[0], hoursToPass[1], hoursToPass[2], hoursToPass[3],
                hoursToPass[4]);
    }


    private native void createChart(com.google.gwt.user.client.Element element, String width, String height,
            double hours0, double hours1, double hours2, double hours3, double hours4) /*-{
        var values = [hours0, hours1, hours2, hours3, hours4];
        var days = ["Mo", "Di", "Mi", "Do", "Fr"];
        var r = $wnd.Raphael(element, width, height, 
        fin = function () 
        {
        this.flag = r.g.popup(this.bar.x, this.bar.y, this.bar.value || "0").insertBefore(this);
        },
        fout = function () 
        {
        this.flag.animate({opacity: 0}, 300, function () {this.remove();});
        });
        r.g.colors = ["#3d3d3d"];
        r.g.txtattr.font = "12px Verdana, sans-serif";
        var c = r.g.barchart(0, 10, 202, 210, [values]).hover(fin, fout).label([days], true);
    }-*/;
}
