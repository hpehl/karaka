package name.pehl.tire.client.cockpit;

import java.util.Date;

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

    private boolean recording;
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
        this.recording = false;
        this.resources = resources;
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    @Override
    public void initializeRecording(boolean recording)
    {
        this.recording = recording;
        if (recording)
        {
            startRecording();
        }
        else
        {
            stopRecording();
        }
    }


    @Override
    public void updateTimes(Activities activities)
    {
        if (activities != null)
        {
            Activity first = activities.getActivities().first();
            if (isToday(first.getStart()))
            {
                today.setText(FormatUtils.hours(first.getMinutes()));
            }
            if (activities.getWeekDiff() == 0)
            {
                week.setText(FormatUtils.hours(activities.getWeeks().last().getMinutes()));
            }
            if (activities.getMonthDiff() == 0)
            {
                month.setText(FormatUtils.hours(activities.getMinutes()));
            }
        }
    }


    @SuppressWarnings("deprecation")
    private boolean isToday(Date date)
    {
        Date now = new Date();
        if (now.getDay() == date.getDay() && now.getMonth() == date.getMonth() && now.getYear() == date.getYear())
        {
            return true;
        }
        return false;
    }


    @UiHandler("startStop")
    void onRecordClicked(ClickEvent event)
    {
        if (getUiHandlers() != null)
        {
            if (recording)
            {
                stopRecording();
            }
            else
            {
                startRecording();
            }
        }
    }


    private void startRecording()
    {
        if (getUiHandlers() != null)
        {
            this.recording = true;
            startStop.setResource(resources.recordOn());
            getUiHandlers().onStartRecording();
        }
    }


    private void stopRecording()
    {
        if (getUiHandlers() != null)
        {
            this.recording = false;
            startStop.setResource(resources.recordOff());
            getUiHandlers().onStopRecording();
        }
    }
}
