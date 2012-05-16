package name.pehl.tire.client.activity.view;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;

import name.pehl.tire.client.activity.presenter.SelectYearAndMonthOrWeekPresenter;
import name.pehl.tire.client.activity.presenter.SelectYearAndMonthOrWeekUiHandlers;
import name.pehl.tire.client.resources.I18n;
import name.pehl.tire.client.ui.EscapablePopupPanel;
import name.pehl.tire.shared.model.TimeUnit;
import name.pehl.tire.shared.model.Year;
import name.pehl.tire.shared.model.YearAndMonthOrWeek;
import name.pehl.tire.shared.model.Years;

import com.google.common.collect.Ordering;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

import static java.util.logging.Level.SEVERE;
import static name.pehl.tire.shared.model.TimeUnit.MONTH;

public class SelectYearAndMonthOrWeekView extends PopupViewWithUiHandlers<SelectYearAndMonthOrWeekUiHandlers> implements
        SelectYearAndMonthOrWeekPresenter.MyView
{
    public interface Binder extends UiBinder<EscapablePopupPanel, SelectYearAndMonthOrWeekView>
    {
    }

    private static final Logger logger = Logger.getLogger(SelectYearAndMonthOrWeekView.class.getName());

    private final EscapablePopupPanel popupPanel;
    private final I18n i18n;
    private TimeUnit unit;
    @UiField UListElement list;


    @Inject
    public SelectYearAndMonthOrWeekView(final EventBus eventBus, final Binder binder, final I18n i18n)
    {
        super(eventBus);
        this.popupPanel = binder.createAndBindUi(this);
        this.i18n = i18n;
        LIElement noDataLi = Document.get().createLIElement();
        noDataLi.setInnerText("Loading...");
        this.list.appendChild(noDataLi);
        setAutoHideOnNavigationEventEnabled(true);
    }


    @Override
    public Widget asWidget()
    {
        return popupPanel;
    }


    @Override
    public void setUnit(TimeUnit unit)
    {
        this.unit = unit;
    }


    @Override
    public void updateYears(Years years)
    {
        if (years != null)
        {
            // clear list
            NodeList<Node> childNodes = list.getChildNodes();
            int length = childNodes.getLength();
            for (int i = 0; i < length; i++)
            {
                childNodes.getItem(i).removeFromParent();
            }

            // fill list
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
                    SortedSet<Integer> reversed = new TreeSet<Integer>(Ordering.natural().reverse());
                    reversed.addAll(monthsOrWeeks);
                    LIElement yearLi = Document.get().createLIElement();
                    yearLi.setInnerText(String.valueOf(year.getYear()));
                    UListElement nestedUl = Document.get().createULElement();
                    for (Integer monthOrWeek : reversed)
                    {
                        LIElement li = Document.get().createLIElement();
                        AnchorElement link = newLink(linkListener, year, monthOrWeek, unit);
                        li.appendChild(link);
                        nestedUl.appendChild(li);
                    }
                    yearLi.appendChild(nestedUl);
                    list.appendChild(yearLi);
                }
            }
        }
    }


    private AnchorElement newLink(LinkListener linkListener, Year year, Integer monthOrWeek, TimeUnit unit)
    {
        AnchorElement link = Document.get().createAnchorElement();
        String text;
        if (unit == MONTH)
        {
            text = i18n.enums().getString("month_" + monthOrWeek);
        }
        else
        {
            text = String.valueOf(monthOrWeek);
        }
        String rel = String.valueOf(year.getYear()) + "|" + monthOrWeek;
        link.setInnerText(text);
        link.setAttribute("rel", rel);
        link.setAttribute("href", "#");

        Element linkElement = (Element) Element.as(link);
        DOM.sinkEvents(linkElement, Event.ONCLICK);
        DOM.setEventListener(linkElement, linkListener);

        return link;
    }

    class LinkListener implements EventListener
    {
        @Override
        public void onBrowserEvent(Event event)
        {
            EventTarget target = event.getEventTarget();
            AnchorElement link = (AnchorElement) Element.as(target);
            String yearAndMonthOrWeek = link.getAttribute("rel");
            String[] parts = yearAndMonthOrWeek.split("\\|");
            if (parts != null && parts.length == 2)
            {
                try
                {
                    int year = Integer.parseInt(parts[0]);
                    int monthOrWeek = Integer.parseInt(parts[1]);
                    YearAndMonthOrWeek result = new YearAndMonthOrWeek();
                    result.setYear(year);
                    result.setMonthOrWeek(monthOrWeek);
                    result.setUnit(unit);
                    if (getUiHandlers() != null)
                    {
                        getUiHandlers().onSelectYearAndMonthOrWeek(result);
                    }
                }
                catch (NumberFormatException e)
                {
                    logger.log(SEVERE, "Cannot parse " + yearAndMonthOrWeek
                            + ": Expected format <yyyy|mm> or <yyyy|cw>.");
                }
            }
        }
    }
}
