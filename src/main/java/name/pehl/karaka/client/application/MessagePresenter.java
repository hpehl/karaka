package name.pehl.karaka.client.application;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import name.pehl.karaka.client.application.ShowMessageEvent.ShowMessageHandler;

public class MessagePresenter extends PresenterWidget<MessagePresenter.MyView> implements ShowMessageHandler
{
    public interface MyView extends View
    {

        void show(Message message);
    }
    @Inject
    public MessagePresenter(final EventBus eventBus, final MyView view)
    {
        super(eventBus, view);
        getEventBus().addHandler(ShowMessageEvent.getType(), this);
    }


    @Override
    public void onShowMessage(ShowMessageEvent event)
    {
        getView().show(event.getMessage());
    }
}
