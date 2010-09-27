package name.pehl.tire.client.application;

import name.pehl.tire.client.resources.Resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

/**
 * @author $Author$
 * @version $Date$ $Revision$
 */
public class ControlView extends ViewWithUiHandlers<ControlUiHandlers> implements ControlPresenter.MyView
{
    interface StatusUi extends UiBinder<Widget, ControlView>
    {
    }

    private static StatusUi uiBinder = GWT.create(StatusUi.class);

    @UiField
    Image startStop;

    private boolean recording;
    private final Widget widget;
    private final Resources resources;


    @Inject
    public ControlView(final Resources resources)
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
        GWT.log("Start Recording");
        this.recording = true;
        startStop.setResource(resources.recordOn());
        getUiHandlers().onStartRecording();
    }


    private void stopRecording()
    {
        GWT.log("Stop Recording");
        this.recording = false;
        startStop.setResource(resources.recordOff());
        getUiHandlers().onStopRecording();
    }
}
