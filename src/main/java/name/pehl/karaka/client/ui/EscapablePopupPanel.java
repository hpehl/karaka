package name.pehl.karaka.client.ui;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.PopupPanel;

public class EscapablePopupPanel extends PopupPanel
{
    @Override
    protected void onPreviewNativeEvent(NativePreviewEvent event)
    {
        Event event0 = Event.as(event.getNativeEvent());
        if (event0.getTypeInt() == Event.ONKEYDOWN)
        {
            int keyCode = event0.getKeyCode();
            if (keyCode == KeyCodes.KEY_ESCAPE)
            {
                event.cancel();
                hide();
            }
        }
    }
}
