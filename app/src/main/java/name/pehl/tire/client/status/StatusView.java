package name.pehl.tire.client.status;

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
 * @version $Date$ $Revision: 90
 *          $
 */
public class StatusView extends ViewWithUiHandlers<StatusUiHandlers> implements StatusPresenter.MyView
{
    interface StatusUi extends UiBinder<Widget, StatusView>
    {
    }

    private static StatusUi uiBinder = GWT.create(StatusUi.class);

    private final Widget widget;
    private final Resources resources;

    @UiField
    Image record;


    @Inject
    public StatusView(final Resources resources)
    {
        this.resources = resources;
        this.widget = uiBinder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    @UiHandler("record")
    void onRecordClicked(ClickEvent event)
    {
        if (getUiHandlers() != null)
        {
            String style = record.getStyleName();
            String onClass = resources.record().on();
            String offClass = resources.record().off();
            if (style.contains(onClass))
            {
                // stop recording
                GWT.log("Stop Recording");
                record.setResource(resources.off());
                record.removeStyleName(onClass);
                record.addStyleName(offClass);
                getUiHandlers().onStopRecording();
            }
            else if (style.contains(offClass))
            {
                // start recording
                GWT.log("Start Recording");
                record.setResource(resources.on());
                record.removeStyleName(offClass);
                record.addStyleName(onClass);
                getUiHandlers().onStartRecording();
            }
        }
    }
}
