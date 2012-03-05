package name.pehl.tire.client.ui;

import com.google.gwt.dom.client.InputElement;
import com.google.gwt.user.client.ui.TextBox;

/**
 * A TextBox with support for the new HTML5 <a href=
 * "http://www.whatwg.org/specs/web-apps/current-work/multipage/common-input-element-attributes.html#attr-input-placeholder"
 * >placeholder</a> attribute.
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-11 10:53:09 +0100 (Do, 11. Nov 2010) $ $Revision: 102
 *          $
 */
public class PlaceholderTextBox extends TextBox
{
    public void setPlaceholder(String placeholder)
    {
        InputElement inputElement = getElement().cast();
        inputElement.setAttribute("placeholder", placeholder);
    }
}