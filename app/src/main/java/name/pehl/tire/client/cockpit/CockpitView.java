package name.pehl.tire.client.cockpit;

import name.pehl.tire.client.resources.Resources;

import com.google.gwt.core.client.GWT;
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
 * @author $Author$
 * @version $Date$ $Revision: 102
 *          $
 */
public class CockpitView extends ViewWithUiHandlers<CockpitUiHandlers> implements CockpitPresenter.MyView
{
    interface CockpitUi extends UiBinder<Widget, CockpitView>
    {
    }

    private static CockpitUi uiBinder = GWT.create(CockpitUi.class);

    // @formatter:off
    @UiField Image startStop;
    @UiField Label today;
    @UiField InlineLabel week;
    @UiField InlineLabel month;
    @UiField Label description;
    @UiField Label project;
    // @formatter:on

    private boolean recording;
    private final Widget widget;
    private final Resources resources;


    @Inject
    public CockpitView(final Resources resources)
    {
        this.recording = false;
        this.resources = resources;
        this.widget = uiBinder.createAndBindUi(this);
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
