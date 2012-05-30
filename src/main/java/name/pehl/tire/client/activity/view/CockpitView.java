package name.pehl.tire.client.activity.view;

import name.pehl.tire.client.activity.presenter.CockpitPresenter;
import name.pehl.tire.client.activity.presenter.CockpitUiHandlers;
import name.pehl.tire.client.resources.Resources;
import name.pehl.tire.client.ui.FormatUtils;
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
    @UiField Label description;
    @UiField Label project;


    @Inject
    public CockpitView(final Binder binder, final Resources resources)
    {
        this.widget = binder.createAndBindUi(this);
        this.resources = resources;
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    @Override
    public void updateMonth(long minutes)
    {
        month.setText(FormatUtils.hours(minutes));
    }


    @Override
    public void updateWeek(long minutes)
    {
        week.setText(FormatUtils.hours(minutes));
    }


    @Override
    public void updateToday(long minutes)
    {
        today.setText(FormatUtils.hours(minutes));
    }


    @Override
    public void updateStatus(Activity activity)
    {
        if (activity != null)
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
            if (activity.isRunning())
            {
                startStop.setResource(resources.recordOn());
            }
            else
            {
                startStop.setResource(resources.recordOff());
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
