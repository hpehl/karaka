package name.pehl.tire.client.application;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewImpl;

public class MessageView extends PopupViewImpl implements MessagePresenter.MyView
{
    private final Widget widget;

    public interface Binder extends UiBinder<Widget, MessageView>
    {
    }


    @Inject
    public MessageView(final EventBus eventBus, final Binder binder)
    {
        super(eventBus);
        widget = binder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    @Override
    public void show(Message message)
    {
        // TODO Auto-generated method stub
    }


    @Override
    public boolean isVisible()
    {
        return false;
    }
}
