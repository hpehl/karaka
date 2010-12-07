package name.pehl.tire.client.activity;

import java.util.Date;

import name.pehl.tire.client.resources.Resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

/**
 * @author $Author$
 * @version $Date$ $Revision: 102
 *          $
 */
public class NewActivityView extends ViewWithUiHandlers<NewActivityUiHandlers> implements NewActivityPresenter.MyView
{
    private static final long ONE_DAY = 86400000l;
    private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("dd. MMM");

    // @formatter:off
    interface NewActivityUi extends UiBinder<Widget, NewActivityView> {}
    private static NewActivityUi uiBinder = GWT.create(NewActivityUi.class);

    @UiField InlineHyperlink theDayBeforeYesterday;
    @UiField InlineHyperlink yesterday;
    @UiField InlineHyperlink today;
    @UiField CalendarLink calendar;
    // @formatter:on

    private final Widget widget;
    private final Resources resources;


    @Inject
    public NewActivityView(final Resources resources)
    {
        this.resources = resources;
        this.resources.newActivity().ensureInjected();
        this.widget = uiBinder.createAndBindUi(this);
        this.today.addStyleName(resources.newActivity().selected());

        long time = new Date().getTime() - ONE_DAY;
        yesterday.setText(DATE_FORMAT.format(new Date(time)));
        time -= ONE_DAY;
        theDayBeforeYesterday.setText(DATE_FORMAT.format(new Date(time)));
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    @UiHandler("theDayBeforeYesterday")
    void onTheDayBeforeYesterdayClicked(ClickEvent event)
    {
        GWT.log("The day before yesterday selected");
        theDayBeforeYesterday.addStyleName(resources.newActivity().selected());
        yesterday.removeStyleName(resources.newActivity().selected());
        today.removeStyleName(resources.newActivity().selected());
        calendar.removeStyleName(resources.newActivity().selected());
        calendar.reset();
        if (getUiHandlers() != null)
        {
            Date date = new Date(System.currentTimeMillis() - 2 * ONE_DAY);
            getUiHandlers().onSelectDay(date);
        }
    }


    @UiHandler("yesterday")
    void onYesterdayClicked(ClickEvent event)
    {
        GWT.log("Yesterday selected");
        theDayBeforeYesterday.removeStyleName(resources.newActivity().selected());
        yesterday.addStyleName(resources.newActivity().selected());
        today.removeStyleName(resources.newActivity().selected());
        calendar.removeStyleName(resources.newActivity().selected());
        calendar.reset();
        if (getUiHandlers() != null)
        {
            Date date = new Date(System.currentTimeMillis() - ONE_DAY);
            getUiHandlers().onSelectDay(date);
        }
    }


    @UiHandler("today")
    void onTodayClicked(ClickEvent event)
    {
        GWT.log("Today selected");
        theDayBeforeYesterday.removeStyleName(resources.newActivity().selected());
        yesterday.removeStyleName(resources.newActivity().selected());
        today.addStyleName(resources.newActivity().selected());
        calendar.removeStyleName(resources.newActivity().selected());
        calendar.reset();
        if (getUiHandlers() != null)
        {
            getUiHandlers().onSelectDay(new Date());
        }
    }


    @UiHandler("calendar")
    void onCalendarClicked(ClickEvent event)
    {
        theDayBeforeYesterday.removeStyleName(resources.newActivity().selected());
        yesterday.removeStyleName(resources.newActivity().selected());
        today.removeStyleName(resources.newActivity().selected());
        calendar.addStyleName(resources.newActivity().selected());
    }


    @UiHandler("calendar")
    void onCalendarChanged(ValueChangeEvent<Date> event)
    {
        GWT.log(event.getValue() + " selected");
        if (getUiHandlers() != null)
        {
            getUiHandlers().onSelectDay(event.getValue());
        }
    }
}
