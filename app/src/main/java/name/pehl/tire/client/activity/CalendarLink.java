package name.pehl.tire.client.activity;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

/**
 * @author $Author$
 * @version $Date$ $Revision: 99
 *          $
 */
public class CalendarLink extends Composite implements HasClickHandlers, HasValueChangeHandlers<Date>, HasValue<Date>,
        HasText
{
    public static final String DEFAULT_STYLENAME = "tire-Calendar";
    private static final DateTimeFormat DEFAULT_FORMAT = DateTimeFormat.getFormat("dd.MM.yyyy");
    private final Anchor anchor;
    private final PopupPanel popup;
    private final DatePicker picker;


    public CalendarLink()
    {
        anchor = new Anchor();
        picker = new DatePicker();
        popup = new PopupPanel(true);
        popup.addAutoHidePartner(anchor.getElement());
        popup.setStyleName("tire-calendarPopup");
        popup.setWidget(picker);

        initWidget(anchor);
        setStyleName(DEFAULT_STYLENAME);

        CalendarHandler handler = new CalendarHandler();
        addClickHandler(handler);
        addValueChangeHandler(handler);
        popup.addCloseHandler(handler);
    }


    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler)
    {
        return anchor.addClickHandler(handler);
    }


    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Date> handler)
    {
        return picker.addValueChangeHandler(handler);
    }


    @Override
    public Date getValue()
    {
        return picker.getValue();
    }


    @Override
    public void setValue(Date value)
    {
        picker.setValue(value);
    }


    @Override
    public void setValue(Date value, boolean fireEvents)
    {
        picker.setValue(value, fireEvents);
    }


    @Override
    public String getText()
    {
        return anchor.getText();
    }


    @Override
    public void setText(String text)
    {
        anchor.setText(text);
    }


    public void reset()
    {
        setValue(null);
        setText("calendar");
    }


    private void updateLabel(Date date)
    {
        if (date != null)
        {
            setText(DEFAULT_FORMAT.format(date));
        }
    }

    private class CalendarHandler implements ValueChangeHandler<Date>, ClickHandler, CloseHandler<PopupPanel>
    {
        @Override
        public void onClick(ClickEvent event)
        {
            Date current = getValue();
            if (current == null)
            {
                current = new Date();
            }
            picker.setCurrentMonth(current);
            popup.showRelativeTo(CalendarLink.this);
        }


        @Override
        public void onClose(CloseEvent<PopupPanel> event)
        {
            updateLabel(getValue());
        }


        @Override
        public void onValueChange(ValueChangeEvent<Date> event)
        {
            popup.hide();
        }
    }
}
