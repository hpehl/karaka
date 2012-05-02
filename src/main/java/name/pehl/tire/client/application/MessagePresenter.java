package name.pehl.tire.client.application;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

public class MessagePresenter extends PresenterWidget<MessagePresenter.MyView>
{
    public interface MyView extends PopupView
    {
        void show(Message message);
    }


    @Inject
    public MessagePresenter(final EventBus eventBus, final MyView view)
    {
        super(eventBus, view);
    }


    public void show(Message message)
    {
        getView().show(message);
    }
}
