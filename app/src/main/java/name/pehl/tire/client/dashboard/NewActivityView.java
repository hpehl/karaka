package name.pehl.tire.client.dashboard;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

/**
 * @author $Author$
 * @version $Date$ $Revision: 90
 *          $
 */
public class NewActivityView extends ViewWithUiHandlers<NewActivityUiHandlers> implements NewActivityPresenter.MyView
{
    interface NewActivityUi extends UiBinder<Widget, NewActivityView>
    {
    }

    private static NewActivityUi uiBinder = GWT.create(NewActivityUi.class);

    private static final long ONE_DAY = 24 * 60 * 60 * 1000;
    private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("dd. MMM");

    @UiField
    InlineHyperlink theDayBeforeYesterday;

    @UiField
    InlineHyperlink yesterday;

    @UiField
    InlineHyperlink today;

    @UiField
    InlineHyperlink calendar;

    private final Widget widget;


    public NewActivityView()
    {
        widget = uiBinder.createAndBindUi(this);
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
        // TODO add/remove css class "selected"
        GWT.log("The day before yesterday selected");
    }


    @UiHandler("yesterday")
    void onYesterdayClicked(ClickEvent event)
    {
        // TODO add/remove css class "selected"
        GWT.log("Yesterday selected");
    }


    @UiHandler("today")
    void onTodayClicked(ClickEvent event)
    {
        // TODO add/remove css class "selected"
        GWT.log("Today selected");
    }


    @UiHandler("calendar")
    void onCalendarClicked(ClickEvent event)
    {
        // TODO Show DatePicker
        // Set selected date as text of hyperlink
        // TODO add/remove css class "selected"
        GWT.log("Calendar selected");
    }
}
