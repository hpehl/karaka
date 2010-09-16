package name.pehl.tire.client.activity;

import java.util.Date;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ActivityView extends Composite implements ActivityDisplay
{
    private final TextBox name;
    private final TextBox description;
    private final DatePicker start;
    private final DatePicker end;
    private final CheckBox pause;
    private final Button newButton;
    private final Button editButton;
    private final Button deleteButton;


    public ActivityView()
    {
        name = new TextBox();
        description = new TextBox();
        start = new DatePicker();
        end = new DatePicker();
        pause = new CheckBox();
        final FlowPanel dataPanel = new FlowPanel();
        dataPanel.add(name);
        dataPanel.add(description);
        dataPanel.add(start);
        dataPanel.add(end);
        dataPanel.add(pause);

        newButton = new Button("New");
        editButton = new Button("Edit");
        deleteButton = new Button("Delete");
        final FlowPanel controlPanel = new FlowPanel();
        controlPanel.add(newButton);
        controlPanel.add(editButton);
        controlPanel.add(deleteButton);

        final VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.add(dataPanel);
        mainPanel.add(controlPanel);
        initWidget(mainPanel);
    }


    /**
     * @return
     * @see name.pehl.tire.client.activity.ActivityDisplay#getEnd()
     */
    @Override
    public HasValue<Date> getEnd()
    {
        return end;
    }


    /**
     * @return
     * @see name.pehl.tire.client.activity.ActivityDisplay#getStart()
     */
    @Override
    public HasValue<Date> getStart()
    {
        return start;
    }


    /**
     * @return
     * @see name.pehl.tire.client.activity.ActivityDisplay#isPause()
     */
    @Override
    public HasValue<Boolean> isPause()
    {
        return pause;
    }


    /**
     * @param duration
     * @see name.pehl.tire.client.activity.ActivityDisplay#setDuration(long)
     */
    @Override
    public void setDuration(long duration)
    {
    }


    /**
     * @return
     * @see name.pehl.tire.client.mvp.DescriptiveDisplay#getDescription()
     */
    @Override
    public HasValue<String> getDescription()
    {
        return description;
    }


    /**
     * @return
     * @see name.pehl.tire.client.mvp.NamedDisplay#getName()
     */
    @Override
    public HasValue<String> getName()
    {
        return name;
    }


    /**
     * @return
     * @see net.customware.gwt.presenter.client.widget.WidgetDisplay#asWidget()
     */
    @Override
    public Widget asWidget()
    {
        return this;
    }


    /**
     * @see net.customware.gwt.presenter.client.Display#startProcessing()
     */
    @Override
    public void startProcessing()
    {
    }


    /**
     * @see net.customware.gwt.presenter.client.Display#stopProcessing()
     */
    @Override
    public void stopProcessing()
    {
    }


    /**
     * @return
     * @see name.pehl.tire.client.mvp.NewEditDeleteDisplay#getDeleteClick()
     */
    @Override
    public HasClickHandlers getDeleteClick()
    {
        return deleteButton;
    }


    /**
     * @return
     * @see name.pehl.tire.client.mvp.NewEditDeleteDisplay#getEditClick()
     */
    @Override
    public HasClickHandlers getEditClick()
    {
        return editButton;
    }


    /**
     * @return
     * @see name.pehl.tire.client.mvp.NewEditDeleteDisplay#getNewClick()
     */
    @Override
    public HasClickHandlers getNewClick()
    {
        return newButton;
    }
}
