package name.pehl.tire.client.ui;

import name.pehl.tire.shared.model.TimeUnit;
import name.pehl.tire.shared.model.Year;
import name.pehl.tire.shared.model.YearAndMonthOrWeek;
import name.pehl.tire.shared.model.Years;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

/**
 * Widget for selecting one {@link YearAndMonthOrWeek} instance. This widget is
 * read-only, thus calling {@link #setValue(YearAndMonthOrWeek)} or
 * {@link #setValue(YearAndMonthOrWeek, boolean)} will have no effect!
 * <p>
 * Use {@link #setPossibleValues(Years, TimeUnit)} to set the possible values.
 * 
 * @author $Author$
 * @version $Revision$
 */
public class YearAndMonthOrWeekPicker extends Widget implements HasValue<YearAndMonthOrWeek>
{
    public static final String CSS_STYLE_NAME = "tire-YearAndMonthOrWeekPicker";

    private final UListElement ul;
    private Years years;
    private TimeUnit unit;
    private YearAndMonthOrWeek currentValue;


    public YearAndMonthOrWeekPicker()
    {
        ul = Document.get().createULElement();
        setElement(ul);
        setStyleName(CSS_STYLE_NAME);
        empty();
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
            reset();
        }
        else
        {
            empty();
        }
    }


    private void reset()
    {
        NodeList<Node> childNodes = ul.getChildNodes();
        int length = childNodes.getLength();
        for (int i = 0; i < length; i++)
        {
            childNodes.getItem(i).removeFromParent();
        }
        if (years != null)
        {
            for (Year year : years.getYears())
            {
                LIElement yearLi = Document.get().createLIElement();
                yearLi.setInnerText(String.valueOf(year.getYear()));
                UListElement nestedUl = Document.get().createULElement();
                if (unit == TimeUnit.MONTH)
                {
                    for (Integer month : year.getMonths())
                    {
                        LIElement monthLi = Document.get().createLIElement();
                        monthLi.setInnerText(String.valueOf(month));
                        nestedUl.appendChild(monthLi);
                    }
                }
                else if (unit == TimeUnit.WEEK)
                {
                    for (Integer week : year.getWeeks())
                    {
                        LIElement monthLi = Document.get().createLIElement();
                        monthLi.setInnerText(String.valueOf(week));
                        nestedUl.appendChild(monthLi);
                    }
                }
                yearLi.appendChild(nestedUl);
                ul.appendChild(yearLi);
            }
        }
    }


    private void empty()
    {
        LIElement li = Document.get().createLIElement();
        li.setInnerText("No data");
        ul.appendChild(li);
    }
}
