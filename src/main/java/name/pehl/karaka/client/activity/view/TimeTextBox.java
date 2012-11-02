package name.pehl.karaka.client.activity.view;

import name.pehl.karaka.client.Defaults;
import name.pehl.karaka.client.ui.Html5TextBox;
import name.pehl.karaka.shared.model.Time;

import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.editor.client.adapters.TakesValueEditor;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;

/**
 * TODO
 * <ul>
 * <li>Use &lt;input type="time"/&gt; as underlying input element.
 * <li>Handle am/pm
 * <li>Use a picker like in http://jonthornton.github.com/jquery-timepicker/
 * <li>Better: https://github.com/ctasada/GWT-Eureka
 * </ul>
 * 
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class TimeTextBox extends Composite implements HasValue<Time>, IsEditor<LeafValueEditor<Time>>,
        ValueChangeHandler<String>
{
    private Time currentValue;
    private final Html5TextBox textBox;
    private final TimeParser timeParser;


    public TimeTextBox()
    {
        this.currentValue = new Time();
        this.textBox = new Html5TextBox();
        this.textBox.addValueChangeHandler(this);
        this.timeParser = new TimeParser();
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
        return currentValue;
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


    private void setValue(Time oldValue, Time value, boolean fireEvents)
    {
        currentValue = value;
        if (value != null)
        {
            textBox.setText(Defaults.TIME_FORMAT.format(value.getDate()));
        }
    }


    @Override
    @SuppressWarnings("deprecation")
    public void onValueChange(ValueChangeEvent<String> event)
    {
        try
        {
            int[] hm = timeParser.parse(event.getValue());
            currentValue.getDate().setHours(hm[0]);
            currentValue.getDate().setMinutes(hm[1]);
        }
        catch (IllegalArgumentException e)
        {
            // TODO Error handling
        }
    }
}
