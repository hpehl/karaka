package name.pehl.tire.client.application;

import static java.util.logging.Level.SEVERE;
import name.pehl.tire.client.resources.Resources;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class MessageView extends ViewImpl implements MessagePresenter.MyView
{
    public interface Binder extends UiBinder<Widget, MessageView>
    {
    }

    private boolean visible;
    private final Widget widget;
    private final Resources resources;
    private final AutoHideTimer autoHideTimer;
    private final TimeoutTimer timeoutTimer;
    @UiField Label messageHolder;


    @Inject
    public MessageView(final Binder binder, final Resources resources)
    {
        this.visible = false;
        this.resources = resources;
        this.resources.message().ensureInjected();
        this.widget = binder.createAndBindUi(this);
        this.autoHideTimer = new AutoHideTimer();
        this.timeoutTimer = new TimeoutTimer();
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    @Override
    public void show(Message message)
    {
        autoHideTimer.cancel();
        timeoutTimer.cancel();
        messageHolder.setText(message.getText());
        if (!visible)
        {
            messageHolder.removeStyleName(resources.message().hide());
            messageHolder.addStyleName(resources.message().show());
        }
        visible = true;
        if (message.isAutoHide())
        {
            autoHideTimer.schedule();
        }
        else
        {
            timeoutTimer.schedule();
        }
    }

    /**
     * Timer for auto hide messages.
     * 
     * @author $LastChangedBy:$
     * @version $LastChangedRevision:$
     */
    class AutoHideTimer extends Timer
    {
        void schedule()
        {
            super.schedule(4000);
        }


        /**
         * Hides the message pane
         * 
         * @see com.google.gwt.user.client.Timer#run()
         */
        @Override
        public void run()
        {
            messageHolder.removeStyleName(resources.message().show());
            messageHolder.addStyleName(resources.message().hide());
            visible = false;
        }
    }

    /**
     * Timer for none auto hide messages.
     * 
     * @author $LastChangedBy:$
     * @version $LastChangedRevision:$
     */
    class TimeoutTimer extends Timer
    {
        void schedule()
        {
            super.schedule(15000);
        }


        /**
         * Shows a auto hide timeout message
         * 
         * @see com.google.gwt.user.client.Timer#run()
         */
        @Override
        public void run()
        {
            show(new Message(SEVERE, "The last operation timed out.", true));
        }
    }
}
