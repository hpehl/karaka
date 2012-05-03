package name.pehl.tire.client.application;

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

    private static final int HIDE_DELAY = 4000;

    private boolean visible;
    private final Widget widget;
    private final Resources resources;
    private final HideTimer hideTimer;
    @UiField Label messageHolder;


    @Inject
    public MessageView(final Binder binder, final Resources resources)
    {
        this.visible = false;
        this.resources = resources;
        this.resources.message().ensureInjected();
        this.widget = binder.createAndBindUi(this);
        this.hideTimer = new HideTimer();
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    @Override
    public void show(Message message)
    {
        hideTimer.cancel();
        messageHolder.setText(message.getText());
        if (!visible)
        {
            messageHolder.removeStyleName(resources.message().fadeOut());
            messageHolder.addStyleName(resources.message().fadeIn());
        }
        visible = true;
        hideTimer.schedule(HIDE_DELAY);
    }


    private void hide()
    {
        messageHolder.removeStyleName(resources.message().fadeIn());
        messageHolder.addStyleName(resources.message().fadeOut());
        visible = false;
    }

    class HideTimer extends Timer
    {
        @Override
        public void run()
        {
            hide();
        }
    }
}
