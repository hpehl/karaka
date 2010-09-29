package name.pehl.tire.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.TextBox;

/**
 * A TextBox which shows a {@linkplain #getDescriptiveText() descriptive text}
 * when the text box is empty. <h3>CSS Style Rules</h3>
 * <ul>
 * <li>.tire-DescriptiveTextBox { primary style }
 * <li>.tire-DescriptiveTextBox-empty { dependent style set when the text box is
 * empty }
 * </ul>
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class DescriptiveTextBox extends TextBox implements FocusHandler, BlurHandler
{
    public static final String DEFAULT_STYLENAME = "tire-DescriptiveTextBox";
    private String descriptiveText;


    public DescriptiveTextBox()
    {
        setStyleName(DEFAULT_STYLENAME);
        addBlurHandler(this);
        addFocusHandler(this);
    }


    public DescriptiveTextBox(Element element)
    {
        super(element);
    }


    public String getDescriptiveText()
    {
        return descriptiveText;
    }


    public void setDescriptiveText(String descriptiveText)
    {
        this.descriptiveText = descriptiveText;
        showDescription();
    }


    private void showDescription()
    {
        if (getDescriptiveText() != null)
        {
            if (UiUtils.isEmpty(this))
            {
                addStyleDependentName("empty");
                setText(descriptiveText);
            }
        }
    }


    @Override
    public void onFocus(FocusEvent event)
    {
        if (getDescriptiveText() != null)
        {
            String text = getText();
            if (text != null && text.equals(getDescriptiveText()))
            {
                setText(null);
            }
            removeStyleDependentName("empty");
        }
    }


    @Override
    public void onBlur(BlurEvent event)
    {
        showDescription();
    }
}
