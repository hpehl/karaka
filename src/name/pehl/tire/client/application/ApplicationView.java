package name.pehl.tire.client.application;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
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
}
