package name.pehl.karaka.client.activity.view;

import name.pehl.karaka.client.activity.presenter.CockpitPresenter;
import name.pehl.karaka.client.activity.presenter.CockpitUiHandlers;
import name.pehl.karaka.client.resources.Resources;
import name.pehl.karaka.client.ui.FormatUtils;
import name.pehl.karaka.shared.model.Activity;
import name.pehl.karaka.shared.model.Durations;

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
    @UiField Image startStop;
    @UiField Label today;
    @UiField InlineLabel week;
    @UiField InlineLabel month;
    @UiField Label activity;
    @UiField Label project;


    @Inject
    public CockpitView(final Binder binder, final Resources resources)
    {
        this.resources = resources;
        this.widget = binder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    @Override
    public void updateDurations(Durations minutes)
    {
        month.setText(FormatUtils.duration(minutes.getMonth()));
        week.setText(FormatUtils.duration(minutes.getWeek()));
        today.setText(FormatUtils.duration(minutes.getDay()));
    }


    @Override
    public void updateStatus(Activity activity)
    {
        if (activity == null)
        {
            this.activity.setText("No activity running");
            this.project.setText("No project selected");
            this.startStop.setResource(resources.recordOff());
        }
        else
        {
            this.activity.setText(activity.getName());
            if (activity.getProject() != null)
            {
                this.project.setText(activity.getProject().getName());
            }
            else
            {
                this.project.setText("No project selected");
            }
            if (activity.isRunning())
            {
                this.startStop.setResource(resources.recordOn());
            }
            else
            {
                this.startStop.setResource(resources.recordOff());
            }
        }
    }


    @UiHandler("startStop")
    void onStartStopClicked(ClickEvent event)
    {
        if (getUiHandlers() != null)
        {
            // CSS, text and flag will be updated in updateStatus()
            getUiHandlers().onStartStop();
        }
    }
}
