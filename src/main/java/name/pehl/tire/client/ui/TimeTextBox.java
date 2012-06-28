package name.pehl.tire.client.ui;

import name.pehl.tire.client.Defaults;
import name.pehl.tire.shared.model.Time;

import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.editor.client.adapters.TakesValueEditor;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class TimeTextBox extends Composite implements HasValue<Time>, IsEditor<LeafValueEditor<Time>>,
        ValueChangeHandler<String>
{
    private Time currentTime;
    private final Html5TextBox textBox;
    private final HourMinuteParser hourMinuteParser;


    public TimeTextBox()
    {
        this.currentTime = new Time();
        this.textBox = new Html5TextBox();
        this.textBox.addValueChangeHandler(this);
        this.hourMinuteParser = new HourMinuteParser();
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


    public void setPlaceholder(String placeholder)
    {
        textBox.setPlaceholder(placeholder);
    }


    private void setValue(Time oldTime, Time time, boolean fireEvents)
    {
        currentTime = time;
        if (time != null)
        {
            textBox.setText(Defaults.TIME_FORMAT.format(time.getDate()));
        }
    }


    @Override
    @SuppressWarnings("deprecation")
    public void onValueChange(ValueChangeEvent<String> event)
    {
        try
        {
            int[] hm = hourMinuteParser.parse(event.getValue());
            currentTime.getDate().setHours(hm[0]);
            currentTime.getDate().setMinutes(hm[1]);
        }
        catch (IllegalArgumentException e)
        {
            // TODO Error handling
        }
    }
}
