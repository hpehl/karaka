package name.pehl.tire.client.ui;

import name.pehl.tire.shared.model.Time;

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
public class TimeTextBox extends Composite implements HasValue<Time>, IsEditor<LeafValueEditor<Time>>
{
    private Time currentTime;
    private final DateTimeFormat format;
    private final PlaceholderTextBox textBox;


    public TimeTextBox()
    {
        currentTime = new Time();
        format = DateTimeFormat.getFormat("HH:mm");
        textBox = new PlaceholderTextBox();
        initWidget(textBox);
    }


    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Time> handler)
    {
        return addHandler(handler, ValueChangeEvent.getType());
    }


    @Override
    public LeafValueEditor<Time> asEditor()
    {
        return TakesValueEditor.of(this);
    }


    @Override
    public Time getValue()
    {
        return currentTime;
    }


    @Override
    public void setValue(Time value)
    {
        setValue(null, value, false);
    }


    @Override
    public void setValue(Time value, boolean fireEvents)
    {
        setValue(null, value, fireEvents);
    }


    private void setValue(Time oldTime, Time time, boolean fireEvents)
    {
        currentTime = time;
        if (time != null)
        {
            textBox.setText(format.format(time.getDate()));
        }
    }
}
