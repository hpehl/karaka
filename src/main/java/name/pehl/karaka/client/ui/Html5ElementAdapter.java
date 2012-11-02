package name.pehl.karaka.client.ui;

import com.google.gwt.dom.client.Element;

class Html5ElementAdapter
{
    private final Element element;


    Html5ElementAdapter(Element element)
    {
        super();
        this.element = element;
    }


    void setPlaceholder(String placeholder)
    {
        element.setAttribute("placeholder", placeholder);
    }


    void setAutofocus(String placeholder)
    {
        element.setAttribute("autofocus", placeholder);
    }
}
