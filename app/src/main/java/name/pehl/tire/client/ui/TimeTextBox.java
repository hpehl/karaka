package name.pehl.tire.client.ui;

import java.util.Date;

import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.editor.client.adapters.TakesValueEditor;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class TimeTextBox extends Composite implements HasValue<Date>, IsEditor<LeafValueEditor<Date>>
{
    private Date currentDate;
    private final DateTimeFormat format;
    private final PlaceholderTextBox textBox;


    public TimeTextBox()
    {
        currentDate = new Date();
        format = DateTimeFormat.getFormat("HH:mm");
        textBox = new PlaceholderTextBox();
        initWidget(textBox);
    }


    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Date> handler)
    {
        return addHandler(handler, ValueChangeEvent.getType());
    }


    @Override
    public LeafValueEditor<Date> asEditor()
    {
        return TakesValueEditor.of(this);
    }


    @Override
    public Date getValue()
    {
        return currentDate;
    }


    @Override
    public void setValue(Date value)
    {
        setValue(null, value, false);
    }


    @Override
    public void setValue(Date value, boolean fireEvents)
    {
        setValue(null, value, fireEvents);
    }


    private void setValue(Date oldDate, Date date, boolean fireEvents)
    {
        currentDate = date;
        textBox.setText(format.format(date));
    }
}
