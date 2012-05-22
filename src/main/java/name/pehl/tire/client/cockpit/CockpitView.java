package name.pehl.tire.client.cockpit;

import name.pehl.tire.client.resources.Resources;
import name.pehl.tire.client.ui.FormatUtils;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Activity;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import static name.pehl.tire.shared.model.Status.RUNNING;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-07 16:33:54 +0100 (Di, 07. Dez 2010) $ $Revision: 102
 *          $
 */
public class CockpitView extends ViewWithUiHandlers<CockpitUiHandlers> implements CockpitPresenter.MyView
{
    public interface Binder extends UiBinder<Widget, CockpitView>
    {
    }

    private final Widget widget;
    private final Resources resources;
    private boolean recording;
    @UiField Image startStop;
    @UiField Label today;
    @UiField InlineLabel week;
    @UiField InlineLabel month;
    @UiField Label description;
    @UiField Label project;


    @Inject
    public CockpitView(final Binder binder, final Resources resources)
    {
        this.widget = binder.createAndBindUi(this);
        this.resources = resources;
        this.recording = false;
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    @Override
    public void updateMonth(Activities activities)
    {
        month.setText(FormatUtils.hours(activities.getMinutes()));
    }


    @Override
    public void updateWeek(Activities activities)
    {
        week.setText(FormatUtils.hours(activities.getMinutes()));
    }


    @Override
    public void updateToday(Activities activities)
    {
        today.setText(FormatUtils.hours(activities.getMinutes()));
    }


    @Override
    public void updateStatus(Activity activity)
    {
        description.setText(activity.getName());
        if (activity.getProject() != null)
        {
            project.setText(activity.getProject().getName());
        }
        else
        {
            project.setText("No project selected");
        }
        if (activity.getStatus() == RUNNING)
        {
            startStop.setResource(resources.recordOn());
            recording = true;
        }
        else
        {
            startStop.setResource(resources.recordOff());
            recording = false;
        }
    }


    @UiHandler("startStop")
    void onRecordClicked(ClickEvent event)
    {
        if (getUiHandlers() != null)
        {
            // CSS and flag will be updated in updateStatus()
            if (recording)
            {
                getUiHandlers().onStopRecording();
            }
            else
            {
                getUiHandlers().onStartRecording();
            }
        }
    }
}
