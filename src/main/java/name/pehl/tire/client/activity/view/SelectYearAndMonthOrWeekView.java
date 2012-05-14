package name.pehl.tire.client.activity.view;

import java.util.SortedSet;

import name.pehl.tire.client.activity.presenter.SelectYearAndMonthOrWeekPresenter;
import name.pehl.tire.client.ui.EscapablePopupPanel;
import name.pehl.tire.shared.model.TimeUnit;
import name.pehl.tire.shared.model.Year;
import name.pehl.tire.shared.model.Years;

import com.google.gwt.core.client.GWT;
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
import com.gwtplatform.mvp.client.PopupViewImpl;

public class SelectYearAndMonthOrWeekView extends PopupViewImpl implements SelectYearAndMonthOrWeekPresenter.MyView
{
    public interface Binder extends UiBinder<EscapablePopupPanel, SelectYearAndMonthOrWeekView>
    {
    }

    private final EscapablePopupPanel popupPanel;
    @UiField UListElement list;


    @Inject
    public SelectYearAndMonthOrWeekView(final EventBus eventBus, final Binder binder)
    {
        super(eventBus);
        this.popupPanel = binder.createAndBindUi(this);
        setAutoHideOnNavigationEventEnabled(true);
    }


    @Override
    public Widget asWidget()
    {
        return popupPanel;
    }


    @Override
    public void updateYears(Years years, TimeUnit unit)
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
                    list.appendChild(yearLi);
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
            GWT.log(yearAndMonthOrWeek);
        }
    }
}
