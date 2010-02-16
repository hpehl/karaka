package name.pehl.tire.client.application;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * The main view which uses a {@link DockLayoutPanel}.
 * 
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ApplicationView extends Composite implements ApplicationDisplay
{
    DockLayoutPanel applicationPanel;


    public ApplicationView()
    {
        applicationPanel = new DockLayoutPanel(Unit.EM);
        applicationPanel.addNorth(new HTML("Header"), 2);
        applicationPanel.addSouth(new HTML("South"), 2);
        applicationPanel.add(new HTML("Center"));
        initWidget(applicationPanel);
    }


    @Override
    public Widget asWidget()
    {
        return this;
    }


    @Override
    public void startProcessing()
    {
    }


    @Override
    public void stopProcessing()
    {
    }


    /**
     * Empty implementation
     * 
     * @param widget
     * @see net.customware.gwt.presenter.client.widget.WidgetContainerDisplay#addWidget(com.google.gwt.user.client.ui.Widget)
     */
    @Override
    public void addWidget(Widget widget)
    {
    }


    /**
     * Removes the specified widget from the center of the dock
     * 
     * @param widget
     * @see net.customware.gwt.presenter.client.widget.WidgetContainerDisplay#removeWidget(com.google.gwt.user.client.ui.Widget)
     */
    @Override
    public void removeWidget(Widget widget)
    {
        applicationPanel.remove(widget);
    }


    /**
     * Adds the specified widget to the center of the dock
     * 
     * @param widget
     * @see net.customware.gwt.presenter.client.widget.WidgetContainerDisplay#showWidget(com.google.gwt.user.client.ui.Widget)
     */
    @Override
    public void showWidget(Widget widget)
    {
        applicationPanel.add(widget);
    }
}
