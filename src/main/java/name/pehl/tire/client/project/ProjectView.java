package name.pehl.tire.client.project;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ProjectView extends Composite implements ProjectPresenter.Display
{
    private final TextBox name;
    private final TextBox description;
    private final Button newProjectButton;


    public ProjectView()
    {
        final FlowPanel panel = new FlowPanel();
        initWidget(panel);

        name = new TextBox();
        description = new TextBox();
        newProjectButton = new Button("Go");

        panel.add(name);
        panel.add(description);
        panel.add(newProjectButton);

        reset();
    }


    @Override
    public HasValue<String> getName()
    {
        return name;
    }


    @Override
    public HasValue<String> getDescription()
    {
        return description;
    }


    @Override
    public HasClickHandlers getNewProject()
    {
        return newProjectButton;
    }


    public void reset()
    {
        name.setFocus(true);
        name.selectAll();
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
