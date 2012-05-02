package name.pehl.tire.client.application;

import name.pehl.tire.client.ui.EscapablePopupPanel;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewImpl;

public class MessageView extends PopupViewImpl implements MessagePresenter.MyView
{
    public interface Binder extends UiBinder<EscapablePopupPanel, MessageView>
    {
    }

    private final EscapablePopupPanel popup;
    @UiField Label messageHolder;


    @Inject
    public MessageView(final EventBus eventBus, final Binder binder)
    {
        super(eventBus);
        this.popup = binder.createAndBindUi(this);
        setAutoHideOnNavigationEventEnabled(true);
    }


    @Override
    public Widget asWidget()
    {
        return popup;
    }


    @Override
    public void show(Message message)
    {
        messageHolder.setText(message.getText());
    }
}
