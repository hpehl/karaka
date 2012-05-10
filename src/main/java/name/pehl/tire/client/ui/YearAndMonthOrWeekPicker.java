package name.pehl.tire.client.ui;

import java.util.SortedSet;

import name.pehl.tire.shared.model.TimeUnit;
import name.pehl.tire.shared.model.Year;
import name.pehl.tire.shared.model.YearAndMonthOrWeek;
import name.pehl.tire.shared.model.Years;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.HasValue;

/**
 * Widget for selecting one {@link YearAndMonthOrWeek} instance. The widget
 * extends {@link SvgPath} so that a list of available years and months or weeks
 * is displayed inside an &lt;ul/&gt; tag when the SVG is clicked. Therefore the
 * click event is swalloed.
 * <p>
 * This widget is read-only, thus calling {@link #setValue(YearAndMonthOrWeek)}
 * or {@link #setValue(YearAndMonthOrWeek, boolean)} will have no effect! Use
 * {@link #setPossibleValues(Years, TimeUnit)} to set the possible values.
 * 
 * @author $Author$
 * @version $Revision$
 */
public class YearAndMonthOrWeekPicker extends SvgPath implements HasValue<YearAndMonthOrWeek>, ClickHandler,
        BlurHandler
{
    /**
     * CSS class for the &lt;ul/&gt; containing the available years and months
     * or weeks
     */
    public static final String CSS_STYLE_NAME = "tire-YearAndMonthOrWeekPicker";

    private final UListElement ul;
    private boolean ulVisible;
    private Years years;
    private TimeUnit unit;
    private YearAndMonthOrWeek currentValue;


    @UiConstructor
    public YearAndMonthOrWeekPicker(int width, int height, String ids, String fills, String strokes, String paths)
    {
        super(width, height, ids, fills, strokes, paths);
        this.ulVisible = false;
        this.ul = Document.get().createULElement();
        this.ul.setClassName(CSS_STYLE_NAME);

        clearList();
        addDomHandler(this, ClickEvent.getType());
        addDomHandler(this, BlurEvent.getType());
    }


    @Override
    public void onClick(ClickEvent event)
    {
        if (!ulVisible)
        {
            showList(event.getClientX(), event.getClientY());
        }
    }


    @Override
    public void onBlur(BlurEvent event)
    {
        hideList();
    }


    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<YearAndMonthOrWeek> handler)
    {
        return addHandler(handler, ValueChangeEvent.getType());
    }


    @Override
    public YearAndMonthOrWeek getValue()
    {
        return currentValue;
    }


    /**
     * This widget is read-only, thus calling this method will have no effect!
     * 
     * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object)
     */
    @Override
    public void setValue(YearAndMonthOrWeek value)
    {
        // nop
    }


    /**
     * This widget is read-only, thus calling this method will have no effect!
     * 
     * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object,
     *      boolean)
     */
    @Override
    public void setValue(YearAndMonthOrWeek value, boolean fireEvents)
    {
        // nop
    }


    public void setPossibleValues(Years years, TimeUnit unit)
    {
        this.years = years;
        this.unit = unit;

        if (this.years != null)
        {
            fillList();
        }
        else
        {
            clearList();
        }
    }


    private void showList(int x, int y)
    {
        Style style = ul.getStyle();
        style.setLeft(x, Unit.PX);
        style.setTop(y, Unit.PX);
        style.setDisplay(Display.BLOCK);
        ulVisible = true;
    }


    private void hideList()
    {
        ul.getStyle().setDisplay(Display.NONE);
        ulVisible = false;
    }


    private void fillList()
    {
        NodeList<Node> childNodes = ul.getChildNodes();
        int length = childNodes.getLength();
        for (int i = 0; i < length; i++)
        {
            childNodes.getItem(i).removeFromParent();
        }
        if (years != null)
        {
            LinkListener linkListener = new LinkListener();
            for (Year year : years.getYears())
            {
                SortedSet<Integer> monthsOrWeeks = null;
                if (unit == TimeUnit.MONTH)
                {
                    monthsOrWeeks = year.getMonths();
                }
                else if (unit == TimeUnit.WEEK)
                {
                    monthsOrWeeks = year.getWeeks();
                }
                if (monthsOrWeeks != null && !monthsOrWeeks.isEmpty())
                {
                    LIElement yearLi = Document.get().createLIElement();
                    yearLi.setInnerText(String.valueOf(year.getYear()));
                    UListElement nestedUl = Document.get().createULElement();
                    for (Integer monthOrWeek : monthsOrWeeks)
                    {
                        LIElement li = Document.get().createLIElement();
                        AnchorElement link = newLink(linkListener, year, monthOrWeek);
                        li.appendChild(link);
                        nestedUl.appendChild(li);
                    }
                    yearLi.appendChild(nestedUl);
                    ul.appendChild(yearLi);
                }
            }
        }
    }


    private AnchorElement newLink(LinkListener linkListener, Year year, Integer monthOrWeek)
    {
        AnchorElement link = Document.get().createAnchorElement();

        String text = String.valueOf(monthOrWeek);
        String rel = String.valueOf(year.getYear()) + "|" + text;
        link.setInnerText(text);
        link.setAttribute("rel", rel);

        Element linkElement = (Element) Element.as(link);
        DOM.sinkEvents(linkElement, Event.ONCLICK);
        DOM.setEventListener(linkElement, linkListener);

        return link;
    }


    private void clearList()
    {
        LIElement li = Document.get().createLIElement();
        li.setInnerText("No data");
        ul.appendChild(li);
    }

    class LinkListener implements EventListener
    {
        @Override
        public void onBrowserEvent(Event event)
        {
            EventTarget target = event.getEventTarget();
            AnchorElement link = (AnchorElement) Element.as(target);
            String yearAndMonthOrWeek = link.getAttribute("rel");
            GWT.log(yearAndMonthOrWeek);
        }
    }
}
