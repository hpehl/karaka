package name.pehl.karaka.client.activity.view;

import name.pehl.karaka.client.activity.view.DurationParser.ParseException;
import name.pehl.karaka.client.ui.FormatUtils;
import name.pehl.karaka.client.ui.Html5TextBox;
import name.pehl.karaka.shared.model.Duration;

import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.editor.client.adapters.TakesValueEditor;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;

public class DurationTextBox extends Composite implements HasValue<Duration>, IsEditor<LeafValueEditor<Duration>>,
        ValueChangeHandler<String>, HasKeyUpHandlers
{
    private Duration currentValue;
    private final Html5TextBox textBox;
    private final DurationParser durationParser;


    public DurationTextBox()
    {
        this.currentValue = Duration.ZERO;
        this.textBox = new Html5TextBox();
        this.textBox.addValueChangeHandler(this);
        this.durationParser = new DurationParser();
        initWidget(textBox);
    }


    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Duration> handler)
    {
        return addHandler(handler, ValueChangeEvent.getType());
    }


    @Override
    public HandlerRegistration addKeyUpHandler(KeyUpHandler handler)
    {
        return textBox.addKeyUpHandler(handler);
    }


    @Override
    public LeafValueEditor<Duration> asEditor()
    {
        return TakesValueEditor.of(this);
    }


    @Override
    public Duration getValue()
    {
        return currentValue;
    }


    @Override
    public void setValue(Duration value)
    {
        setValue(null, value, false);
    }


    @Override
    public void setValue(Duration value, boolean fireEvents)
    {
        setValue(null, value, fireEvents);
    }


    public void setPlaceholder(String placeholder)
    {
        textBox.setPlaceholder(placeholder);
    }


    private void setValue(Duration oldValue, Duration value, boolean fireEvents)
    {
        currentValue = value;
        if (value != null)
        {
            textBox.setText(FormatUtils.duration(value));
        }
    }


    @Override
    public void onValueChange(ValueChangeEvent<String> event)
    {
        try
        {
            Duration d = durationParser.parse(event.getValue());
            currentValue = d;
            ValueChangeEvent.fire(this, currentValue);
        }
        catch (ParseException e)
        {
            // TODO Error handling
        }
    }

    public void clear()
    {
        textBox.setText("");
    }
}
